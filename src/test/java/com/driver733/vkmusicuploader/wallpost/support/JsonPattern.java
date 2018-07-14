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
package com.driver733.vkmusicuploader.wallpost.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Retrieves JSON from VK Execute API call.
 *
 * @since 0.1
 */
@Immutable
@SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
public final class JsonPattern {

    /**
     * Exec String for VK Execute API call.
     */
    private final String exec;

    /**
     * Regex pattern.
     */
    private final Pattern pattern;

    /**
     * Ctor.
     * @param exec Exec String for VK Execute API call.
     * @param pattern Regex pattern.
     */
    public JsonPattern(
        final String exec,
        final Pattern pattern
    ) {
        this.exec = exec;
        this.pattern = pattern;
    }

    /**
     * Creates a JSON object from the provided string.
     * @return Created {@link JsonObject}.
     * @throws Exception If the provided patter was not found.
     */
    public JsonObject json() throws Exception {
        final Matcher matcher = this.pattern.matcher(
            this.exec
        );
        if (matcher.find()) {
            String match = matcher.group();
            match = match.substring(
                0,
                match.length() - 2
            );
            return new Gson()
                .fromJson(
                    match,
                    JsonObject.class
                );
        }
        throw new Exception(
            String.format(
                String.join(
                    "No occurrence of the provided pattern \"%s\"",
                        " found in the Json String \"%s\""
                ),
                this.pattern.toString(),
                this.exec
            )
        );
    }

}
