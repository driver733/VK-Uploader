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
package com.driver733.vkmusicuploader.properties;

import com.jcabi.aspects.Immutable;
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
@Immutable
public final class ImmutableProperties extends Properties {

    /**
     * Properties construct.
     */
    private final File file;

    /**
     * Ctor.
     * @param file Properties construct.
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
     * @throws IOException If the properties construct cannot be loaded.
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public synchronized void load() throws IOException {
        if (!this.file.exists()) {
            throw new IOException("Specified properties file does not exist.");
        }
        final FileInputStream fis = new FileInputStream(this.file);
        try {
            super.load(fis);
        } catch (final IOException ex) {
            throw new IOException("Failed to load properties", ex);
        }
        fis.close();
    }

    /**
     * Sets the property and stores the properties object.
     * @param key The key to be placed into this property list.
     * @param value The value corresponding to <tt>key</tt>.
     *  list, or {@code null} if it did not have one.
     * @throws IOException If the properties cannot be stored.
     */
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
    public synchronized void setPropertyAndStore(
        final String key,
        final String value
    ) throws IOException {
        this.put(key, value);
        this.store();
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
     * @throws IOException If the properties construct cannot be saved.
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
        try {
            super.store(fos, comment);
        } catch (final IOException ex) {
            throw new IOException("Failed to store properties", ex);
        }
        fos.close();
    }

}
