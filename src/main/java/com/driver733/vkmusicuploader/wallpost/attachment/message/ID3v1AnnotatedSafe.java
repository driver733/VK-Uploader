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
package com.driver733.vkmusicuploader.wallpost.attachment.message;

import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.NotSupportedException;

/**
 * Returns empty string if a tag is missing.
 *
 *
 *
 * @since 0.1
 * @checkstyle ParameterNameCheck (1000 lines)
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
        return this.tag.getVersion();
    }

    @Override
    public String getTrack() {
        return new TagSafe(
            this.tag.getTrack()
        ).value();
    }

    @Override
    public void setTrack(final String track) {
        throw new UnsupportedOperationException("#setTrack()");
    }

    @Override
    public String getArtist() {
        return new TagSafe(
            this.tag.getArtist()
        ).value();
    }

    @Override
    public void setArtist(final String artist) {
        throw new UnsupportedOperationException("#setArtist()");
    }

    @Override
    public String getTitle() {
        return new TagSafe(
            this.tag.getTitle()
        ).value();
    }

    @Override
    public void setTitle(final String title) {
        throw new UnsupportedOperationException("#setTitle()");
    }

    @Override
    public String getAlbum() {
        return new TagSafe(
            this.tag.getAlbum()
        ).value();
    }

    @Override
    public void setAlbum(final String album) {
        throw new UnsupportedOperationException("#setAlbum()");
    }

    @Override
    public String getYear() {
        return new TagSafe(
            this.tag.getYear()
        ).value();
    }

    @Override
    public void setYear(final String year) {
        throw new UnsupportedOperationException("#setYear()");
    }

    @Override
    public int getGenre() {
        return this.tag.getGenre();
    }

    @Override
    public void setGenre(final int genre) {
        throw new UnsupportedOperationException("#setGenre()");
    }

    @Override
    public String getGenreDescription() {
        return new TagSafe(
            this.tag.getGenreDescription()
        ).value();
    }

    @Override
    public String getComment() {
        return new TagSafe(
            this.tag.getComment()
        ).value();
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
     * @return Tag if it exists. Empty attachmentString otherwise.
     */
    private static String isPresent(final String str) {
      
    }

}
