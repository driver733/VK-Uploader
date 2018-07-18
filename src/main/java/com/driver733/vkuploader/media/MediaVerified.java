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

import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.cactoos.list.ListOf;

/**
 * Verifies that the {@link List}
 * of audios files is not empty.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class MediaVerified implements Media {

    /**
     * Origin.
     */
    private final Media origin;

    /**
     * Ctor.
     * @param origin Origin.
     */
    public MediaVerified(final Media origin) {
        this.origin = origin;
    }

    @Override
    public List<Path> files() throws IOException {
        final List<Path> audios = new ListOf<>(
            this.origin.files()
        );
        if (audios.isEmpty()) {
            throw new IOException("No media found");
        }
        return audios;
    }
}
