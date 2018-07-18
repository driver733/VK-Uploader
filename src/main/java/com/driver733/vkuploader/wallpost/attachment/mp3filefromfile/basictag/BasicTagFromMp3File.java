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
package com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.basictag;

import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;
import java.io.IOException;

/**
 * A {@link ID3v1} from an MP3 file.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class BasicTagFromMp3File implements BasicTag {

    /**
     * Origin.
     */
    private final Mp3File file;

    /**
     * Ctor.
     * @param file The {@link Mp3File} with tags.
     */
    public BasicTagFromMp3File(final Mp3File file) {
        this.file = file;
    }

    @Override
    public ID3v1 construct() throws IOException {
        final ID3v1 result;
        if (this.file.hasId3v1Tag()) {
            result = this.file.getId3v1Tag();
        } else if (this.file.hasId3v2Tag()) {
            result = this.file.getId3v2Tag();
        } else {
            throw new IOException("No ID3v1 or ID3v2 tag found");
        }
        return result;
    }

}
