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
package com.driver733.vkmusicuploader.wallpost;

import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import org.cactoos.Scalar;
import org.cactoos.scalar.Constant;

/**
 * A {@link WallPost} with a specified message.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class WallPostWithMessage implements WallPost {

    /**
     * Origin.
     */
    private final WallPost post;

    /**
     * Wall WallPostMusicAlbum text.
     */
    private final Scalar<String> message;

    /**
     * Ctor.
     * @param post Origin.
     * @param message Wall WallPostMusicAlbum text.
     */
    public WallPostWithMessage(
        final WallPost post,
        final Scalar<String> message
    ) {
        this.post = post;
        this.message = message;
    }

    /**
     * Ctor.
     * @param post Origin.
     * @param message Wall WallPostMusicAlbum text.
     */
    public WallPostWithMessage(
        final WallPost post,
        final String message
    ) {
        this(
            post,
            new Constant<>(
                message
            )
        );
    }

    @Override
    public WallPostQuery construct() throws Exception {
        return this.post
            .construct()
            .message(
                this.message
                    .value()
            );
    }

}
