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
package com.driver733.vkmusicuploader.media.photo;

import com.driver733.vkmusicuploader.media.Media;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;

/**
 * Constructs a list of audios files
 *  in the specified folder.
 *
 *
 *
 * @since 0.2
 */
@Immutable
public final class MediaPhotosBasic implements Media {

    /**
     * EntranceDirectory that contains files.
     */
    private final Path dir;

    /**
     * Ctor.
     * @param dir EntranceDirectory that contains files.
     */
    public MediaPhotosBasic(final Path dir) {
        this.dir = dir;
    }

    // @checkstyle ParameterNameCheck (10 lines)
    @Override
    public List<Path> files() throws IOException {
        return new ListOf<>(
            Files.list(
                this.dir
            ).filter(
                file -> Files.isRegularFile(file)
                    && file.getFileName().toString().endsWith(".jpg")
            ).collect(
                Collectors.toList()
            )
        );
    }

}
