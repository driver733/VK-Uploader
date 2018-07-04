/**
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
package com.driver733.vkmusicuploader.media.audio;

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link MediaVerified}.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@Immutable
public final class MediaVerifiedTest {

    @Test
    public void testAll() throws IOException {
        MatcherAssert.assertThat(
            "Incorrect list produced.",
            new MediaVerified(
                new AudiosNonProcessed(
                    new AudiosBasic(
                        new File("src/test/resources/album")
                    ),
                    new ImmutableProperties(
                        new File("src/test/resources/audiosTest.properties")
                    )
                )
            ).files(),
            Matchers.containsInAnyOrder(
                new File("src/test/resources/album/test.mp3")
            )
        );
    }

    @Test(expected = IOException.class)
    public void testException() throws IOException {
        MatcherAssert.assertThat(
            "Exception should have been thrown.",
            new MediaVerified(
                new AudiosNonProcessed(
                    new AudiosBasic(
                        new File("src/test/")
                    ),
                    new ImmutableProperties(
                        new File("src/test/resources/audiosTest.properties")
                    )
                )
            ).files(),
            Matchers.containsInAnyOrder(
                new File("src/test/resources/album/test.mp3")
            )
        );
    }

}
