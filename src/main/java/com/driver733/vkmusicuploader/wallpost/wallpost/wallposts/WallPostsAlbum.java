/**
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
package com.driver733.vkmusicuploader.wallpost.wallpost.wallposts;

import com.driver733.vkmusicuploader.media.audio.AudiosBasic;
import com.driver733.vkmusicuploader.media.audio.AudiosNonProcessed;
import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.wallpost.WallPostAlbum;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.jcabi.log.Logger;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates {@link com.driver733.vkmusicuploader.wallpost.wallpost.WallPost}s
 *  with albums found in the specified directory.
 *  Each wall post has up 9 audios.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
@Immutable
public final class WallPostsAlbum implements WallPosts {

    /**
     * Maximum number of requests in each batch request.
     * @see
     */
    private static final int BATCH_MAX_REQ = 25;

    /**
     * Number of files in each wall post.
     * 1 (Album image)
     */
    private static final int PHOTOS_IN_POST = 1;

    /**
     * The "cost" of a wall.post request.
     */
    private static final int WALL_POST_REQ = 1;

    /**
     * Maximum number of attachments in a wall post.
     */
    private static final int MAX_ATTACHMENTS = 10;

    /**
     * Audios in each batch request.
     */
    private static final int AUDIOS_IN_REQ =
        WallPostsAlbum.BATCH_MAX_REQ
            - 3 * (WallPostsAlbum.PHOTOS_IN_POST
            + WallPostsAlbum.WALL_POST_REQ
        );

    /**
     * Audios in each Wall Post.
     */
    private static final int AUDIOS_IN_POST =
        WallPostsAlbum.MAX_ATTACHMENTS - WallPostsAlbum.PHOTOS_IN_POST;

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
    private final File dir;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServers servers;

    /**
     * Properties that contain the {@link AudioStatus}es of audio files.
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
     *  {@link AudioStatus}es of audio files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostsAlbum(
        final VkApiClient client,
        final UserActor actor,
        final File dir,
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
     * @throws Exception If no audios are found.
     */
    public List<ExecuteBatchQuery> postsQueries() throws Exception {
        final List<File> audios = this.audios();
        final List<ExecuteBatchQuery> queries = new ArrayList<>(
            audios.size()
        );
        int iter = 0;
        Logger.debug(
            this,
            "Analyzing directory '%s'...",
            this.dir.getPath()
        );
        while (iter < audios.size()) {
            final int to;
            if (audios.size() < iter + WallPostsAlbum.AUDIOS_IN_REQ) {
                to = audios.size() - iter;
            } else {
                to = iter + WallPostsAlbum.AUDIOS_IN_REQ;
            }
            queries.add(
                this.postsBatch(
                    audios.subList(
                        iter,
                        to
                    )
                )
            );
            iter += WallPostsAlbum.AUDIOS_IN_REQ;
        }
        Collections.reverse(queries);
        if (queries.isEmpty()) {
            Logger.debug(
                this,
                "No audio files to upload. Skipping..."
            );
        }
        return queries;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void updateProperties() throws IOException {
        final List<File> audios = this.audios();
        for (final File audio : audios) {
            this.properties.setProperty(
                audio.getName(),
                new StringBuilder(
                    this.properties.getProperty(
                        audio.getName()
                    )
                ).replace(
                    0,
                    1,
                    AudioStatus.POSTED
                        .toString()
                ).toString()
            );
        }
        this.properties.store();
    }

    /**
     * Finds audio files that have not been posted yet.
     * @return An array of audio {@link File}s.
     * @throws IOException If a certain criteria of
     *  is not fulfilled.
     */
    @Cacheable(forever = true)
    private List<File> audios() throws IOException {
        return new AudiosNonProcessed(
            new AudiosBasic(
                this.dir
            ),
            this.properties
        ).files();
    }

    // @checkstyle LocalFinalVariableNameCheck (30 lines)
    /**
     * Constructs a query for batch posting wall postsQueries
     * associated with the album.
     * @param audios Audio files to include with the wall postsQueries.
     * @return ExecuteBatchQuery.
     * @throws Exception If the WallPost query cannot be obtained.
     */
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.OptimizableToArrayCall"
        })
    private ExecuteBatchQuery postsBatch(
        final List<File> audios
    ) throws
        Exception {
        Logger.info(
            this,
            "Processing directory: '%s'...",
            this.dir.getPath()
        );
        final List<WallPostQuery> posts = new ArrayList<>(
            audios.size()
        );
        int from = 0;
        while (from < audios.size()) {
            final int to;
            if (audios.size() < from + WallPostsAlbum.AUDIOS_IN_POST) {
                to = audios.size();
            } else {
                to = from + WallPostsAlbum.AUDIOS_IN_POST;
            }
            final WallPostQuery query;
            try {
                query = new WallPostAlbum(
                    this.client,
                    this.actor,
                    new Array<>(
                        audios.subList(
                            from,
                            to
                        )
                    ),
                    this.servers,
                    this.properties,
                    this.group
                ).construct();
            } catch (final IOException ex) {
                throw new IOException(
                    "Failed to obtain a WallPost query",
                    ex
                );
            }
            posts.add(query);
            from += WallPostsAlbum.AUDIOS_IN_POST;
        }
        Collections.reverse(posts);
        return new ExecuteBatchQuery(
            this.client,
            this.actor,
            posts.toArray(
                new WallPostQuery[0]
            )
        );
    }

}
