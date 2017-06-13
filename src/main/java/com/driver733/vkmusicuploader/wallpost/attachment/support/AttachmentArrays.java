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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.driver733.vkmusicuploader.support.CachedExecuteBatchQueryClient;
import com.driver733.vkmusicuploader.support.ImmutableProperties;
import com.driver733.vkmusicuploader.support.QueryResults;
import com.driver733.vkmusicuploader.wallpost.attachment.Attachment;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.IOException;
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
public final class AttachmentArrays {

    /**
     * Array of attachmentsFields.
     */
    private final Attachment[] attachments;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Properties that contain the {@link AudioStatus} of audio files.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audio files.
     * @param attachments Attachments.
     */
    public AttachmentArrays(
        final UserActor actor,
        final ImmutableProperties properties,
        final Attachment... attachments
    ) {
        this.attachments = attachments;
        this.actor = actor;
        this.properties = properties;
    }

    /**
     * Constructs Attachment strings for the wall WallPostAlbum.
     * @return Attachment strings.
     * @throws ClientException VK API Client error.
     * @throws ApiException VK API error.
     * @throws IOException If properties fail to load.
     */
    public List<String> attachmentsFields()
        throws ApiException, ClientException, IOException {
        try {
            this.properties.load();
        } catch (final IOException ex) {
            throw new IOException("Could not load properties", ex);
        }
        final QueriesFromAttachments queries =
            new QueriesFromAttachments(this.attachments);
        final JsonElement root =
            new VkApiClient(
                new CachedExecuteBatchQueryClient(
                    new QueryResults(queries.queriesResults())
                        .results()
                )
            ).execute()
                .batch(this.actor, queries.nonCachedQueries())
                .execute();
        new PropertiesUpdate(
            this.properties,
            queries.idsMap(),
            root.getAsJsonArray()
        ).save();
        try {
            this.properties.store();
        } catch (final IOException ex) {
            throw new IOException("Could not store properties", ex);
        }
        try {
            return new AttachmentsFromResults(
                root.getAsJsonArray()
            ).attachments();
        } catch (final IOException ex) {
            throw new IOException(
                "Could not map idsMap queriesResults to attachments", ex
            );
        }
    }

}
