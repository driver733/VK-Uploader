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

package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback.Fallback;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo #13 Create tests for the class AttachmentWallPhoto.
 */
@Immutable
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
     * Multiple sources of the photos. The first valid one will be chosen.
     */
    private final Fallback<byte[]> photo;

    /**
     * WallPhoto upload URL for the photo construct.
     */
    private final String url;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param photo File that contains a photo. Typically an album toByteArray.
     * @param url WallPhoto upload URL for the photo construct.
     */
    public AttachmentWallPhoto(
        final UserActor actor,
        final Fallback<byte[]> photo,
        final String url
    ) {
        this.photo = photo;
        this.actor = actor;
        this.url = url;
        this.client = new VkApiClient(
            new TransportClientHttp()
        );
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws ClientException, ApiException, IOException {
        final List<AbstractQueryBuilder> result = new ArrayList<>(1);
        for (final byte[] element : this.photo.firstValid()) {
            final Path path;
            try {
                path = Files.write(
                    File.createTempFile("albumCover", ".jpg").toPath(),
                    element
                );
            } catch (final IOException ex) {
                throw new IOException(
                    "Failed to save album cover to byte array",
                    ex
                );
            }
            path.toFile().deleteOnExit();
            final WallUploadResponse response = this.client.upload()
                .photoWall(this.url, path.toFile())
                .execute();
            result.add(
                this.client.photos()
                    .saveWallPhoto(this.actor, response.getPhoto())
                    .server(response.getServer())
                    .hash(response.getHash())
                    .groupId(AttachmentWallPhoto.GROUP_ID)
            );
        }
        return result;
    }

}
