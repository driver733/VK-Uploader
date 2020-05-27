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

import com.driver733.vkuploader.post.SuppressFBWarnings;
import com.driver733.vkuploader.wallpost.WallPost;
import com.jcabi.aspects.Immutable;
import java.util.List;
import org.cactoos.Scalar;
import org.cactoos.list.StickyList;

/**
 * Constructs a {@link WallPost}
 *  message by combining the specified strings.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class MessageBasic implements Scalar<String> {

    /**
     * Parts of the message.
     */
    private final List<String> parts;

    /**
     * Ctor.
     * @param parts Parts of the message.
     */
    public MessageBasic(final String... parts) {
        this.parts = new StickyList<>(
            parts
        );
    }

    @Override
    @SuppressFBWarnings("VA_FORMAT_STRING_USES_NEWLINE")
    public String value() {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < this.parts.size(); index += 1) {
            final String result = this.parts.get(index);
            if (!result.isEmpty()) {
                final String res;
                if (index == this.parts.size() - 1) {
                    res = String.format("%s", result);
                } else {
                    res = String.format("%s\n", result);
                }
                builder.append(res);
            }
        }
        return builder.toString();
    }
}
