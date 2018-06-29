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

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadAudio;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines if audios has been already
 *  uploaded and returns a fake a real
 *  query accordingly.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo #6 Test this class.
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@Immutable
public final class AttachmentCachedAudio implements Attachment {

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
    private final Array<File> audios;

    /**
     * Audio upload URL for the audios files.
     */
    private final String url;

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param url Audio upload URL for the audios files.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audios files.
     * @param audios Audios files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public AttachmentCachedAudio(
        final VkApiClient client,
        final UserActor actor,
        final String url,
        final ImmutableProperties properties,
        final List<File> audios,
        final int group
    ) {
        this.client = client;
        this.audios = new Array<>(audios);
        this.actor = actor;
        this.url = url;
        this.properties = properties;
        this.group = group;
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws Exception {
        final List<AbstractQueryBuilder> list = new ArrayList<>(
            this.audios.size()
        );
        for (final File audio : this.audios) {
            list.addAll(
                this.upload(audio)
            );
        }
        return list;
    }

    /**
     * Forms a {@link AbstractQueryBuilder}
     *  for uploading an audios {@link File}.
     * @param audio Audio {@link File} to upload.
     * @return A {@link List} with a single {@link AbstractQueryBuilder}
     *  which uploads the audios.
     * @throws ApiException VK API error.
     * @throws ClientException VK Client error.
     * @throws Exception If the {@link AudioStatus} is invalid.
     * @checkstyle LocalFinalVariableNameCheck (20 lines)
     * @checkstyle StringLiteralsConcatenationCheck (100 lines)
     * @checkstyle LocalFinalVariableNameCheck (100 lines)
     */
    private List<AbstractQueryBuilder> upload(final File audio)
        throws Exception {
        final List<AbstractQueryBuilder> result;
        if (
            this.properties.getProperty(
            audio.getName()
        ) == null
            ) {
            result = new AttachmentAudio(
                this.client,
                this.actor,
                this.properties,
                this.group,
                new UploadAudio(
                    this.client,
                    this.url,
                    audio
                )
                ).upload();
        } else {
            final String value = this.properties.getProperty(
                audio.getName()
            );
            final int status = Integer.parseInt(
                value.substring(
                    0,
                    StringUtils.ordinalIndexOf(
                        value,
                        "_",
                        1
                    )
                )
            );
            if (status == 0) {
                final Integer ownerId = Integer.parseInt(
                    value.substring(
                        StringUtils.ordinalIndexOf(
                            value,
                            "_",
                            1
                        ) + 1,
                        StringUtils.ordinalIndexOf(
                            value,
                            "_",
                            2
                        )
                    )
                );
                final int mediaId = Integer.parseInt(
                    value.substring(
                        StringUtils.ordinalIndexOf(
                            value,
                            "_",
                            2
                        ) + 1
                    )
                );
                result = new AttachmentAddAudio(
                    this.client,
                    this.actor,
                    ownerId,
                    mediaId,
                    this.group
                ).upload();
            } else if (status == 1) {
                final String mediaId = value.substring(
                    StringUtils.ordinalIndexOf(
                        value,
                        "_",
                        1
                    ) + 1
                );
                final AudioAddQuery query =
                    new AudioAddQuery(
                        new VkApiClient(
                            new TransportClientCached(
                                String.format(
                                    "{ \"response\" : %s }", mediaId
                                )
                            )
                        ),
                        new UserActor(
                            0,
                            ""
                        ),
                        0,
                        0
                    );
                result = Collections.singletonList(query);
            } else {
                throw new IOException("Invalid audios status");
            }
        }
        return result;
    }

}
