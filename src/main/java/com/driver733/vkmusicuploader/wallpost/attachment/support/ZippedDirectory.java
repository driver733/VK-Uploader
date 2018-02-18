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

import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Creates a zip file with files in the directory.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class ZippedDirectory {

    /**
     * Directory that contains files to filename.
     */
    private final File dir;

    /**
     * Output zip file name.
     */
    private final String filename;

    /**
     * Ctor.
     * @param dir Directory that contains files to zip.
     */
    public ZippedDirectory(final File dir) {
        this(dir, dir.getName());
    }

    /**
     * Ctor.
     * @param dir Directory that contains files to zip.
     * @param filename Output zip file name.
     */
    public ZippedDirectory(final File dir, final String filename) {
        this.dir = dir;
        this.filename = String.format(
            "%s/%s.zip",
            dir.getParentFile().getAbsolutePath(),
            filename
        );
    }

    /**
     * Creates a zip file with files in the directory.
     * @return Created zip file.
     * @throws IOException If zip file cannot be created.
     * @checkstyle MagicNumberCheck (20 lines)
     */
    @SuppressWarnings(
        {"PMD.AvoidInstantiatingObjectsInLoops", "PMD.AssignmentInOperand"}
    )
    public File zip() throws IOException {
        FileInputStream fis = null;
        try (
            FileOutputStream fos = new FileOutputStream(this.filename);
            ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            final byte[] buffer = new byte[1024];
            final File[] files = this.dir.listFiles();
            assert files != null;
            for (int iter = 0; iter < files.length; iter += 1) {
                fis = new FileInputStream(files[iter]);
                zos.putNextEntry(new ZipEntry(files[iter].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
        } catch (final IOException ex) {
            throw new IOException("Error creating zip file", ex);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return new File(this.filename);
    }
}
