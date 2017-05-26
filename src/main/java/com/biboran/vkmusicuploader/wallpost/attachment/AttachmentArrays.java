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
     * @param attachments Attachments.
     */
    public AttachmentArrays(
        final UserActor actor,
        final Attachment... attachments
    ) {
        this.attachments = attachments;
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
        final JsonElement element = this.client.execute()
            .batch(this.actor, queries)
            .execute();
        try {
            return mapResultsToAttachments(element);
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Maps queries results to Attachment strings.
     * @param root JsonArray that contains the results of the queries.
     * @return Attachment strings.
     * @throws IOException If unknown Attachment format is found.
     */
    private static List<String> mapResultsToAttachments(
        final JsonElement root
    ) throws IOException {
        final JsonArray array = root.getAsJsonArray();
        final List<String> list = new ArrayList<>(array.size());
        for (final JsonElement element : array) {
            addAttachmentStringsFromElements(list, element);
        }
        return list;
    }

    /**
     * Extracts attachment string from the provided Json Elements.
     * @param list List in which attachment strings will be saved.
     * @param root Json Element.
     * @throws IOException If attachment string cannot be extracted.
     */
    private static void addAttachmentStringsFromElements(
        final List<String> list,
        final JsonElement root
    ) throws IOException {
        try {
            if (root.isJsonArray()) {
                final JsonArray array = root.getAsJsonArray();
                for (final JsonElement element : array) {
                    list.add(
                        jsonObjectToAttachmentFormat(
                        element.getAsJsonObject()
                        )
                    );
                }
            } else if (root.isJsonPrimitive()) {
                list.add(audioId(root));
            } else {
                list.add(
                    jsonObjectToAttachmentFormat(
                        root.getAsJsonObject()
                    )
                );
            }
        } catch (final IOException ex) {
            throw new IOException("Could not extract attachment string.", ex);
        }
    }

    /**
     * Constructs an Attachment string based on the JsonObject.
     * @param object JsonObject that contains the response of the query.
     * @return Attachment string.
     * @throws IOException if unknown Attachment format is found.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    private static String jsonObjectToAttachmentFormat(
        final JsonObject object
    ) throws IOException {
        final AttachmentFormat format;
        if (object.has("artist")) {
            format = new AttachmentFormat(
                AttachmentFormat.AttachmentType.AUDIO,
                object.get("owner_id").getAsInt(),
                object.get("id").getAsInt()
            );
        } else if (object.has("photo_75")) {
            format = new AttachmentFormat(
                AttachmentFormat.AttachmentType.PHOTO,
                object.get("owner_id").getAsInt(),
                object.get("id").getAsInt()
            );
        } else {
            throw new IOException("Unknown AttachmentFormat");
        }
        return format.toString();
    }

    /**
     * Constructs an Attachment string for the wall Post.
     * @param element Json Element that contains media ID
     *  of the audio that was added to the group page.
     * @return Attachment string.
     */
    private static String audioId(final JsonElement element) {
        return new AttachmentFormat(
            AttachmentFormat.AttachmentType.AUDIO,
            element.getAsInt()
        ).toString();
    }

}
