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
 * @todo #20 Test other conditions (both valid and invalid).
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ID3v1AnnotatedSafeTest {

    @Test
    public void valid()
        throws InvalidDataException, IOException, UnsupportedTagException {
        final ID3v1 tag = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/test.mp3")
            ).construct()
        );
        MatcherAssert.assertThat(
            tag.getAlbum(),
            Matchers.equalTo(
                "Album: Elegant Testing"
            )
        );
        MatcherAssert.assertThat(
            tag.getArtist(),
            Matchers.equalTo(
                "Artist: Test Man"
            )
        );
    }

    @Test
    public void invalid()
        throws InvalidDataException, IOException, UnsupportedTagException {
        final ID3v1 tag = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File("src/test/resources/testMissingTags.mp3")
            ).construct()
        );
        MatcherAssert.assertThat(
            tag.getAlbum(),
            Matchers.equalTo("")
        );
        MatcherAssert.assertThat(
            tag.getArtist(),
            Matchers.equalTo("")
        );
    }

}
