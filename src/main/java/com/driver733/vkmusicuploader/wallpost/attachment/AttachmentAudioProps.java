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

import com.driver733.vkmusicuploader.wallpost.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.objects.audio.Audio;
import java.io.IOException;

/**
 * Saves the result of a successful
 *  audios upload.
 *
 *
 *
 * @since 0.1
 * @checkstyle MemberNameCheck (50 lines)
 */
@Immutable
public final class AttachmentAudioProps {

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
     */
    private final ImmutableProperties properties;

    /**
     * The name of the file.
     */
    private final String fileName;

    /**
     * {@link Audio} with audios ID and owner ID.
     */
    private final Audio audio;

    /**
     * Ctor.
     * @param audio The {@link Audio} with audios ID and owner ID.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audios files.
     * @param fileName The name of the file.
     * @checkstyle ParameterNameCheck (10 lines)
     */
    public AttachmentAudioProps(
        final Audio audio,
        final String fileName,
        final ImmutableProperties properties
    ) {
        this.properties = properties;
        this.audio = audio;
        this.fileName = fileName;
    }

    /**
     * Uploads the audios files.
     * @throws IOException If an exception occurs
     *  while loading/saving the properties.
     */
    public void saveProps()
        throws IOException {
        this.properties.setPropertyAndStore(
            this.fileName,
            String.format(
                "%s_%d_%d",
                AudioStatus.UPLOADED,
                this.audio.getOwnerId(),
                this.audio.getId()
            )
        );
    }

}
