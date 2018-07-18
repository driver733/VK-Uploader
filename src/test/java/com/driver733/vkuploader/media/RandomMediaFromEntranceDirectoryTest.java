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
package com.driver733.vkuploader.media;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link RandomMediaFromDirectory}.
 *
 * @since 0.1
 * @checkstyle AnonInnerLengthCheck (500 lines
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (500 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class RandomMediaFromEntranceDirectoryTest {

    /**
     * Test path.
     */
    private static final Path ROOT = Paths.get(
        "src/test/resources/randomDir/"
    );

    @Test
    public void testSingle() throws IOException {
        MatcherAssert.assertThat(
            "Returned paths do not match the expected ones.",
            new RandomMediaFromDirectory(
                RandomMediaFromEntranceDirectoryTest.ROOT
            ).file(),
            Matchers.anyOf(
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("1.properties")
                ),
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("2.properties")
                ),
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("3.properties")
                ),
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("4.properties")
                ),
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("5.properties")
                ),
                Matchers.equalTo(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("6.properties")
                )
            )
        );
    }

    @Test
    public void testShuffledList() throws IOException {
        MatcherAssert.assertThat(
            "Returned path does not match the expected one.",
            new RandomMediaFromDirectory(
                RandomMediaFromEntranceDirectoryTest.ROOT
            ).files(),
                Matchers.containsInAnyOrder(
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("1.properties"),
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("2.properties"),
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("3.properties"),
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("4.properties"),
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("5.properties"),
                    RandomMediaFromEntranceDirectoryTest.ROOT
                        .resolve("6.properties")
                )
        );
    }

}
