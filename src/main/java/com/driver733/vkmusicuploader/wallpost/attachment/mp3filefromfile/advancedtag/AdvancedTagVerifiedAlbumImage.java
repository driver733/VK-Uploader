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
package com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag;

import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v2;
import java.io.IOException;

/**
 * Verifies that the tag contains an album image.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AdvancedTagVerifiedAlbumImage implements AdvancedTag {

    /**
     * Origin.
     */
    private final AdvancedTag origin;

    /**
     * Ctor.
     * @param origin Origin.
     */
    public AdvancedTagVerifiedAlbumImage(final AdvancedTag origin) {
        this.origin = origin;
    }

    @Override
    public ID3v2 construct() throws IOException {
        final ID3v2 result = this.origin.construct();
        if (result.getAlbumImage() == null) {
            throw new IOException("No album byteArray found");
        }
        return result;
    }

}
