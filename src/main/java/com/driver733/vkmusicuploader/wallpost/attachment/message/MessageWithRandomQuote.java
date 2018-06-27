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
package com.driver733.vkmusicuploader.wallpost.attachment.message;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import java.io.IOException;
import org.cactoos.Scalar;

/**
 * A message with a random quote
 *  from Forismatic API.
 *
 * @author Mikhail Yakushin (yakushin@terpmail.umd.edu)
 * @version $Id$
 * @since 0.2
 */
@Immutable
public final class MessageWithRandomQuote implements Scalar<String> {

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
        MessageWithRandomQuote.VERSION
    );

    @Override
    public String value() throws IOException {
        final String message;
        final Response response = new JdkRequest(
            MessageWithRandomQuote.BASE
        ).uri()
            .path(
                MessageWithRandomQuote.PATH
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
            )
            .fetch();
        final JsonObject json = new Gson().fromJson(
            response
                .body(),
            JsonElement.class
        ).getAsJsonObject();
        final String quote = json.get("quoteText")
            .getAsString();
        final String author = json.get("quoteAuthor")
            .getAsString();
        if (author.isEmpty()) {
            message = String.format(
                "%s%n",
                quote
            );
        } else {
            message = String.format(
                "%s%n© %s",
                quote,
                author
            );
        }
        return message;
    }

}
