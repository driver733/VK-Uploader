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
package com.driver733.vkmusicuploader.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
public final class ImmutableProperties extends Properties {

    /**
     * Properties file.
     */
    private final File file;

    /**
     * Ctor.
     * @param file Properties file.
     */
    public ImmutableProperties(final File file) {
        super();
        this.file = file;
    }

    @Override
    @SuppressWarnings({
        "PMD.AvoidSynchronizedAtMethodLevel",
        "PMD.AvoidDuplicateLiterals"
        })
    public synchronized void load(final InputStream stream) throws
        IOException {
        throw new IOException("Not supported");
    }

    /**
     * Loads the properties from the provided {@link File}.
     * @throws IOException If the properties file cannot be loaded.
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public synchronized void load() throws IOException {
        if (!this.file.exists() && !this.file.createNewFile()) {
            throw new IOException("Failed to create the Properties file.");
        }
        final FileInputStream fis = new FileInputStream(this.file);
        super.load(fis);
        fis.close();
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void store(
        final OutputStream stream,
        final String comments
    ) throws IOException {
        throw new IOException("Not supported");
    }

    /**
     * Saves the properties using the provided {@link File}.
     * @throws IOException If the properties file cannot be saved.
     */
    public void store() throws IOException {
        this.store("");
    }

    /**
     * Saves the properties using the provided {@link File}.
     * @param comment Comment.
     * @throws IOException If the properties cannot be saved.
     */
    private void store(final String comment) throws IOException {
        final FileOutputStream fos = new FileOutputStream(this.file);
        super.store(fos, comment);
        fos.close();
    }
}
