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
package com.driver733.vkmusicuploader.wallpost.attachment.support.mp3filefromfile.bytearray.fallback;

import com.driver733.vkmusicuploader.wallpost.attachment.support.mp3filefromfile.bytearray.ByteArray;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.jcabi.log.Logger;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo #36 Create tests for the class.
 */
@Immutable
public final class FallbackByteArrayImage implements Fallback<byte[]> {

    /**
     * Byte array providers. Some or all might be faulty and raise exception.
     */
    private final Array<ByteArray> arrays;

    /**
     * Ctor.
     * @param arrays Byte array providers.
     */
    public FallbackByteArrayImage(final ByteArray... arrays) {
        this.arrays = new Array<>(
            Arrays.asList(arrays)
        );
    }

    @Override
    @Cacheable(forever = true)
    public List<byte[]> firstValid() {
        final Array<byte[]> result = new Array<>();
        for (final ByteArray array : this.arrays) {
            try {
                result.with(
                    array.toByteArray()
                );
                break;
            } catch (final IOException ignored) {
                Logger.debug(this, ignored.getMessage());
            }
        }
        return result;
    }

}
