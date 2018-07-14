/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Mikhail Yakushin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.driver733.vkmusicuploader.wallpost.wallposts;

import com.driver733.vkmusicuploader.media.photo.MediaPhotosBasic;
import com.driver733.vkmusicuploader.media.photo.MediaPhotosNonProcessed;
import com.driver733.vkmusicuploader.post.SuppressFBWarnings;
import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.wallpost.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.WallPost;
import com.driver733.vkmusicuploader.wallpost.WallPostPhotoAlbum;
import com.driver733.vkmusicuploader.wallpost.attachment.support.WallPhotoStatus;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.jcabi.log.Logger;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Creates {@link WallPost}s
 *  with photos found in the specified directory.
 *  Each wall post has up to 10 photos.
 *
 *
 *
 * @since 0.2
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (20 lines)
 */
@Immutable
@SuppressFBWarnings(
    value = "NP_NULL_ON_SOME_PATH",
    justification = "If path exists then NP will not occur."
)
public final class WallPostsPhotoAlbum implements WallPosts {

    /**
     * Maximum number of requests in each batch request.
     */
    private static final int BATCH_MAX_REQ = 25;

    /**
     * Number of files in each wall post.
     *  (Equal to the maximum number of attachments)
     */
    private static final int PHOTOS_IN_POST = 10;

    /**
     * The "cost" of a wall.post request.
     */
    private static final int WALL_POST_REQ = 1;

    /**
     * Photos in each batch request.
     */
    private static final int PHOTOS_IN_REQ =
        WallPostsPhotoAlbum.BATCH_MAX_REQ
            - 3 * WallPostsPhotoAlbum.WALL_POST_REQ;

    /**
     * Group ID.
     */
    private final int group;

    /**
     * {@link VkApiClient} for all requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Album dir.
     */
    private final Path dir;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServers servers;

    /**
     * Properties that contain the {@link WallPhotoStatus}es of photos.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param dir Album dir.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param properties Properties that contain the
     *  {@link WallPhotoStatus}es of photos.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostsPhotoAlbum(
        final VkApiClient client,
        final UserActor actor,
        final Path dir,
        final UploadServers servers,
        final ImmutableProperties properties,
        final int group
    ) {
        this.client = client;
        this.actor = actor;
        this.dir = dir;
        this.servers = servers;
        this.properties = properties;
        this.group = group;
    }

    // @checkstyle LocalFinalVariableNameCheck (20 lines)
    /**
     * Constructs queries for batch posting wall postsQueries
     * associated with the album.
     * @return ExecuteBatchQuery.
     * @throws Exception If no photos are found.
     */
    public List<ExecuteBatchQuery> postsQueries() throws Exception {
        final List<Path> photos = this.photos();
        final List<ExecuteBatchQuery> queries = new ArrayList<>(photos.size());
        int iter = 0;
        Logger.debug(
            this,
            "Analyzing directory '%s'...",
            this.dir
        );
        while (iter < photos.size()) {
            final int to;
            if (photos.size() < iter + WallPostsPhotoAlbum.PHOTOS_IN_REQ) {
                to = photos.size() - iter;
            } else {
                to = iter + WallPostsPhotoAlbum.PHOTOS_IN_REQ;
            }
            queries.add(
                this.postsBatch(
                    photos.subList(
                        iter,
                        to
                    )
                )
            );
            iter += WallPostsPhotoAlbum.PHOTOS_IN_REQ;
        }
        if (queries.isEmpty()) {
            Logger.debug(this, "No photos to upload. Skipping...");
        }
        return queries;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void updateProperties() throws IOException {
        final List<Path> photos = this.photos();
        for (final Path photo : photos) {
            this.properties.setPropertyAndStore(
                photo.getFileName()
                    .toString(),
                WallPhotoStatus.POSTED.toString()
            );
        }
    }

    /**
     * Finds photo files that have not been posted yet.
     * @return A list of photos {@link File}s.
     * @throws IOException If a certain criteria of
     *  is not fulfilled.
     */
    @Cacheable(forever = true)
    private List<Path> photos() throws IOException {
        return new MediaPhotosNonProcessed(
            new MediaPhotosBasic(
                this.dir
            ),
            this.properties
        ).files();
    }

    // @checkstyle LocalFinalVariableNameCheck (30 lines)
    /**
     * Constructs a query for batch posting wall postsQueries
     * associated with the album.
     * @param photos Photos to include with the wall postsQueries.
     * @return ExecuteBatchQuery.
     * @throws Exception If the WallPost query cannot be obtained.
     */
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.OptimizableToArrayCall"
        })
    private ExecuteBatchQuery postsBatch(final List<Path> photos) throws
        Exception {
        Logger.info(
            this,
            "Processing directory: '%s'...",
            this.dir
        );
        final List<WallPostQuery> posts = new ArrayList<>(
            photos.size()
        );
        int from = 0;
        while (from < photos.size()) {
            final int to;
            if (photos.size() < from + WallPostsPhotoAlbum.PHOTOS_IN_POST) {
                to = photos.size();
            } else {
                to = from + WallPostsPhotoAlbum.PHOTOS_IN_POST;
            }
            final WallPostQuery query;
            try {
                query = new WallPostPhotoAlbum(
                    this.client,
                    this.actor,
                    new ListOf<>(
                        photos.subList(
                            from,
                            to
                        )
                    ),
                    this.servers,
                    this.group
                ).construct();
            } catch (final IOException ex) {
                throw new IOException(
                    "Failed to obtain a WallPost query",
                    ex
                );
            }
            posts.add(query);
            from += WallPostsPhotoAlbum.PHOTOS_IN_POST;
        }
        return new ExecuteBatchQuery(
            this.client,
            this.actor,
            posts.toArray(
                new WallPostQuery[0]
            )
        );
    }

}
