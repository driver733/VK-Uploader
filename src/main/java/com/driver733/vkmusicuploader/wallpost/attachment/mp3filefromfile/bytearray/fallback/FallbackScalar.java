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
import com.jcabi.log.Logger;
import java.io.IOException;
import org.cactoos.Scalar;

/**
 * Finds the first valid scalar.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @param <T> Type.
 */
@Immutable
public final class FallbackScalar<T> implements Scalar<T> {

    /**
     * Byte array providers. Some or all might be faulty and raise an exception.
     */
    private final Array<Scalar<T>> arrays;

    /**
     * Ctor.
     * @param arrays Byte array providers.
     */
    public FallbackScalar(final Scalar<T>... arrays) {
        this.arrays = new Array<>(arrays);
    }

    @Override
    public T value() throws Exception {
        for (final Scalar<T> array : this.arrays) {
            try {
                return array.value();
            } catch (final IOException ex) {
                Logger.debug(this, ex.getMessage());
            }
        }
        throw new IOException("No valid Scalar found found.");
    }

}
