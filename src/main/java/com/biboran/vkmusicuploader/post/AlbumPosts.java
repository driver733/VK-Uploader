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

package com.biboran.vkmusicuploader.post;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
 */
public final class AlbumPosts implements Posts {

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Album directory.
     */
    private final File directory;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServers servers;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param directory Album directory.
     * @param uploadServers Upload servers that provide upload URLs
     *  for attachmentsFields.
     */
    public AlbumPosts(
        final UserActor actor,
        final File directory,
        final UploadServers uploadServers
    ) {
        this.actor = actor;
        this.directory = directory;
        this.servers = uploadServers;
    }

    /**
     * Constructs queries for batch posting wall posts
     * associated with the album.
     * @return ExecuteBatchQuery.
     */
    public List<ExecuteBatchQuery> posts() {
        final File[] audios = this.directory.listFiles(
            (fileDir, fileName) -> fileName.endsWith(".mp3")
        );
        int iter = 0;
        assert audios != null;
        final List<ExecuteBatchQuery> queries = new ArrayList<>(audios.length);
        while (iter < audios.length) {
            final int batchAudios = 1;
            if (audios.length < iter + batchAudios) {
                queries.add(
                    this.postsBatch(
                        Arrays.copyOfRange(audios, iter, audios.length - iter)
                    )
                );
            } else {
                queries.add(
                    this.postsBatch(
                        Arrays.copyOfRange(audios, iter, iter + batchAudios)
                    )
                );
            }
            iter += batchAudios;
        }
        Collections.reverse(queries);
        return queries;
    }

    /**
     * Constructs a query for batch posting wall posts
     * associated with the album.
     * @param audios Audio files to include with the wall posts.
     * @return ExecuteBatchQuery.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private ExecuteBatchQuery postsBatch(final File... audios) {
        final List<WallPostQuery> posts = new ArrayList<>(audios.length);
        int iter = 0;
        while (iter < audios.length) {
            final int postAudios = 1;
            if (audios.length < iter + postAudios) {
                posts.add(
                    new Post(
                        this.actor,
                        Arrays.copyOfRange(audios, iter, audios.length),
                        this.servers
                    ).construct()
                );
            } else {
                posts.add(
                    new Post(
                        this.actor,
                        Arrays.copyOfRange(audios, iter, iter + postAudios),
                        this.servers
                    ).construct()
                );
            }
            iter += postAudios;
        }
        Collections.reverse(posts);
        return new ExecuteBatchQuery(
            new VkApiClient(new HttpTransportClient()),
            this.actor,
            posts.toArray(new WallPostQuery[posts.size()])
        );
    }

}
