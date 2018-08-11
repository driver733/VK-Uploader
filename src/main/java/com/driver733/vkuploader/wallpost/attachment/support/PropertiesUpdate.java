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
package com.driver733.vkuploader.wallpost.attachment.support;

import com.driver733.vkuploader.wallpost.PropsFile;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 * Records the audios files that have been
 *  posted.
 *
 * @since 0.1
 */
@Immutable
public final class PropertiesUpdate implements Runnable {

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
     */
    private final PropsFile properties;

    /**
     * A map of with index-audio_id pairs from the audios queries.
     */
    private final Map<Integer, String> ids;

    /**
     * {@link JsonArray} that contains the
     *  {@link QueryResultsBasic}
     *  of the queries.
     */
    private final JsonArray root;

    /**
    * Ctor.
    * @param properties Properties that contain
    *  the {@link AudioStatus} of audios files.
    * @param ids A map of with index-audio_id pairs from the audios queries.
    * @param root A {@link JsonArray} that contains the
    *  {@link QueryResultsBasic}
    *  of the queries.
    */
    public PropertiesUpdate(
        final PropsFile properties,
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
    public void run() {
        final Map<Integer, String> results = this.resStrings();
        for (final int index : this.ids.keySet()) {
            try {
                this.properties.with(
                    this.key(
                        index
                    ).toString(),
                    String.format(
                        "%s_%s",
                        AudioStatus.ADDED.toString(),
                        results.get(
                            index
                        )
                    )
                );
            } catch (final IOException ex) {
                throw new IllegalStateException(
                    "PropertiesUpdate key not found",
                    ex
                );
            }
        }
    }

    /**
     * Finds the key for the provided index.
     * @param index For which to find the key.
     * @return The {@link Map}`s key.
     * @throws IOException If key is not found.
     * @checkstyle StringLiteralsConcatenationCheck (50 lines)
     */
    private Object key(
        final int index
    ) throws IOException {
        for (
            final Map.Entry<String, String> entry : this.properties.entrySet()
        ) {
            final String value = entry.getValue();
            if (
                Objects.equals(
                    this.ids.get(index),
                    value.substring(
                        StringUtils.ordinalIndexOf(
                            value,
                            "_",
                            2
                        ) + 1
                    )
                )
                ) {
                return entry.getKey();
            }
        }
        throw new IOException("Key not found.");
    }

    /**
     * Forms a {@link Map} with attachment
     *  attachmentStrings with corresponding indexes.
     * @return A {@link Map} with attachment
     *  attachmentStrings with corresponding indexes.
     */
    private Map<Integer, String> resStrings() {
        final List<Integer> integers = new ArrayList<>(
            this.ids.keySet()
        );
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
