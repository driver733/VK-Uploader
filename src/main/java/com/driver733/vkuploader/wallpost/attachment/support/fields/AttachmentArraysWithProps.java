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
package com.driver733.vkuploader.wallpost.attachment.support.fields;

import com.driver733.vkuploader.wallpost.PropsFile;
import com.driver733.vkuploader.wallpost.attachment.Attachment;
import com.driver733.vkuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkuploader.wallpost.attachment.support.IdsMap;
import com.driver733.vkuploader.wallpost.attachment.support.PropertiesUpdate;
import com.driver733.vkuploader.wallpost.attachment.support.QueryResultsBasic;
import com.driver733.vkuploader.wallpost.attachment.support.queries.QueriesFromAttachments;
import com.driver733.vkuploader.wallpost.attachment.support.queries.safe.QueriesSafeCached;
import com.driver733.vkuploader.wallpost.attachment.support.queries.safe.QueriesSafeNonCached;
import com.driver733.vkuploader.wallpost.attachment.support.strings.AttachmentsFromResults;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientExecuteBatchCached;
import java.io.IOException;
import java.util.List;
import org.cactoos.list.StickyList;

/**
 * Returns attachment strings from
 *  all of the specified attachments.
 *
 * Saves the results in specified {@link java.util.Properties}.
 *
 *
 *
 * @since 0.2
 * @checkstyle ClassDataAbstractionCouplingCheck (20 lines)
 * @checkstyle ParameterNumberCheck (10 lines)
 */
@Immutable
public final class AttachmentArraysWithProps implements AttachmentsFields {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * Array of attachmentsFields.
     */
    private final List<Attachment> attachments;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
     */
    private final PropsFile properties;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audios files.
     * @param group Group ID.
     * @param attachments Attachments.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public AttachmentArraysWithProps(
        final UserActor actor,
        final PropsFile properties,
        final int group,
        final Attachment... attachments
    ) {
        this.actor = actor;
        this.properties = properties;
        this.group = group;
        this.attachments = new StickyList<>(
            attachments
        );
    }

    @Override
    public List<String> attachmentsFields()
        throws Exception {
        final List<AbstractQueryBuilder> queries = new QueriesFromAttachments(
            this.attachments
        ).queries();
        final IdsMap ids =
            new IdsMap(
                queries
            );
        final JsonElement root =
            new VkApiClient(
                new TransportClientExecuteBatchCached(
                    new QueryResultsBasic(
                        new QueriesSafeCached(
                            queries
                        )
                    )
                )
            ).execute()
                .batch(
                    this.actor,
                    new QueriesSafeNonCached(
                        queries
                    ).queries()
                ).execute();
        new PropertiesUpdate(
            this.properties,
            ids.value(),
            root.getAsJsonArray()
        ).run();
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
