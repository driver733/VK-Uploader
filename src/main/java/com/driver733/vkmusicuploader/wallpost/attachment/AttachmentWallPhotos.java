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
package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Determines if audio has been already
 *  uploaded and returns a fake a real
 *  query accordingly.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo #6 Test the class.
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@Immutable
public final class AttachmentWallPhotos implements Attachment {

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
    private final Array<File> photos;

    /**
     * Audio upload URL for the audio files.
     */
    private final String url;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param url Audio upload URL for the audio files.
     * @param photos Audios files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public AttachmentWallPhotos(
        final VkApiClient client,
        final UserActor actor,
        final String url,
        final List<File> photos,
        final int group
    ) {
        this.client = client;
        this.photos = new Array<>(photos);
        this.actor = actor;
        this.url = url;
        this.group = group;
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws Exception {
        final List<AbstractQueryBuilder> list = new ArrayList<>(
            this.photos.size()
        );
        for (final File photo : this.photos) {
            final List<AbstractQueryBuilder> queries;
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
     * @param photo Audio {@link File} to upload.
     * @return A {@link List} with a single {@link AbstractQueryBuilder}
     *  which uploads the photo.
     * @throws ApiException VK API error.
     * @throws ClientException VK Client error.
     * @throws Exception If the {@link AudioStatus} is invalid
     * @checkstyle LocalFinalVariableNameCheck (20 lines)
     * @checkstyle StringLiteralsConcatenationCheck (100 lines)
     * @checkstyle LocalFinalVariableNameCheck (100 lines)
     */
    private List<AbstractQueryBuilder> upload(final File photo)
        throws Exception {
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
