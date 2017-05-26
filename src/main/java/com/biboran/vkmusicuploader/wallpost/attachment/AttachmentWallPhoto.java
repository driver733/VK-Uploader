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

package com.biboran.vkmusicuploader.wallpost.attachment;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.queries.photos.PhotosSaveWallPhotoQuery;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
public final class AttachmentWallPhoto implements Attachment {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * File that contains a photo. Typically an album image.
     */
    private final byte[] photo;

    /**
     * WallPhoto upload URL for the photo file.
     */
    private final String url;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param photo File that contains a photo. Typically an album image.
     * @param url WallPhoto upload URL for the photo file.
     */
    public AttachmentWallPhoto(
        final UserActor actor,
        final byte[] photo,
        final String url
    ) {
        this.photo = Arrays.copyOf(photo, photo.length);
        this.actor = actor;
        this.url = url;
        this.client = new VkApiClient(
            new HttpTransportClient()
        );
    }

    /**
     * Uploads the photo to the wall.
     * @return PhotosSaveWallPhotoQuery that will save the wall Post photo.
     * @throws ClientException VK API Client error.
     * @throws ApiException VK API error.
     */
    public List<PhotosSaveWallPhotoQuery> upload()
        throws ClientException, ApiException {
        final Path path;
        try {
            path = Files.write(
                File.createTempFile("albumCover", ".jpg").toPath(),
                this.photo
            );
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
        path.toFile().deleteOnExit();
        final WallUploadResponse response = this.client.upload()
            .photoWall(this.url, path.toFile())
            .execute();
        return Collections.singletonList(
            this.client.photos()
                .saveWallPhoto(this.actor, response.getPhoto())
                .server(response.getServer())
                .hash(response.getHash())
                .groupId(AttachmentWallPhoto.GROUP_ID)
        );
    }

}
