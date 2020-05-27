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
package com.driver733.vkuploader.wallpost;

import com.driver733.vkuploader.post.UploadServersBasic;
import com.driver733.vkuploader.wallpost.attachment.AttachmentAudio;
import com.driver733.vkuploader.wallpost.attachment.AttachmentWallPhoto;
import com.driver733.vkuploader.wallpost.attachment.message.MessageWithRandomQuote;
import com.driver733.vkuploader.wallpost.attachment.message.RequestRandomQuote;
import com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayFromFile;
import com.driver733.vkuploader.wallpost.attachment.support.fields.AttachmentArrays;
import com.driver733.vkuploader.wallpost.attachment.upload.UploadAudio;
import com.driver733.vkuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.nio.file.Path;
import org.cactoos.Scalar;
import org.cactoos.scalar.Constant;

/**
 * A Wallpost with a random quote.
 *
 * @since 0.2
 * @checkstyle ClassDataAbstractionCouplingCheck (5 lines)
 */
@Immutable
@SuppressWarnings("PMD.OnlyOneConstructorShouldDoInitialization")
public final class WallPostWithRandomQuote implements WallPost {

    /**
     * Resulted query.
     */
    private final Scalar<WallPost> query;

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (500 lines)
     */
    public WallPostWithRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final int group
    ) {
        this.query =
            new Constant<>(
                () -> new WallPostWithOwnerId(
                    new WallPostFromGroup(
                        new WallPostWithMessage(
                            new WallPostBase(
                                client,
                                actor
                            ),
                            new MessageWithRandomQuote(
                                new RequestRandomQuote()
                                    .value()
                            )
                        )
                    ),
                        -group
                ).construct()
            );
    }

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param photo WallPost photo.
     * @param group Group ID.
     */
    public WallPostWithRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final Path photo,
        final int group
    ) {
        this.query =
            new Constant<>(
                () -> new WallPostWithOwnerId(
                    new WallPostFromGroup(
                        new WallPostWithMessage(
                            new WallPostWithAttachments(
                                new WallPostBase(
                                    client,
                                    actor
                                ),
                                new AttachmentArrays(
                                    actor,
                                    group,
                                    new AttachmentWallPhoto(
                                        client,
                                        actor,
                                        group,
                                        new UploadWallPhoto(
                                            client,
                                            servers.wallPhoto(),
                                            photo
                                        )
                                    )
                                )
                            ),
                            new MessageWithRandomQuote(
                                new RequestRandomQuote()
                                    .value()
                            )
                        )
                    ),
                    -group
                ).construct()
            );
    }

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param audio WallPost audio.
     */
    public WallPostWithRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final int group,
        final Path audio
    ) {
        this.query =
            new Constant<>(
                () -> new WallPostWithOwnerId(
                    new WallPostFromGroup(
                        new WallPostWithMessage(
                            new WallPostWithAttachments(
                                new WallPostBase(
                                    client,
                                    actor
                                ),
                                new AttachmentArrays(
                                    actor,
                                    group,
                                    new AttachmentAudio(
                                        client,
                                        actor,
                                        group,
                                        new UploadAudio(
                                            client,
                                            servers.audios(),
                                            audio
                                        )
                                    )
                                )
                            ),
                            new MessageWithRandomQuote(
                                new RequestRandomQuote()
                                    .value()
                            )
                        )
                    ),
                    -group
                ).construct()
            );
    }

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @param photo WallPost photo.
     * @param audio WallPost audio.
     */
    public WallPostWithRandomQuote(
        final VkApiClient client,
        final UserActor actor,
        final UploadServersBasic servers,
        final int group,
        final Path photo,
        final Path audio
    ) {
        this.query =
            new Constant<>(
                () -> new WallPostWithOwnerId(
                    new WallPostFromGroup(
                        new WallPostWithMessage(
                            new WallPostWithAttachments(
                                new WallPostBase(
                                    client,
                                    actor
                                ),
                                new AttachmentArrays(
                                    actor,
                                    group,
                                    new AttachmentWallPhoto(
                                        client,
                                        actor,
                                        group,
                                        new UploadWallPhoto(
                                            client,
                                            servers.wallPhoto(),
                                            new ByteArrayFromFile(
                                                photo
                                            )
                                        )
                                    ),
                                    new AttachmentAudio(
                                        client,
                                        actor,
                                        group,
                                        new UploadAudio(
                                            client,
                                            servers.audios(),
                                            audio
                                        )
                                    )
                                )
                            ),
                            new MessageWithRandomQuote(
                                new RequestRandomQuote()
                                    .value()
                            )
                        )
                    ),
                    -group
                ).construct()
            );
    }

    @Override
    public WallPostQuery construct() throws Exception {
        return this.query
            .value()
            .construct();
    }

}
