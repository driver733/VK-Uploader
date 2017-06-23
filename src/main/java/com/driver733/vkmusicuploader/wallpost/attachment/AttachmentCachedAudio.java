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
package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo Test this class.
 */
@Immutable
public final class AttachmentCachedAudio implements Attachment {

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Audios files.
     */
    private final Array<File> audios;

    /**
     * Audio upload URL for the audio files.
     */
    private final String url;

    /**
     * Properties that contain the {@link AudioStatus} of audio files.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param url Audio upload URL for the audio files.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audio files.
     * @param audios Audios files.*
     * @checkstyle ParameterNumberCheck (2 lines)
     */
    public AttachmentCachedAudio(
        final UserActor actor,
        final String url,
        final ImmutableProperties properties,
        final List<File> audios
    ) {
        this.audios = new Array<>(audios);
        this.actor = actor;
        this.url = url;
        this.properties = properties;
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws ClientException, ApiException, IOException {
        final List<AbstractQueryBuilder> list = new ArrayList<>(
            this.audios.size()
        );
        for (final File audio : this.audios) {
            final List<AbstractQueryBuilder> queries;
            try {
                queries = this.upload(audio);
            } catch (final IOException ex) {
                throw new IOException(
                    "Failed to get upload query for audio upload",
                    ex
                );
            }
            list.addAll(queries);
        }
        return list;
    }

    // @checkstyle LocalFinalVariableNameCheck (100 lines)
    // @checkstyle StringLiteralsConcatenationCheck (100 lines)
    /**
     * Forms a {@link AbstractQueryBuilder} for uploading an audio {@link File}.
     * @param audio Audio {@link File} to upload.
     * @return A {@link List} with a single {@link AbstractQueryBuilder}
     *  which uploads the audio.
     * @throws ApiException VK API error.
     * @throws ClientException VK Client error.
     * @throws IOException If the {@link AudioStatus} is invalid.
     * @checkstyle LocalFinalVariableNameCheck (20 lines)
     */
    private List<AbstractQueryBuilder> upload(final File audio)
        throws ApiException, ClientException, IOException {
        final List<AbstractQueryBuilder> result;
        if (this.properties.getProperty(audio.getName()) == null) {
            result = new AttachmentAudio(
                this.actor,
                this.url,
                this.properties,
                audio
            ).upload();
        } else {
            final String value = this.properties.getProperty(audio.getName());
            final int status = Integer.parseInt(
                value.substring(
                    0,
                    StringUtils.ordinalIndexOf(value, "_", 1)
                )
            );
            if (status == 0) {
                final Integer ownerId = Integer.parseInt(
                    value.substring(
                        StringUtils.ordinalIndexOf(value, "_", 1) + 1,
                        StringUtils.ordinalIndexOf(value, "_", 2)
                    )
                );
                final int mediaId = Integer.parseInt(
                    value.substring(
                        StringUtils.ordinalIndexOf(value, "_", 2) + 1
                    )
                );
                result = new AttachmentAddAudio(
                    this.actor,
                    ownerId,
                    mediaId
                ).upload();
            } else if (status == 1) {
                final int mediaId = Integer.parseInt(
                    value.substring(
                        StringUtils.ordinalIndexOf(value, "_", 1) + 1
                    )
                );
                final AbstractQueryBuilder query =
                    new CachedAudioAddQuery(mediaId);
                query.unsafeParam("audio_id", mediaId);
                result = Collections.singletonList(query);
            } else {
                throw new IOException("Invalid audio status");
            }
        }
        return result;
    }
}
