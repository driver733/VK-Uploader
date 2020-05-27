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
import java.io.File;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link MessageBasic}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AvoidDuplicateLiterals (200 lines)
 */
@Immutable
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class MessageBasicTest {

    @Test
    public void allTags()
        throws InvalidDataException, IOException, UnsupportedTagException {
        final ID3v1 tag = new ID3v1AnnotatedSafe(
            new BasicTagFromMp3File(
                new Mp3File(
                    new File("src/test/resources/album/test.mp3")
                )
            ).construct()
        );
        Assertions.assertThat(
            new MessageBasic(
                tag.getAlbum(),
                tag.getArtist()
            ).value()
        ).isEqualTo("Album: Elegant Testing\nArtist: Test Man");
    }

    @Test
    public void missingTags()
        throws InvalidDataException, IOException, UnsupportedTagException {
        Assertions.assertThat(
            new MessageBasic(
                new ID3v1AnnotatedSafe(
                    new BasicTagFromMp3File(
                        new Mp3File(
                            new File(
                                "src/test/resources/album/testMissingTags.mp3"
                            )
                        )
                    ).construct()
                ).getAlbum(),
                new ID3v1AnnotatedSafe(
                    new BasicTagFromMp3File(
                        new Mp3File(
                            new File(
                                "src/test/resources/album/testMissingTags.mp3"
                            )
                        )
                    ).construct()
                ).getArtist()
            ).value()
        ).isEqualTo("");
    }

}
