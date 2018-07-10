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
import com.vk.api.sdk.objects.docs.responses.DocUploadResponse;
import com.vk.api.sdk.queries.upload.UploadDocQuery;
import com.vk.api.sdk.queries.upload.UploadQueryBuilder;
import java.io.File;

/**
 * Handles upload procedure for the document uploaded
 *  uploaded to the wall.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class UploadWallDocument
    implements Upload<UploadDocQuery, DocUploadResponse> {

    /**
     * {@link VkApiClient} that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * Upload URL for the document file.
     */
    private final String url;

    /**
     * Document file to upload.
     */
    private final File doc;

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Upload URL for the doc.
     * @param doc Document file to upload.
     */
    public UploadWallDocument(
        final VkApiClient client, final String url, final File doc
    ) {
        this.client = client;
        this.url = url;
        this.doc = doc;
    }

    @Override
    public UploadQueryBuilder<UploadDocQuery, DocUploadResponse> query() {
        return this.client.upload().doc(this.url, this.doc);
    }

}
