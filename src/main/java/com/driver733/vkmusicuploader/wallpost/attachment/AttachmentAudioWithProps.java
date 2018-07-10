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

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.Upload;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadAudio;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.audio.responses.AudioUploadResponse;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import com.vk.api.sdk.queries.upload.UploadAudioQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Upload the audios, caches the upload result
 *  in {@link java.util.Properties} and returns
 *  {@link AudioAddQuery} for the uploaded
 *  audios.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AttachmentAudioWithProps implements Attachment {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * {@link VkApiClient} that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Audios files.
     */
    private final Array<Upload<UploadAudioQuery, AudioUploadResponse>> audios;

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
     */
    private final ImmutableProperties properties;

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param audios Audios files.
     * @param group Group ID.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audios files.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public AttachmentAudioWithProps(
        final VkApiClient client,
        final UserActor actor,
        final ImmutableProperties properties,
        final int group,
        final UploadAudio... audios
    ) {
        this.client = client;
        this.actor = actor;
        this.properties = properties;
        this.group = group;
        this.audios = new Array<>(audios);
    }

    @Override
    public List<AbstractQueryBuilder> upload()
        throws Exception {
        final List<AbstractQueryBuilder> list = new ArrayList<>(
            this.audios.size()
        );
        for (final Upload<UploadAudioQuery, AudioUploadResponse> audio
            : this.audios) {
            list.addAll(
                this.upload(audio)
            );
        }
        return list;
    }

    /**
     * Uploads the audios files.
     * @param upload Audio construct to upload.
     * @return AudioAddQuery that will add the uploaded audios
     *  to the group page.
     * @throws Exception If a query cannot be created.
     */
    private List<AbstractQueryBuilder> upload(
        final Upload<UploadAudioQuery, AudioUploadResponse> upload
    ) throws Exception {
        this.properties.load();
        final String filename = upload.query()
            .fileName();
        final AudioUploadResponse response = upload.query()
            .execute();
        final Audio audio = this.client
            .audio()
            .save(
                this.actor,
                response.getServer(),
                response.getAudio(),
                response.getHash()
            ).execute();
        new AttachmentAudioProps(
            audio,
            filename,
            this.properties
        ).saveProps();
        return new Array<>(
            new AudioAddQuery(
                this.client,
                this.actor,
                audio.getId(),
                audio.getOwnerId()
            ).groupId(this.group)
        );
    }

}
