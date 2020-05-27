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
package com.driver733.vkuploader.wallpost.attachment;

import com.driver733.vkuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.queries.photos.PhotosSaveWallPhotoQuery;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Determines if audios has been already
 * uploaded and returns a fake a real
 * query accordingly.
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @since 0.1
 */
@Immutable
public final class AttachmentWallPhotos implements
    Attachment<PhotosSaveWallPhotoQuery, List<Photo>> {

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
     * Audios files.
     */
    private final List<Path> photos;

    /**
     * Audio upload URL for the audios files.
     */
    private final String url;

    /**
     * Ctor.
     *
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param url Audio upload URL for the audios files.
     * @param photos Audios files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public AttachmentWallPhotos(
        final VkApiClient client,
        final UserActor actor,
        final String url,
        final List<Path> photos,
        final int group
    ) {
        this.client = client;
        this.photos = photos;
        this.actor = actor;
        this.url = url;
        this.group = group;
    }

    @Override
    public List<AbstractQueryBuilder<PhotosSaveWallPhotoQuery, List<Photo>>> upload()
        throws Exception {
        final List<AbstractQueryBuilder<PhotosSaveWallPhotoQuery, List<Photo>>> list =
            new ArrayList<>(
                this.photos.size()
            );
        for (final Path photo : this.photos) {
            final List<AbstractQueryBuilder<PhotosSaveWallPhotoQuery, List<Photo>>> queries;
            try {
                queries = this.upload(photo);
            } catch (final IOException ex) {
                throw new IOException(
                    "Failed to get upload query for photo upload",
                    ex
                );
            }
            list.addAll(queries);
        }
        return list;
    }

    /**
     * Forms a {@link AbstractQueryBuilder} for uploading an photo {@link File}.
     *
     * @param photo Audio {@link File} to upload.
     * @return A {@link List} with a single {@link AbstractQueryBuilder}
     *  which uploads the photo.
     * @throws ApiException VK API error.
     * @throws ClientException VK Client error.
     * @throws Exception If the {@link AudioStatus} is invalid
     */
    private List<AbstractQueryBuilder<PhotosSaveWallPhotoQuery, List<Photo>>> upload(
        final Path photo
    ) throws Exception {
        return new AttachmentWallPhoto(
            this.client,
            this.actor,
            this.group,
            new UploadWallPhoto(
                this.client,
                this.url,
                photo
            )
        ).upload();
    }

}
