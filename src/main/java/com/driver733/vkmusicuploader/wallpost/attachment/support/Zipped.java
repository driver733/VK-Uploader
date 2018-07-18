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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.jcabi.aspects.Immutable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.cactoos.io.BytesOf;
import org.cactoos.io.InputOf;

/**
 * Creates a zip file with files in the directory.
 *
 *
 *
 * @since 0.2
 */
@Immutable
@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
public final class Zipped {

    /**
     * DirectoryEntrance or a file to zip.
     */
    private final Path directory;

    /**
     * Ctor.
     * @param directory DirectoryEntrance or a file to zip.
     */
    public Zipped(final Path directory) {
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
            final List<Path> paths = Files.walk(
                this.directory
            ).filter(
                dirr -> !Files.isDirectory(dirr)
            ).collect(
                Collectors.toList()
            );
            for (final Path path : paths) {
                final ZipEntry entry = new ZipEntry(
                    this.directory.relativize(
                        path
                    ).toString()
                );
                zos.putNextEntry(entry);
                zos.write(
                    new BytesOf(
                        new InputOf(
                            path
                        )
                    ).asBytes()
                );
                zos.closeEntry();
            }
            zos.flush();
            zos.close();
            return new ByteArrayInputStream(
                out.toByteArray()
            );
        }
    }

}
