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

import com.jcabi.aspects.Immutable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;
import org.cactoos.scalar.IoCheckedScalar;

/**
 * Creates a zip file with files in the directory.
 *
 *
 *
 * @since 0.2
 */
@Immutable
@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
public final class ZippedDirectory {

    /**
     * EntranceDirectory or a file to zip.
     */
    private final Path directory;

    /**
     * Ctor.
     * @param directory EntranceDirectory or a file to zip.
     */
    public ZippedDirectory(final Path directory) {
        this.directory = directory;
    }

    /**
     * Creates a zip file with files in the directory.
     * @return Created zip file.
     * @throws Exception If zip file cannot be created.
     */
    public InputStream zip() throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(out)) {
            Files.walk(
                this.directory
            ).filter(
                dir -> !Files.isDirectory(dir)
            ).forEach(
                path -> {
                    try {
                        zos.putNextEntry(
                            new ZipEntry(
                                this.directory.relativize(
                                    path
                                ).toString()
                            )
                        );
                        zos.write(
                            new IoCheckedScalar<>(
                                () -> new BytesOf(
                                    new InputOf(
                                        path
                                    )
                                ).asBytes()
                            ).value()
                        );
                        zos.closeEntry();
                        zos.flush();
                        zos.close();
                    } catch (final IOException ex) {
                        throw new IllegalStateException(
                            "Zipping failed with an exception: ",
                            ex
                        );
                    }
                }
                );
            return new ByteArrayInputStream(
                out.toByteArray()
            );
        }
    }

}
