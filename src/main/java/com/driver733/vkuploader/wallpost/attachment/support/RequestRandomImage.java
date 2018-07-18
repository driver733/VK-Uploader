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
package com.driver733.vkuploader.wallpost.attachment.support;

import com.jcabi.aspects.Immutable;
import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import org.cactoos.Scalar;

/**
 * Fetches a random image from www.picsum.photos.
 *
 *
 *
 * @since 0.2
 */
@Immutable
public final class RequestRandomImage implements Scalar<Request> {

    /**
     * Image width.
     */
    private static final int WIDTH = 600;

    /**
     * Image height.
     */
    private static final int HEIGHT = 400;

    /**
     * Base URL.
     */
    private static final String BASE = "https://picsum.photos";

    /**
     * Randon image URL path.
     */
    private static final String QUERY = "?random";

    /**
     * Request path.
     */
    private static final String PATH = String.format(
        "/%d/%d/",
        RequestRandomImage.WIDTH,
        RequestRandomImage.HEIGHT
    );

    @Override
    public Request value() {
        return new JdkRequest(
            RequestRandomImage.BASE
        ).uri()
            .path(
                RequestRandomImage.PATH
            )
            .queryParam(
                RequestRandomImage.QUERY,
                ""
            )
            .back()
            .method(
                Request.GET
            )
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64)"
            );
    }

}
