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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.driver733.vkmusicuploader.support.ImmutableProperties;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
final class PropertiesUpdate {

    /**
     * Properties that contain the {@link AudioStatus} of audio files.
     */
    private final ImmutableProperties properties;

    /**
     * A map of with index-audio_id pairs from the audio queries.
     */
    private final Map<Integer, String> ids;

    /**
     * {@link JsonArray} that contains the
     *  {@link com.driver733.vkmusicuploader.support.QueryResults}
     *  of the queries.
     */
    private final JsonArray root;

    /**
    * Ctor.
    * @param properties Properties that contain
    *  the {@link AudioStatus} of audio files.
    * @param ids A map of with index-audio_id pairs from the audio queries.
    * @param root A {@link JsonArray} that contains the
    *  {@link com.driver733.vkmusicuploader.support.QueryResults}
    *  of the queries.
    */
    PropertiesUpdate(
        final ImmutableProperties properties,
        final Map<Integer, String> ids,
        final JsonArray root
    ) {
        this.properties = properties;
        this.ids = ids;
        this.root = root;
    }

    /**
     * Saves the properties.
     * @throws IOException If properties cannot be saved.
     */
    public void save() throws IOException {
        final Map<Integer, String> results = this.resStrings();
        for (final int index : this.ids.keySet()) {
            this.properties.setPropertyAndStore(
                this.key(index).toString(),
                String.format(
                    "%s_%s",
                    AudioStatus.ADDED.toString(),
                    results.get(index)
                )
            );
        }
    }

    /**
     * Finds the key for the provided index.
     * @param index For which to find the key.
     * @return The {@link Map}`s key.
     * @throws IOException If key is not found.
     */
    private Object key(final int index) throws IOException {
        for (final Map.Entry<Object, Object> entry
            : this.properties.entrySet()) {
            if (
                Objects.equals(
                    this.ids.get(index),
                    entry.getValue().toString().substring(2)
                )
                ) {
                return entry.getKey();
            }
        }
        throw new IOException("Key not found.");
    }

    /**
     * Forms a {@link Map} with attachment strings with corresponding indexes.
     * @return A {@link Map} with attachment strings with corresponding indexes.
     */
    private Map<Integer, String> resStrings() {
        final List<Integer> integers = new ArrayList<>(this.ids.keySet());
        final Map<Integer, String> results = new HashMap<>();
        int index = 0;
        for (final JsonElement element : this.root) {
            if (element.isJsonPrimitive()) {
                results.put(
                    integers.get(index),
                    String.valueOf(element.getAsInt())
                );
                index += 1;
            }
        }
        return results;
    }

}
