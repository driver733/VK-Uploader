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

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.objects.audio.responses.AudioUploadResponse;
import com.vk.api.sdk.queries.upload.UploadAudioQuery;
import com.vk.api.sdk.queries.upload.UploadQueryBuilder;
import java.io.File;
import java.io.IOException;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
public final class UploadAudio
    implements Upload<UploadAudioQuery, AudioUploadResponse> {

    /**
     * {@link VkApiClient} that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * Upload URL for the audio.
     */
    private final String url;

    /**
     * Audio file to upload.
     */
    private final File audio;

    /**
     * Ctor.
     * @param client The {@link VkApiClient}
     *  that is used for all VK API requests.
     * @param url Upload URL for the audio.
     * @param audio Audio file to upload.
     */
    public UploadAudio(
        final VkApiClient client, final String url, final File audio
    ) {
        this.client = client;
        this.url = url;
        this.audio = audio;
    }

    @Override
    public UploadQueryBuilder<UploadAudioQuery, AudioUploadResponse> query()
        throws IOException {
        return this.client.upload().audio(this.url, this.audio);
    }

}
