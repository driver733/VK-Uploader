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
package com.driver733.vkuploader.wallpost.wallposts;

import com.driver733.vkuploader.media.Media;
import com.driver733.vkuploader.media.MediaAudiosBasic;
import com.driver733.vkuploader.media.MediaEmpty;
import com.driver733.vkuploader.media.MediaRandom;
import com.driver733.vkuploader.media.photo.MediaPhotosBasic;
import com.driver733.vkuploader.post.UploadServersBasic;
import com.driver733.vkuploader.wallpost.WallPost;
import com.driver733.vkuploader.wallpost.WallPostWithRandomQuote;
import com.driver733.vkuploader.wallpost.attachment.upload.TransportClientFake;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Creates {@link WallPost}s
 *  with photos found in the specified directory.
 *  Each wall post has up to 10 photos.
 *
 *
 *
 * @since 0.2
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
@Immutable
@SuppressWarnings({
    "PMD.AvoidInstantiatingObjectsInLoops",
    "PMD.OnlyOneConstructorShouldDoInitialization"
    })
public final class WallPostsRandomQuote implements WallPosts {

    /**
     * Maximum number of requests in each batch request.
     */
    private static final int BATCH_MAX_REQ = 25;

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
     * Photos.
     */
    private final Media photos;

    /**
     * Audios.
     */
    private final Media audios;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServersBasic servers;

    /**
     * Number of posts to create.
     */
    private final int count;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param group Group ID.
     * @param count Number of posts to create.
     * @checkstyle ParameterNumberCheck (200 lines)
     */
    public WallPostsRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final int group,
        final int count
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = new UploadServersBasic(
            new VkApiClient(
                new TransportClientFake()
            ),
            new UserActor(
                0,
                ""
            ),
            0
        );
        this.photos = new MediaEmpty();
        this.audios = new MediaEmpty();
        this.group = group;
        this.count = count;
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param count Number of posts to create.
     * @param photos EntranceDirectory with photos.
     */
    public WallPostsRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final int group,
        final int count,
        final Path photos
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = servers;
        this.group = group;
        this.count = count;
        this.photos = new MediaPhotosBasic(
            photos
        );
        this.audios = new MediaEmpty();
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param audios EntranceDirectory with audios.
     * @param count Number of posts to create.
     */
    public WallPostsRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final int group,
        final Path audios,
        final int count
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = servers;
        this.group = group;
        this.count = count;
        this.photos = new MediaEmpty();
        this.audios = new MediaAudiosBasic(
            audios
        );
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param count Number of posts to create.
     * @param photos EntranceDirectory with photos.
     * @param audios EntranceDirectory with audios.
     */
    public WallPostsRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final int group,
        final int count,
        final Path photos,
        final Path audios
    ) {
        this.client = client;
        this.actor = actor;
        this.group = group;
        this.servers = servers;
        this.count = count;
        this.photos = new MediaPhotosBasic(
            photos
        );
        this.audios = new MediaAudiosBasic(
            audios
        );
    }

    /**
     * Constructs queries for batch posting wall postsQueries
     * associated with the album.
     * @return ExecuteBatchQuery.
     * @throws Exception If no photos are found.
     */
    public List<ExecuteBatchQuery> postsQueries() throws Exception {
        int left = this.count;
        final List<ExecuteBatchQuery> result =
            new ArrayList<>(
                left
            );
        int num;
        while (left > 0) {
            if (left > WallPostsRandomQuote.BATCH_MAX_REQ) {
                num = WallPostsRandomQuote.BATCH_MAX_REQ;
            } else {
                num = left;
            }
            if (
                this.photos.files().isEmpty()
                    && this.audios.files().isEmpty()
                ) {
                result.add(
                    this.posts(
                        num,
                        new WallPostWithRandomQuote(
                            this.client,
                            this.actor,
                            this.group
                        )
                    )
                );
            } else if (
                this.audios
                    .files()
                    .isEmpty()
            ) {
                result.add(
                    this.posts(
                        num,
                        new WallPostWithRandomQuote(
                            this.client,
                            this.actor,
                            this.servers,
                            new MediaRandom(
                                this.photos
                            ).file(),
                            this.group
                        )
                    )
                );
            } else if (
                this.photos
                    .files()
                    .isEmpty()
            ) {
                result.add(
                    this.posts(
                        num,
                        new WallPostWithRandomQuote(
                            this.client,
                            this.actor,
                            this.servers,
                            this.group,
                            new MediaRandom(
                                this.audios
                            ).file()
                        )
                    )
                );
            } else {
                result.add(
                    this.posts(
                        num,
                        new WallPostWithRandomQuote(
                            this.client,
                            this.actor,
                            this.servers,
                            this.group,
                            new MediaRandom(
                                this.photos
                            ).file(),
                            new MediaRandom(
                                this.audios
                            ).file()
                        )
                    )
                );
            }
            left -= num;
        }
        return result;
    }

    @Override
    public void updateProperties() {
        throw new UnsupportedOperationException(
            "All used resources are random."
        );
    }

    /**
     * Creates a Batch Query.
     * @param num Number of posts to create.
     * @param wallpost Wallpost to post.
     * @return Batch queries.
     * @throws Exception If an error occurs while constructing queries.
     */
    @SuppressWarnings("PMD.OptimizableToArrayCall")
    private ExecuteBatchQuery posts(
        final int num,
        final WallPostWithRandomQuote wallpost
    ) throws Exception {
        final List<WallPostQuery> queries = new ArrayList<>(
            WallPostsRandomQuote.BATCH_MAX_REQ
        );
        for (int iter = 0; iter < num; iter += 1) {
            queries.add(
                wallpost.construct()
            );
            TimeUnit.SECONDS.sleep(2);
        }
        return new ExecuteBatchQuery(
            this.client,
            this.actor,
            queries.toArray(
                new WallPostQuery[0]
            )
        );
    }

}
