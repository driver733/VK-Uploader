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
package com.driver733.vkuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback;

import com.jcabi.aspects.Immutable;
import com.jcabi.log.Logger;
import java.io.IOException;
import java.util.List;
import org.cactoos.Bytes;
import org.cactoos.list.ListOf;
import org.cactoos.scalar.IoCheckedScalar;

/**
 * Finds the first valid byte array.
 *
 *
 *
 * @since 0.2
 * @checkstyle IllegalCatchCheck (50 lines)
 */
@Immutable
public final class FallbackBytes implements Bytes {

    /**
     * Byte array providers. Some or all might be faulty and raise an exception.
     */
    private final List<Bytes> arrays;

    /**
     * Ctor.
     *
     * @param arrays Byte array providers.
     */
    public FallbackBytes(final Bytes... arrays) {
        this.arrays = new ListOf<>(arrays);
    }

    @Override
    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    public byte[] asBytes() throws Exception {
        for (final Bytes array : this.arrays) {
            try {
                return new IoCheckedScalar<>(
                    array::asBytes
                ).value();
            } catch (final IOException ex) {
                Logger.debug(
                    this,
                    "Unable to read bytes from the provided Bytes object: %s",
                    ex.toString()
                );
            }
        }
        throw new Exception("No valid byte[] found.");
    }
}
