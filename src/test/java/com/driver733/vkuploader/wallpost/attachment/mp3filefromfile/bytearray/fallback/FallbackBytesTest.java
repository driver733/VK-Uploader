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
package com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback;

import com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagFromMp3File;
import com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayFromFile;
import com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromAdvancedTag;
import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.Mp3File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link FallbackBytes}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@Immutable
public final class FallbackBytesTest {

    /**
     * Path to test album cover image.
     */
    private final Path path =
        Paths.get("src/test/resources/album/albumCover.jpg");

    /**
     * Test mp3 file.
     */
    private final Path audio = Paths.get(
        "src/test/resources/album/test.mp3"
    );

    @Test
    public void testAdvancedTag()
        throws Exception {
        MatcherAssert.assertThat(
            new FallbackBytes(
                new ByteArrayImageFromAdvancedTag(
                    new AdvancedTagFromMp3File(
                        new Mp3File(
                            this.audio
                        )
                    )
                ),
                new ByteArrayFromFile(
                    this.audio
                )
            ).asBytes(),
            Matchers.equalTo(
                Files.readAllBytes(
                    this.path
                )
            )
        );
    }

    @Test
    public void testFile() throws Exception {
        MatcherAssert.assertThat(
            new FallbackBytes(
                new ByteArrayFromFile(
                    this.path
                ),
            new ByteArrayFromFile(
                this.audio
            )
        ).asBytes(),
            Matchers.equalTo(
                Files.readAllBytes(
                    this.path
                )
            )
        );
    }

    @Test(expected = Exception.class)
    public void testException() throws Exception {
        MatcherAssert.assertThat(
            new FallbackBytes(
                new ByteArrayFromFile(
                    this.path
                        .resolve("invalid")
                )
            ).asBytes(),
            Matchers.equalTo(
                Files.readAllBytes(
                    this.path
                )
            )
        );
    }

    @Test
    public void testValidAndException() throws Exception {
        MatcherAssert.assertThat(
            new FallbackBytes(
                new ByteArrayFromFile(
                    this.path
                        .resolve("wrong")
                ),
                new ByteArrayFromFile(
                    this.path
                )
            ).asBytes(),
            Matchers.equalTo(
                Files.readAllBytes(
                    this.path
                )
            )
        );
    }

}
