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
package com.driver733.vkmusicuploader.media;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;

/**
 * Returns a random file from a directory.
 *
 * @since 0.2
 */
public final class RandomMediaFromDirectory implements Media {

    /**
     * Path to dir with files.
     */
    private final Path directory;

    /**
     * Ctor.
     * @param directory Path to dir with files.
     */
    public RandomMediaFromDirectory(final Path directory) {
        this.directory = directory;
    }

    @Override
    public List<Path> files() throws IOException {
        final List<Path> files = Files.list(
            this.directory
        ).filter(
            file -> Files.isRegularFile(file) && file.endsWith(".jpg")
        ).collect(
            Collectors.toCollection(
                ListOf<Path>::new
            )
        );
        return new ListOf<>(
            files.get(
                ThreadLocalRandom.current()
                    .nextInt(
                        0,
                        files.size() + 1
                    )
            )
        );
    }

}
