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
package com.driver733.vkuploader.wallpost.attachment.message;

import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import org.cactoos.Scalar;

/**
 * Request for Forismatic API fetching a random quote.
 *
 *
 *
 * @since 0.2
 */
public final class RequestRandomQuote implements Scalar<Request> {

    /**
     * Forismatic API version.
     */
    private static final String VERSION = "1.0";

    /**
     * Base URL.
     */
    private static final String BASE = "https://api.forismatic.com";

    /**
     * Request path.
     */
    private static final String PATH = String.format(
        "/api/%s/",
        RequestRandomQuote.VERSION
    );

    @Override
    public Request value() {
        return new JdkRequest(
            RequestRandomQuote.BASE
            ).uri()
            .path(
                RequestRandomQuote.PATH
            )
            .back()
            .method(
                Request.POST
            )
            .body()
            .formParam(
                "method",
                "getQuote"
            ).formParam(
                "format",
                "json"
            ).formParam(
                "lang",
                "ru"
            ).back()
            .header(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64)"
            );
    }

}
