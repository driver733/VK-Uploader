/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Mikhail Yakushin
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
package com.driver733.vkmusicuploader.wallpost.attachment.support.message;

import com.driver733.vkmusicuploader.wallpost.attachment.support.message.messagepart.MessagePart;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import java.io.IOException;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class MessageBasic implements Message {

    /**
     * Parts of the message.
     */
    private final Array<MessagePart> parts;

    /**
     * Ctor.
     * @param parts Parts of the message.
     */
    public MessageBasic(final MessagePart... parts) {
        this.parts = new Array<>(parts);
    }

    @Override
    public String construct() throws IOException {
        final StringBuilder builder = new StringBuilder();
        for (final MessagePart part : this.parts) {
            final String result;
            try {
                result = part.construct();
            } catch (final IOException ex) {
                throw new IOException("Failed to construct message part", ex);
            }
            builder.append(
                String.format("%s%n", result)
            );
        }
        return builder.toString();
    }
}
