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
package com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback;

import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import org.cactoos.Bytes;

/**
 * Finds the first valid byte array.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 */
@Immutable
public final class FallbackBytes implements Bytes {

    /**
     * Byte array providers. Some or all might be faulty and raise an exception.
     */
    private final Array<Bytes> arrays;

    /**
     * Ctor.
     *
     * @param arrays Byte array providers.
     */
    public FallbackBytes(final Bytes... arrays) {
        this.arrays = new Array<>(arrays);
    }

    @Override
    public byte[] asBytes() throws Exception {
        for (final Bytes array : this.arrays) {
            try {
                return array.asBytes();
            } catch (final Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
        throw new Exception("No valid byte[] found.");
    }
}
