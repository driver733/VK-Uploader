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
package com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.fields;

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.Attachment;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.support.IdsMap;
import com.driver733.vkmusicuploader.wallpost.attachment.support.PropertiesUpdate;
import com.driver733.vkmusicuploader.wallpost.attachment.support.QueryResultsBasic;
import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.strings.AttachmentsFromResults;
import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.QueriesFromAttachments;
import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.safe.QueriesSafeCached;
import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.safe.QueriesSafeNonCached;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientExecuteBatchCached;
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
 * @checkstyle ClassDataAbstractionCouplingCheck (20 lines)
 * @checkstyle ParameterNumberCheck (10 lines)
 */
@Immutable
public final class AttachmentArrays implements AttachmentsFields {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * Array of attachmentsFields.
     */
    private final Array<Attachment> attachments;

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
     * @param group Group ID.
     * @param attachments Attachments.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public AttachmentArrays(
        final UserActor actor,
        final ImmutableProperties properties,
        final int group,
        final Attachment... attachments
    ) {
        this.actor = actor;
        this.properties = properties;
        this.group = group;
        this.attachments = new Array<>(attachments);
    }

    @Override
    public List<String> attachmentsFields()
        throws ApiException, ClientException, IOException {
        this.properties.load();
        final List<AbstractQueryBuilder> queries = new QueriesFromAttachments(
            this.attachments
        ).queries();
        final IdsMap ids =
            new IdsMap(this.attachments);
        final JsonElement root =
            new VkApiClient(
                new TransportClientExecuteBatchCached(
                    new QueryResultsBasic(
                        new QueriesSafeCached(queries)
                    )
                )
            ).execute()
                .batch(
                    this.actor,
                    new QueriesSafeNonCached(queries).queries()
                ).execute();
        new PropertiesUpdate(
            this.properties,
            ids.idsMap(),
            root.getAsJsonArray()
        ).save();
        try {
            return new AttachmentsFromResults(
                root.getAsJsonArray(),
                this.group
            ).attachmentStrings();
        } catch (final IOException ex) {
            throw new IOException(
                "Could not map idsMap queriesResults to attachments", ex
            );
        }
    }
}
