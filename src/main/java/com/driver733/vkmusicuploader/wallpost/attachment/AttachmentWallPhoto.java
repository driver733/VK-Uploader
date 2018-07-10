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

package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.wallpost.attachment.upload.Upload;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.queries.upload.UploadPhotoWallQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Attachment} with a photo uploaded
 *  to the wall.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AttachmentWallPhoto implements Attachment {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Provides a query for uploading the photo.
     */
    private final Upload<UploadPhotoWallQuery, WallUploadResponse> photo;

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param photo File that contains a photo. Typically an album byteArray.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public AttachmentWallPhoto(
        final VkApiClient client,
        final UserActor actor,
        final int group,
        final UploadWallPhoto photo
    ) {
        this.client = client;
        this.actor = actor;
        this.group = group;
        this.photo = photo;
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws Exception {
        final List<AbstractQueryBuilder> result = new ArrayList<>(1);
        final WallUploadResponse response =
            this.photo.query()
                .execute();
        result.add(
            this.client.photos()
                .saveWallPhoto(
                    this.actor,
                    response.getPhoto()
                ).server(
                    response.getServer()
                ).hash(
                    response.getHash()
                ).groupId(this.group)
        );
        return result;
    }

}
