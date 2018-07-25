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
package com.driver733.vkuploader.media.photo;

import com.driver733.vkuploader.media.Media;
import com.driver733.vkuploader.post.SuppressFBWarnings;
import com.driver733.vkuploader.wallpost.PropsFile;
import com.driver733.vkuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkuploader.wallpost.attachment.support.WallPhotoStatus;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.cactoos.list.StickyList;

/**
 * Returns a {@link List} of files
 * that have not been processed
 * yet.
 *
 *
 *
 * @since 0.2
 */
@Immutable
@SuppressFBWarnings(
    value = "NP_NULL_ON_SOME_PATH",
    justification = "If path exists then NP will not occur."
)
public final class MediaPhotosNonProcessed implements Media {

    /**
     * Origin.
     */
    private final Media origin;

    /**
     * Properties that contain the {@link AudioStatus}es of audios files.
     */
    private final PropsFile props;

    /**
     * Ctor.
     * @param origin Origin.
     * @param properties Properties that contain
     *  the {@link AudioStatus}es of audios files.
     */
    public MediaPhotosNonProcessed(
        final Media origin,
        final PropsFile properties
    ) {
        this.origin = origin;
        this.props = properties;
    }

    @Override
    public List<Path> files() throws IOException {
        final List<Path> photos = new StickyList<>(
            this.origin.files()
        );
        final List<Path> result = new ArrayList<>(
            photos.size()
        );
        for (final Path file : photos) {
            if (
                !this.props.containsKey(
                    file.getFileName().toString()
                )
                    || !this.props
                    .property(
                        file.getFileName().toString()
                    ).substring(0, 1)
                    .equals(
                        WallPhotoStatus.POSTED.toString()
                    )
                ) {
                result.add(file);
            }
        }
        return result;
    }

}
