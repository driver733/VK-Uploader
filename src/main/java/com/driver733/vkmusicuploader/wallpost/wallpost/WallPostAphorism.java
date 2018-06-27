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
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.driver733.vkmusicuploader.post.UploadUrls;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallPhoto;
import com.driver733.vkmusicuploader.wallpost.attachment.message.MessageWithRandomQuote;
import com.driver733.vkmusicuploader.wallpost.attachment.support.RandomImage;
import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.fields.AttachmentArrays;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;

/**
 * A Wallpost with a random quote
 *  and a random image.
 *
 * @author Mikhail Yakushin (yakushin@terpmail.umd.edu)
 * @version $Id$
 * @since 0.2
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
@Immutable
public final class WallPostAphorism implements WallPost {

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadUrls servers;

    /**
     * Group ID.
     */
    private final int group;

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param servers Upload servers that provide upload URLs
     *  for attachmentsFields.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostAphorism(
        final VkApiClient client,
        final UserActor actor,
        final UploadUrls servers,
        final int group
    ) {
        this.client = client;
        this.actor = actor;
        this.servers = servers;
        this.group = group;
    }

    @Override
    public WallPostQuery construct() throws Exception {
        return new WallPostWithOwnerId(
            new WallPostFromGroup(
                new WallPostWithMessage(
                    new WallPostWithAttachments(
                        new WallPostBase(
                            this.client,
                            this.actor
                        ),
                        new AttachmentArrays(
                            this.actor,
                            this.group,
                            new AttachmentWallPhoto(
                                this.client,
                                this.actor,
                                this.group,
                                new UploadWallPhoto(
                                    this.client,
                                    this.servers.wallPhoto(),
                                    new RandomImage()
                                )
                            )
                        )
                    ),
                    new MessageWithRandomQuote()
                )
            ),
            -this.group
        ).construct();
    }

}
