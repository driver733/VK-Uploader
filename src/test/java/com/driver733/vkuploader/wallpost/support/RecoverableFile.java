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
package com.driver733.vkuploader.wallpost.support;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Recovers file to its original state.
 *
 *
 *
 * @since 0.1
 */
public final class RecoverableFile implements Recoverable<File> {

    /**
     * Contents of file to be saved and recovered.
     */
    private final byte[] contents;

    /**
     * The {@link Path} of the file to be recovered.
     */
    private final Path path;

    /**
     * Ctor.
     * @param contents Contents of the file to be saved and recovered.
     * @param path The {@link Path} of the file to be recovered.
     */
    public RecoverableFile(final byte[] contents, final Path path) {
        this.contents = Arrays.copyOf(
            contents,
            contents.length
        );
        this.path = path;
    }

    @Override
    public File recover() throws IOException {
        try (
            OutputStream fop = Files.newOutputStream(
                this.path
            )
        ) {
            fop.write(this.contents);
            fop.flush();
        } catch (final IOException ex) {
            throw new IOException(
                "Failed to complete the recovery.",
                ex
            );
        }
        return this.path.toFile();
    }

}
