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
package com.driver733.vkmusicuploader.wallpost.wallpost.wallposts;

import com.driver733.vkmusicuploader.media.Media;
import com.driver733.vkmusicuploader.media.MediaRandom;
import com.driver733.vkmusicuploader.media.audio.MediaAudiosBasic;
import com.driver733.vkmusicuploader.media.audio.MediaEmpty;
import com.driver733.vkmusicuploader.media.photo.MediaPhotosBasic;
import com.driver733.vkmusicuploader.post.SuppressFBWarnings;
import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.support.WallPhotoStatus;
import com.driver733.vkmusicuploader.wallpost.wallpost.WallPostRandom;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Creates {@link com.driver733.vkmusicuploader.wallpost.wallpost.WallPost}s
 *  with photos found in the specified directory.
 *  Each wall post has up to 10 photos.
 *
 * @since 0.2
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 * @checkstyle ParameterNumberCheck (500 lines)
 */
@Immutable
@SuppressFBWarnings(
    value = "NP_NULL_ON_SOME_PATH",
    justification = "If path exists then NP will not occur."
    )
@SuppressWarnings({
    "PMD.AvoidInstantiatingObjectsInLoops",
    "PMD.OnlyOneConstructorShouldDoInitialization"
    })
public final class WallPostsRandom implements WallPosts {

    /**
     * Maximum number of requests in each batch request.
     */
    private static final int BATCH_MAX_REQ = 25;

    /**
     * Random seed.
     */
    private static final int SEED = 123456;

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
    private final UploadServers servers;

    /**
     * Properties for caching results.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param properties For caching results.
     * @param photos Directory with photos.
     */
    public WallPostsRandom(
        final VkApiClient client,
        final UserActor actor,
        final UploadServers servers,
        final ImmutableProperties properties,
        final int group,
        final Path photos
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = servers;
        this.group = group;
        this.properties = properties;
        this.photos = new MediaRandom(
            new MediaPhotosBasic(
                photos
            ),
            WallPostsRandom.SEED
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
     * @param properties For caching results.
     * @param audios Directory with nonProcessedAudios.
     */
    public WallPostsRandom(
        final VkApiClient client,
        final UserActor actor,
        final UploadServers servers,
        final ImmutableProperties properties,
        final Path audios,
        final int group
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = servers;
        this.group = group;
        this.properties = properties;
        this.photos = new MediaEmpty();
        this.audios = new MediaRandom(
            new MediaAudiosBasic(
                audios
            ),
            WallPostsRandom.SEED
        );
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param properties For caching results.
     * @param photos Directory with photos.
     * @param audios Directory with nonProcessedAudios.
     */
    public WallPostsRandom(
        final VkApiClient client,
        final UserActor actor,
        final UploadServers servers,
        final ImmutableProperties properties,
        final int group,
        final Path photos,
        final Path audios
    ) {
        this.client = client;
        this.actor = actor;
        this.properties = properties;
        this.servers = servers;
        this.group = group;
        this.photos = new MediaRandom(
            new MediaPhotosBasic(
                photos
            ),
            WallPostsRandom.SEED
        );
        this.audios = new MediaRandom(
            new MediaAudiosBasic(
                audios
            ),
            WallPostsRandom.SEED
        );
    }

    /**
     * Constructs queries for batch posting wall postsQueries
     * associated with the album.
     * @return ExecuteBatchQuery.
     * @throws Exception If no photos are found.
     */
    @SuppressWarnings("PMD.OptimizableToArrayCall")
    public List<ExecuteBatchQuery> postsQueries() throws Exception {
        final Iterator<Path> audio = this.audios
            .files()
            .iterator();
        final Iterator<Path> photo = this.photos
            .files()
            .iterator();
        int left = this.numberOfFilesToProcess();
        final List<ExecuteBatchQuery> result = new ArrayList<>(
            left
        );
        int num;
        while (left > 0) {
            if (left > WallPostsRandom.BATCH_MAX_REQ) {
                num = WallPostsRandom.BATCH_MAX_REQ;
            } else {
                num = left;
            }
            final List<WallPostQuery> posts = new ArrayList<>(
                num
            );
            for (int iter = 0; iter < num; iter += 1) {
                final WallPostQuery query;
                if (
                    this.photos.files().isEmpty()
                        && this.audios.files().isEmpty()
                    ) {
                    break;
                } else if (
                    this.audios
                        .files()
                        .isEmpty()
                ) {
                    query = new WallPostRandom(
                        this.client,
                        this.actor,
                        this.servers,
                        photo.next(),
                        this.properties,
                        this.group
                    ).construct();
                } else if (
                    this.photos
                        .files()
                        .isEmpty()
                ) {
                    query = new WallPostRandom(
                        this.client,
                        this.actor,
                        this.servers,
                        this.group,
                        this.properties,
                        audio.next()
                    ).construct();
                } else {
                    query = new WallPostRandom(
                        this.client,
                        this.actor,
                        this.servers,
                        this.group,
                        this.properties,
                        photo.next(),
                        audio.next()
                    ).construct();
                }
                posts.add(query);
            }
            result.add(
                new ExecuteBatchQuery(
                    this.client,
                    this.actor,
                    posts.toArray(
                        new WallPostQuery[0]
                    )
                )
            );
            left -= num;
        }
        return result;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void updateProperties() throws IOException {
        final List<Path> images = this.photos.files();
        final int nphotos = Math.min(
            this.numberOfFilesToProcess(),
            images.size()
        );
        for (int iter = 0; iter < nphotos; iter += 1) {
            this.properties.setPropertyAndStore(
                images.get(iter).getFileName().toString(),
                WallPhotoStatus.POSTED.toString()
            );
        }
        final List<Path> audioss = this.audios.files();
        final int naudios = Math.min(
            this.numberOfFilesToProcess(),
            audioss.size()
        );
        for (int iter = 0; iter < naudios; iter += 1) {
            final Path audio = audioss.get(iter);
            this.properties.setPropertyAndStore(
                audio.getFileName().toString(),
                new StringBuilder(
                    this.properties.getProperty(
                        audio.getFileName()
                            .toString()
                    )
                ).replace(
                    0,
                    1,
                    AudioStatus.POSTED
                        .toString()
                ).toString()
            );
        }
    }

    /**
     * Determines the number of files to process.
     * @return Number of files to process.
     * @throws IOException If files cannot be read.
     */
    private int numberOfFilesToProcess() throws IOException {
        final int left;
        if (this.audios.files().isEmpty()) {
            left = this.photos
                .files()
                .size();
        } else if (this.photos.files().isEmpty()) {
            left = this.audios
                .files()
                .size();
        } else {
            left = Math.min(
                this.audios
                    .files()
                    .size(),
                this.photos
                    .files()
                    .size()
            );
        }
        return left;
    }

}
