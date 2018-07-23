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

package com.driver733.vkuploader.wallpost;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Props.
 *
 * @since 0.4
 */
public interface Props {

    /**
     * Adds a new property.
     * @param key Property key.
     * @param value Property value.
     * @return The origin object.
     * @throws IOException If an error occurs.
     */
    Props with(
        String key,
        String value
    ) throws IOException;

    /**
     * Retrieves property value by key.
     * @param key Property key.
     * @return Property value.
     */
    String property(
        String key
    );

    /**
     * Shows whether the properties contains
     *  a property with the specified key.
     * @param key Property key.
     * @return Logical result.
     */
    boolean containsKey(String key);

    /**
     * Retrieves all properties
     *  as a {@link Set} of {@link Map.Entry}s.
     * @return The {@link Set} of {@link Map.Entry}s.
     */
    Set<Map.Entry<String, String>> entrySet();

}
