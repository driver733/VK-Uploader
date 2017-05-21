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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import java.io.IOException;
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
 */
public final class AttachmentArrays {

    /**
     * Array of attachmentsFields.
     */
    private final Attachment[] attachments;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param attachmentArrays Attachments.
     */
    public AttachmentArrays(
        final UserActor actor,
        final Attachment... attachmentArrays
    ) {
        this.attachments = attachmentArrays;
        this.actor = actor;
        this.client = new VkApiClient(
            new HttpTransportClient()
        );
    }

    /**
     * Constructs Attachment strings for the wall Post.
     * @return Attachment strings.
     * @throws ClientException VK API Client error.
     * @throws ApiException VK API error.
     */
    public List<String> attachmentsFields()
        throws ClientException, ApiException {
        final List<AbstractQueryBuilder> queries =
            new ArrayList<>(this.attachments.length);
        for (final Attachment attachment : this.attachments) {
            try {
                queries.addAll(attachment.upload());
            } catch (final ApiException | ClientException exception) {
                throw new IllegalStateException(exception);
            }
        }
        final JsonElement jsonElement = this.client.execute()
            .batch(this.actor, queries)
            .execute();
        try {
            return mapResultsToAttachments(jsonElement);
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Maps queries results to Attachment strings.
     * @param jsonElement JsonArray that contains the results of the queries.
     * @return Attachment strings.
     * @throws IOException If unknown Attachment format is found.
     */
    private static List<String> mapResultsToAttachments(
        final JsonElement jsonElement
    ) throws IOException {
        final JsonArray jsonArray = jsonElement.getAsJsonArray();
        final List<String> list = new ArrayList<>(jsonArray.size());
        for (final JsonElement jsonElementt : jsonArray) {
            if (jsonElementt.isJsonArray()) {
                final JsonArray array = jsonElementt.getAsJsonArray();
                for (final JsonElement innerElement : array) {
                    list.add(
                        jsonObjectToAttachmentFormat(
                            innerElement.getAsJsonObject()
                        )
                    );
                }
            } else if (jsonElementt.isJsonPrimitive()) {
                list.add(
                    audioId(jsonElementt)
                );
            } else {
                list.add(
                    jsonObjectToAttachmentFormat(
                        jsonElementt.getAsJsonObject()
                    )
                );
            }
        }
        return list;
    }

    /**
     * Constructs an Attachment string based on the JsonObject.
     * @param jsonObject JsonObject that contains the response of the query.
     * @return Attachment string.
     * @throws IOException if unknown Attachment format is found.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private static String jsonObjectToAttachmentFormat(
        final JsonObject jsonObject
    ) throws IOException {
        final AttachmentFormat format;
        if (jsonObject.has("artist")) {
            format = new AttachmentFormat(
                AttachmentFormat.AttachmentType.AUDIO,
                jsonObject.get("owner_id").getAsInt(),
                jsonObject.get("id").getAsInt()
            );
        } else if (jsonObject.has("photo_75")) {
            format = new AttachmentFormat(
                AttachmentFormat.AttachmentType.PHOTO,
                jsonObject.get("owner_id").getAsInt(),
                jsonObject.get("id").getAsInt()
            );
        } else {
            throw new IOException("Unknown AttachmentFormat");
        }
        return format.toString();
    }

    /**
     * Constructs an Attachment string for the wall Post.
     * @param jsonElement Json Element that contains media ID
     *  of the audio that was added to the group page.
     * @return Attachment string.
     */
    private static String audioId(final JsonElement jsonElement) {
        return new AttachmentFormat(
            AttachmentFormat.AttachmentType.AUDIO,
            jsonElement.getAsInt()
        ).toString();
    }

}
