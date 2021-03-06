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
package com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.advancedtag;

import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import java.io.IOException;

/**
 * Acquires a {@link ID3v2} tag from an MP3 file.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AdvancedTagFromMp3File implements AdvancedTag {

    /**
     * Origin.
     */
    private final Mp3File file;

    /**
     * Ctor.
     * @param file The {@link Mp3File} with tags.
     */
    public AdvancedTagFromMp3File(final Mp3File file) {
        this.file = file;
    }

    @Override
    public ID3v2 construct() throws IOException {
        if (this.file.hasId3v2Tag()) {
            return this.file.getId3v2Tag();
        } else {
            throw new IOException("No ID3v2 tag found");
        }
    }

}
