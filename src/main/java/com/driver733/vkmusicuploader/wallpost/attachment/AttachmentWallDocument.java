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
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallDocument;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.docs.responses.DocUploadResponse;
import com.vk.api.sdk.queries.upload.UploadDocQuery;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Attachment of a document that had been uploaded
 *  to the wall.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AttachmentWallDocument implements Attachment {

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Provides a query for uploading the doc.
     */
    private final Upload<UploadDocQuery, DocUploadResponse> doc;

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param doc File that contains a doc. Typically an album byteArray.
     */
    public AttachmentWallDocument(
        final VkApiClient client,
        final UserActor actor,
        final UploadWallDocument doc
    ) {
        this.client = client;
        this.actor = actor;
        this.doc = doc;
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws Exception {
        final DocUploadResponse response =
            this.doc.query()
                .execute();
        return new ListOf<>(
            this.client.docs().save(
                this.actor,
                response.getFile()
            )
        );
    }

}
