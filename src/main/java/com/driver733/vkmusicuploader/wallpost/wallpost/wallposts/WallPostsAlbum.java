/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Mikhail Yakushin
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

import com.driver733.vkmusicuploader.audio.AudiosBasic;
import com.driver733.vkmusicuploader.audio.AudiosNonProcessed;
import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.support.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.wallpost.WallPostAlbum;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.jcabi.log.Logger;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class or Interface description.
 * <p>
 * Additional info
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
    private static final int MAX_REQUESTS = 25;

    /**
     * Number of photos in each wall post.
     * 1 (Album image)
     */
    private static final int PHOTOS_IN_POST = 1;

    /**
     * The "cost" of a wall.post request.
     */
    private static final int WALL_POST_REQUEST = 1;

    /**
     * Maximum number of attachments in a wall post.
     */
    private static final int MAX_ATTACHMENTS = 10;

    /**
     * Audios in each batch request.
     */
    private static final int AUDIOS_IN_REQUEST =
        WallPostsAlbum.MAX_REQUESTS
            - 3 * (WallPostsAlbum.PHOTOS_IN_POST
            + WallPostsAlbum.WALL_POST_REQUEST);

    /**
     * Audios in each Wall Post.
     */
    private static final int AUDIOS_IN_POST =
        WallPostsAlbum.MAX_ATTACHMENTS - WallPostsAlbum.PHOTOS_IN_POST;

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
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param dir Album dir.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param properties Properties that contain the
     *  {@link AudioStatus}es of audio files.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostsAlbum(
        final UserActor actor,
        final File dir,
        final UploadServers servers,
        final ImmutableProperties properties
    ) {
        this.actor = actor;
        this.dir = dir;
        this.servers = servers;
        this.properties = properties;
    }

    // @checkstyle LocalFinalVariableNameCheck (20 lines)
    /**
     * Constructs queries for batch posting wall postsQueries
     * associated with the album.
     * @return ExecuteBatchQuery.
     * @throws IOException If no audios are found.
     */
    public List<ExecuteBatchQuery> postsQueries() throws IOException {
        final Array<File> audios = this.audios();
        final Array<ExecuteBatchQuery> queries = new Array<>();
        int iter = 0;
        Logger.debug(
            this,
            "Analyzing directory '%s'...", this.dir.getPath()
        );
        while (iter < audios.size()) {
            final int to;
            if (audios.size() < iter + WallPostsAlbum.AUDIOS_IN_REQUEST) {
                to = audios.size() - iter;
            } else {
                to = iter + WallPostsAlbum.AUDIOS_IN_REQUEST;
            }
            queries.add(
                this.postsBatch(
                    audios.subList(iter, to)
                )
            );
            iter += WallPostsAlbum.AUDIOS_IN_REQUEST;
        }
        Collections.reverse(queries);
        if (queries.isEmpty()) {
            Logger.debug(this, "No audio files to upload. Skipping...");
        }
        return queries;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void updateProperties() throws IOException {
        final Array<File> audios = this.audios();
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
                    AudioStatus.POSTED.toString()
                ).toString()
            );
        }
        this.properties.store();
    }

    /**
     * Finds audio files that have not been posted yet.
     * @return An array of audio {@link File}s.
     * @throws IOException If a certain criteria of
     *  {@link com.driver733.vkmusicuploader.audio.Audios}
     *  is not fulfilled.
     */
    @Cacheable(forever = true)
    private Array<File> audios() throws IOException {
        return new AudiosNonProcessed(
            new AudiosBasic(this.dir),
            this.properties
        ).audios();
    }

    // @checkstyle LocalFinalVariableNameCheck (30 lines)
    /**
     * Constructs a query for batch posting wall postsQueries
     * associated with the album.
     * @param audios Audio files to include with the wall postsQueries.
     * @return ExecuteBatchQuery.
     * @throws IOException If the WallPost query cannot be obtained.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private ExecuteBatchQuery postsBatch(final List<File> audios) throws
        IOException {
        Logger.info(
            this,
            "Processing directory: '%s'...", this.dir.getPath()
        );
        final List<WallPostQuery> posts = new ArrayList<>(audios.size());
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
                    this.actor,
                    new Array<>(
                        audios.subList(from, to)
                    ),
                    this.servers,
                    this.properties
                ).construct();
            } catch (final IOException ex) {
                throw new IOException("Failed to obtain a WallPost query", ex);
            }
            posts.add(query);
            from += WallPostsAlbum.AUDIOS_IN_POST;
        }
        Collections.reverse(posts);
        return new ExecuteBatchQuery(
            new VkApiClient(new HttpTransportClient()),
            this.actor,
            posts.toArray(new WallPostQuery[posts.size()])
        );
    }

}
