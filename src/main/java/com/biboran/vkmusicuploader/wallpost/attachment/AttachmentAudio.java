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

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.audio.responses.AudioUploadResponse;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
public final class AttachmentAudio implements Attachment {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Audios files.
     */
    private final File[] audios;

    /**
     * Audio upload URL for the audio files.
     */
    private final String url;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param url Audio upload URL for the audio files.
     * @param audios Audios files.
     */
    public AttachmentAudio(
        final UserActor actor,
        final String url,
        final File... audios
    ) {
        this.audios = audios;
        this.actor = actor;
        this.url = url;
        this.client = new VkApiClient(
            new HttpTransportClient()
        );
    }

    /**
     * Uploads audios.
     * @return AudioSaveQueries that will save the audios.
     * @throws ClientException VK API Client error.
     * @throws ApiException VK API error.
     */
    public List<AudioAddQuery> upload()
        throws ClientException, ApiException {
        final List<AudioAddQuery> list = new ArrayList<>(
            this.audios.length
        );
        for (final File audio : this.audios) {
            list.addAll(
                this.upload(audio)
            );
        }
        return list;
    }

    /**
     * Uploads the audio files.
     * @param audio Audio file to upload.
     * @return AudioAddQuery that will add the uploaded audio to the group page.
     * @throws ApiException VK API error.
     * @throws ClientException VK API Client error.
     */
    private List<AudioAddQuery> upload(final File audio)
        throws ApiException, ClientException {
        final AudioUploadResponse response = this.client.upload().audio(
            this.url,
            audio
        ).execute();
        final Audio result = this.client.audio().save(
            this.actor,
            response.getServer(),
            response.getAudio(),
            response.getHash()
        ).execute();
        return Collections.singletonList(
            this.client.audio()
                .add(
                    this.actor,
                    result.getId(),
                    result.getOwnerId()
                )
                .groupId(AttachmentAudio.GROUP_ID)
        );
    }

}
