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
package com.driver733.vkmusicuploader.wallpost.attachment.upload;

import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.queries.upload.UploadPhotoWallQuery;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.Bytes;
import org.cactoos.io.BytesOf;

/**
 * Constructs a query for uploading a photo
 *  to the wall.
 *
 * @since 0.1
 */
@Immutable
public final class UploadWallPhoto
    implements Upload<UploadPhotoWallQuery, WallUploadResponse> {

    /**
     * {@link VkApiClient} that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * WallPhoto upload URL for the photo construct.
     */
    private final String url;

    /**
     * Multiple sources of the files. The first valid one will be chosen.
     */
    private final Bytes photo;

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Wall Photo upload URL for the photo construct.
     * @param photo Multiple sources of the files.
     *  The first valid one will be chosen.
     */
    public UploadWallPhoto(
        final VkApiClient client,
        final String url,
        final Bytes photo
    ) {
        this.client = client;
        this.url = url;
        this.photo = photo;
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Wall Photo upload URL for the photo construct.
     * @param photo Photo to upload.
     */
    public UploadWallPhoto(
        final VkApiClient client,
        final String url,
        final byte[] photo
    ) {
        this(
            client,
            url,
            () -> photo
        );
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Wall Photo upload URL for the photo construct.
     * @param photo Photo file.
     */
    public UploadWallPhoto(
        final VkApiClient client,
        final String url,
        final File photo
    ) {
        this(
            client,
            url,
            new BytesOf(photo)
        );
    }

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Wall Photo upload URL for the photo construct.
     * @param photo Photo file.
     */
    public UploadWallPhoto(
        final VkApiClient client,
        final String url,
        final Path photo
    ) {
        this(
            client,
            url,
            new BytesOf(photo)
        );
    }

    /**
     * Constructs a query for uploading a photo to the wall.
     * @return A {@link UploadPhotoWallQuery}.
     * @throws Exception If an exception occurs while constructing a query.
     */
    @SuppressWarnings("PMD.AvoidBranchingStatementAsLastInLoop")
    public UploadPhotoWallQuery query() throws Exception {
        final Path path;
        try {
            path = Files.write(
                File.createTempFile(
                    "albumCover",
                    ".jpg"
                ).toPath(),
             this.photo.asBytes()
            );
        } catch (final IOException ex) {
            throw new IOException(
                "Failed to save album cover from byte array to file",
                ex
            );
        }
        path.toFile().deleteOnExit();
        return this.client
            .upload()
            .photoWall(
                this.url,
                path.toFile()
            );
    }

}
