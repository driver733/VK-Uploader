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
package com.driver733.vkmusicuploader.wallpost.attachment.message;

import com.driver733.vkmusicuploader.wallpost.attachment.message.messagepart.ID3v1AnnotatedSafe;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.basictag.BasicTagFromMp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ID3v1AnnotatedSafeTest {

    /**
     * A sample MP3 file with all tags.
     */
    private final ID3v1 tag;

    public ID3v1AnnotatedSafeTest()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/test.mp3")
            ).construct()
        );
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setTrack()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setTrack("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setArtist()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setArtist("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setTitle()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setTitle("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setAlbum()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setAlbum("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setYear()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setYear("");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setGenre()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setGenre(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void setComment()
        throws InvalidDataException, IOException, UnsupportedTagException {
        this.tag.setComment("");
    }

    @Test
    public void valid()
        throws InvalidDataException, IOException, UnsupportedTagException {
        MatcherAssert.assertThat(
            this.tag.getAlbum(),
            Matchers.equalTo(
                "Album: Elegant Testing"
            )
        );
        MatcherAssert.assertThat(
            this.tag.getArtist(),
            Matchers.equalTo(
                "Artist: Test Man"
            )
        );
        MatcherAssert.assertThat(
            this.tag.getVersion(),
            Matchers.equalTo("4.0")
        );
        MatcherAssert.assertThat(
            this.tag.getTrack(),
            Matchers.equalTo(
                "Track: 01/1"
            )
        );
        MatcherAssert.assertThat(
            this.tag.getTitle(),
            Matchers.equalTo(
                "Title: Test of MP3 File"
            )
        );
        MatcherAssert.assertThat(
            this.tag.getComment(),
            Matchers.equalTo(
                "Comment: Test comment"
            )
        );
        MatcherAssert.assertThat(
            this.tag.getGenre(),
            Matchers.equalTo(
                0
            )
        );
        MatcherAssert.assertThat(
            this.tag.getGenreDescription(),
            Matchers.equalTo(
                "Genre Description: Blues"
            )
        );
    }

    @Test
    public void invalid()
        throws InvalidDataException, IOException, UnsupportedTagException {
        final ID3v1 missing = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/testMissingTags.mp3")
            ).construct()
        );
        MatcherAssert.assertThat(
            missing.getAlbum(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getArtist(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getVersion(),
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            missing.getTrack(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getTitle(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getYear(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getComment(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            missing.getGenre(),
            Matchers.equalTo(-1)
        );
        MatcherAssert.assertThat(
            missing.getGenreDescription(),
            Matchers.equalTo("")
        );
    }

    /**
     * A @todo #21 Figure out why year tag is not working.
     */
    @Test
    public void fix() {
        if (this.tag.getYear() != null && !"".equals(this.tag.getYear())) {
            MatcherAssert.assertThat(
                this.tag.getYear(),
                Matchers.equalTo(
                    "Year: 2017"
                )
            );
        }
    }

}
