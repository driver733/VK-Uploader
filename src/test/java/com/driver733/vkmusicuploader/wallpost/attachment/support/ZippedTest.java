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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.ZipInputStream;
import org.cactoos.io.InputOf;
import org.cactoos.io.LengthOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.TeeInput;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link Zipped}.
 *
 * @author Mikhail Yakushin (yakushin@terpmail.umd.edu)
 * @version $Id$
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
        final File actual = new File(
            "src/test/resources/zip/actual.zip"
        );
        actual.deleteOnExit();
        final File expected = new File(
            "src/test/resources/zip/expected.zip"
        );
        new LengthOf(
            new TeeInput(
                new InputOf(
                    new Zipped(
                        Paths.get("src/test/resources/zip/test/")
                    ).zip()
                ),
                new OutputTo(
                    new FileOutputStream(
                        actual
                    )
                )
            )
        ).intValue();
        MatcherAssert.assertThat(
            "Created ZIP file is invalid.",
            new ArrayList<String>(2) {
                {
                    final ZipInputStream zis = new ZipInputStream(
                        new FileInputStream(expected)
                    );
                    add(
                        zis.getNextEntry().getName()
                    );
                    add(
                        zis.getNextEntry().getName()
                    );
                }
            },
            Matchers.<Collection<String>>allOf(
                Matchers.hasSize(2),
                Matchers.hasItem("inside/audiosTest.properties"),
                Matchers.hasItem("attachment.txt")
            )
        );
    }

}