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
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallPhotos;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.fields.AttachmentArrays;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.util.List;

/**
 * Creates a {@link WallPost} with the specified
 *  photos.
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
@Immutable
public final class WallPostPhotoAlbum implements WallPost {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * {@link VkApiClient} for all requests.
     */
    private final VkApiClient client;

    /**
     * Photos files.
     */
    private final Array<File> photos;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadUrls servers;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param photos Audio files.
     * @param servers Upload servers
     *  that provide upload URLs for attachmentsFields.
     *  {@link AudioStatus} of audios files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostPhotoAlbum(
        final VkApiClient client,
        final UserActor actor,
        final List<File> photos,
        final UploadUrls servers,
        final int group
    ) {
        this.client = client;
        this.photos = new Array<>(photos);
        this.actor = actor;
        this.servers = servers;
        this.group = group;
    }

    /**
     * Constructs a WallPostQuery for a wall WallPostAlbum.
     * @return WallPostQuery.
     * @throws Exception If an exception occurs
     *  while constructing the {@link WallPost}.
     */
    public WallPostQuery construct() throws Exception {
        return new WallPostWithOwnerId(
            new WallPostFromGroup(
                new WallPostWithAttachments(
                    new WallPostBase(
                        this.client,
                        this.actor
                    ),
                    new AttachmentArrays(
                        this.actor,
                        this.group,
                        new AttachmentWallPhotos(
                            this.client,
                            this.actor,
                            this.servers.wallPhoto(),
                            this.photos,
                            this.group
                        )
                    )
                )
            ),
            -this.group
        ).construct();
    }

}
