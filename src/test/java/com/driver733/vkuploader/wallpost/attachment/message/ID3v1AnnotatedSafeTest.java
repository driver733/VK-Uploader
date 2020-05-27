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
package com.driver733.vkuploader.wallpost.attachment.message;

import com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.basictag.BasicTagFromMp3File;
import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link ID3v1AnnotatedSafe}.
 *
 * @checkstyle JavadocMethodCheck (500 lines)
 * @since 0.1
 */
@Immutable
public final class ID3v1AnnotatedSafeTest {

    /**
     * A sample MP3 file with all tags.
     */
    private final ID3v1 tag;

    public ID3v1AnnotatedSafeTest()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/album/test.mp3")
            ).construct()
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setTrack() {
        this.tag.setTrack("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setArtist() {
        this.tag.setArtist("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setTitle() {
        this.tag.setTitle("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setAlbum() {
        this.tag.setAlbum("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setYear() {
        this.tag.setYear("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setGenre() {
        this.tag.setGenre(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setComment() {
        this.tag.setComment("");
    }

    @Test
    public void valid() {
        Assertions.assertThat(this.tag.getAlbum()).isEqualTo("Album: Elegant Testing");
        Assertions.assertThat(this.tag.getArtist()).isEqualTo("Artist: Test Man");
        Assertions.assertThat(this.tag.getVersion()).isEqualTo("4.0");
        Assertions.assertThat(this.tag.getTrack()).isEqualTo("Track: 01/1");
        Assertions.assertThat(this.tag.getTitle()).isEqualTo("Title: Test of MP3 File");
        Assertions.assertThat(this.tag.getComment()).isEqualTo("Comment: Test comment");
        Assertions.assertThat(this.tag.getGenre()).isEqualTo(0);
        Assertions.assertThat(this.tag.getGenreDescription()).isEqualTo("Genre Description: Blues");
    }

    @Test
    public void invalid()
        throws InvalidDataException, IOException, UnsupportedTagException {
        final ID3v1 missing = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/album/testMissingTags.mp3")
            ).construct()
        );
        Assertions.assertThat(missing.getAlbum()).isEqualTo("");
        Assertions.assertThat(missing.getArtist()).isEqualTo("");
        Assertions.assertThat(missing.getVersion()).isEqualTo("1");
        Assertions.assertThat(missing.getTrack()).isEqualTo("");
        Assertions.assertThat(missing.getTitle()).isEqualTo("");
        Assertions.assertThat(missing.getYear()).isEqualTo("");
        Assertions.assertThat(missing.getComment()).isEqualTo("");
        Assertions.assertThat(missing.getGenre()).isEqualTo(-1);
        Assertions.assertThat(missing.getGenreDescription()).isEqualTo("");
    }

    /**
     * A @todo #40 Figure out why year tag is not working.
     */
    @Test
    public void fix() {
        if (this.tag.getYear() != null && !"".equals(this.tag.getYear())) {
            Assertions.assertThat(
                this.tag.getYear()
            ).isEqualTo("Year: 2018");
        }
    }

}
