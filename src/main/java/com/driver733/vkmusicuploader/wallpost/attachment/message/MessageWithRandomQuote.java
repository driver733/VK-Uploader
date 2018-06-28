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
import org.cactoos.Scalar;
import org.cactoos.scalar.Constant;

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
     * Forismatic API HTTP request.
     */
    private final Scalar<Request> request;

    /**
     * Ctor.
     * @param request Forismatic API HTTP request.
     */
    public MessageWithRandomQuote(final Scalar<Request> request) {
        this.request = request;
    }

    /**
     * Ctor.
     * @param request Forismatic API HTTP request.
     */
    public MessageWithRandomQuote(final Request request) {
        this(
            new Constant<>(
                request
            )
        );
    }

    @Override
    public String value() throws Exception {
        final String message;
        final Response response = this.request.value().fetch();
        final JsonObject json = new Gson().fromJson(
            response.body(),
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
