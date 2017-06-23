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
package com.driver733.vkmusicuploader.wallpost.attachment.message.messagepart;

import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.NotSupportedException;

//@checkstyle ParameterNameCheck (1000 lines)

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@SuppressWarnings({"PMD.BooleanGetMethodName", "PMD.ExcessivePublicCount"})
public final class ID3v1AnnotatedSafe implements ID3v1 {

    /**
     * Origin.
     */
    private final ID3v1 tag;

    /**
     * Ctor.
     * @param tag Origin.
     */
    public ID3v1AnnotatedSafe(final ID3v1 tag) {
        this.tag = tag;
    }

    @Override
    public String getVersion() {
        final String result;
        final String value = this.tag.getVersion();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = String.format("Version: %s", value);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public String getTrack() {
        final String result;
        final String value = this.tag.getTrack();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = value;
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setTrack(final String track) {
        throw new UnsupportedOperationException("#setTrack()");
    }

    @Override
    public String getArtist() {
        final String result;
        final String value = this.tag.getArtist();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = String.format("Artist: %s", value);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setArtist(final String artist) {
        throw new UnsupportedOperationException("#setArtist()");
    }

    @Override
    public String getTitle() {
        final String result;
        final String value = this.tag.getTitle();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = value;
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setTitle(final String title) {
        throw new UnsupportedOperationException("#setTitle()");
    }

    @Override
    public String getAlbum() {
        final String result;
        final String value = this.tag.getAlbum();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = String.format("Album: %s", value);
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setAlbum(final String album) {
        throw new UnsupportedOperationException("#setAlbum()");
    }

    @Override
    public String getYear() {
        final String result;
        final String value = this.tag.getYear();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = value;
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setYear(final String year) {
        throw new UnsupportedOperationException("#setYear()");
    }

    @Override
    public int getGenre() {
        throw new UnsupportedOperationException("#getGenre()");
    }

    @Override
    public void setGenre(final int genre) {
        throw new UnsupportedOperationException("#setGenre()");
    }

    @Override
    public String getGenreDescription() {
        final String result;
        final String value = this.tag.getGenreDescription();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = value;
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public String getComment() {
        final String result;
        final String value = this.tag.getComment();
        if (ID3v1AnnotatedSafe.isPresent(value)) {
            result = value;
        } else {
            result = "";
        }
        return result;
    }

    @Override
    public void setComment(final String comment) {
        throw new UnsupportedOperationException("#setComment()");
    }

    @Override
    public byte[] toBytes() throws NotSupportedException {
        return this.tag.toBytes();
    }

    /**
     * Checks if the tag exists.
     * @param str Tag.
     * @return Tag if it exists. Empty string otherwise.
     */
    private static boolean isPresent(final String str) {
        final boolean result;
        if (str == null || str.isEmpty() || "-1".equals(str)) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
