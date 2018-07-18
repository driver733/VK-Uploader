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

package com.driver733.vkmusicuploader.wallpost.attachment.message;

import org.cactoos.Scalar;

/**
 * The tag that can be safely used.
 *
 * @since 0.1
 */
public final class TagSafe implements Scalar<String> {

    /**
     * Tag origin.
     */
    private final String origin;

    /**
     * Ctor.
     * @param origin Tag origin.
     */
    public TagSafe(final String origin) {
        this.origin = origin;
    }

    /**
     * Verifies the tag.
     * @return The original tag origin or
     *  an empty string if the tag does not pass validation.
     */
    public String value() {
        final String result;
        if (
            this.origin == null
                || this.origin.isEmpty()
                || "-1".equalsIgnoreCase(this.origin)
                || "Unknown".equalsIgnoreCase(this.origin)
            ) {
            result = this.origin;
        } else {
            result = "";
        }
        return result;
    }

}
