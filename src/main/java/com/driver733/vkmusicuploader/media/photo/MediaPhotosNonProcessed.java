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
package com.driver733.vkmusicuploader.media.photo;

import com.driver733.vkmusicuploader.media.Media;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.support.WallPhotoStatus;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns a {@link List} of files
 * that have not been processed
 * yet.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 */
@Immutable
public final class MediaPhotosNonProcessed implements Media {

    /**
     * Origin.
     */
    private final Media origin;

    /**
     * Properties that contain the {@link AudioStatus}es of audio files.
     */
    private final ImmutableProperties props;

    /**
     * Ctor.
     * @param origin Origin.
     * @param properties Properties that contain
     *  the {@link AudioStatus}es of audio files.
     */
    public MediaPhotosNonProcessed(
        final Media origin,
        final ImmutableProperties properties
    ) {
        this.origin = origin;
        this.props = properties;
    }

    @Override
    public List<File> files() throws IOException {
        this.props.load();
        final Array<File> photos = new Array<>(
            this.origin.files()
        );
        final List<File> result = new ArrayList<>(
            photos.size()
        );
        for (final File file : photos) {
            if (
                !this.props.containsKey(file.getName())
                    || !this.props
                    .get(file.getName())
                    .toString()
                    .substring(0, 1)
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
