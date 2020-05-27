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

import com.driver733.vkuploader.post.SuppressFBWarnings;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Random file.
 *
 * @since 0.2
 */
@SuppressFBWarnings(
    value = "DMI_RANDOM_USED_ONLY_ONCE",
    justification = "This is done on purpose."
)
public final class MediaRandom implements Media, MediaSingle {

    /**
     * Origin.
     */
    private final Media origin;

    /**
     * Random seed.
     */
    private final long seed;

    /**
     * Ctor.
     * @param origin Origin.
     * @param seed Random seed.
     */
    public MediaRandom(
        final Media origin,
        final long seed
    ) {
        this.origin = origin;
        this.seed = seed;
    }

    /**
     * Ctor.
     * @param origin Origin.
     */
    public MediaRandom(
        final Media origin
    ) {
        this(
            origin,
            new Random()
                .nextLong()
        );
    }

    /**
     * Origin.
     * @param seed Random seed.
     * @param files Files.
     */
    public MediaRandom(
        final long seed,
        final Path... files
    ) {
        this(
            new Media() {
                @Override
                public List<Path> files() {
                    return Arrays.asList(
                        files
                    );
                }
            },
            seed
        );
    }

    /**
     * Origin.
     * @param files Files.
     */
    public MediaRandom(
        final Path... files
    ) {
        this(
            new Media() {
                @Override
                public List<Path> files() {
                    return Arrays.asList(
                        files
                    );
                }
            },
            new Random()
                .nextLong()
        );
    }

    @Override
    public Path file() throws IOException {
        return this.files().get(0);
    }

    @Override
    public List<Path> files() throws IOException {
        final List<Path> result = new ArrayList<>(
            this.origin.files()
        );
        Collections.shuffle(
            result,
            new Random(
                this.seed
            )
        );
        return result;
    }

}
