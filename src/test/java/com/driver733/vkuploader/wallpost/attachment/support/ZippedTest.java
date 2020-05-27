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
package com.driver733.vkuploader.wallpost.attachment.support;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;
import org.assertj.core.api.Assertions;
import org.cactoos.io.InputOf;
import org.cactoos.io.LengthOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.junit.Test;

/**
 * Test for {@link Zipped}.
 *
 *
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class ZippedTest {

    @Test
    @SuppressWarnings({
        "PMD.NonStaticInitializer",
        "PMD.ProhibitPlainJunitAssertionsRule",
        "PMD.AvoidInstantiatingObjectsInLoops"
        })
    public void testDirectory() throws Exception {
        final Path actual = Paths.get(
            "src/test/resources/zip/actual.zip"
        );
        actual.toFile().deleteOnExit();
        final File expected = new File(
            "src/test/resources/zip/expected.zip"
        );
        new LengthOf(
            new TeeInput(
                new InputOf(
                    new Zipped(
                        Paths.get("src/test/resources/zip/test/")
                    ).stream()
                ),
                new OutputTo(
                    Files.newOutputStream(
                        actual
                    )
                )
            )
        ).intValue();
        Assertions.assertThat(
            new ArrayList<String>(2) {
                {
                    final ZipInputStream zis = new ZipInputStream(
                        Files.newInputStream(expected.toPath())
                    );
                    add(
                        zis.getNextEntry().getName()
                    );
                    add(
                        zis.getNextEntry().getName()
                    );
                }
            }
        ).containsOnly("inside/audiosTest.properties", "attachment.txt");
    }

}
