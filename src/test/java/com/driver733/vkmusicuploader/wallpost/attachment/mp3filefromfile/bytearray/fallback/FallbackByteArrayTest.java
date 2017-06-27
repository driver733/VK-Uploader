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
package com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback;

import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagFromMp3File;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromAdvancedTag;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromFile;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public final class FallbackByteArrayTest {

    /**
     * Path to test album cover image.
     */
    private final Path path =
        Paths.get("src/test/resources/testAlbumCover.jpg");

    /**
     * Test mp3 file.
     */
    private final File audio = new File("src/test/resources/test.mp3");

    @Test
    public void advancedTag()
        throws InvalidDataException, IOException, UnsupportedTagException {
        MatcherAssert.assertThat(
            new FallbackByteArray(
                new ByteArrayImageFromAdvancedTag(
                    new AdvancedTagFromMp3File(
                        new Mp3File(this.audio)
                    )
                ),
                new ByteArrayImageFromFile(this.audio)
            ).firstValid(),
            Matchers.equalTo(
                Files.readAllBytes(this.path)
            )
        );
    }

    @Test
    public void file() throws IOException, URISyntaxException {
        MatcherAssert.assertThat(
            new FallbackByteArray(
                new ByteArrayImageFromFile(
                    this.path.toFile()
                ),
            new ByteArrayImageFromFile(this.audio)
        ).firstValid(),
            Matchers.equalTo(
                Files.readAllBytes(this.path)
            )
        );
    }

}
