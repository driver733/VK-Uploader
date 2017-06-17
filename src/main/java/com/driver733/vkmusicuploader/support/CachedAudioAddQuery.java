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
package com.driver733.vkmusicuploader.support;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

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
public class CachedAudioAddQuery extends AbstractQueryBuilder {

    /**
     * Creates a AbstractQueryBuilder instance that can be used
     * to build api request with various parameters.
     * @param audioId Value of "audio id" parameter. Minimum is 0.
     * @checkstyle ParameterNameCheck (2 lines)
     */
    public CachedAudioAddQuery(final int audioId) {
        super(
            new VkApiClient(
                new CachedClient(
                    new JsonParser().parse(
                        new JsonReader(
                            new StringReader(
                                new GsonBuilder().create().toJson(
                                    audioId,
                                    Integer.class
                                )
                            )
                        )
                    ).getAsJsonPrimitive()
                )
            ),
            "cached_audio.add",
            Integer.class
        );
    }
    /**
     * Get reference to this object.
     * @return A reference to this {@code AbstractQueryBuilder}
     *  object to fulfill the "Builder" pattern.
     */
    protected final CachedAudioAddQuery getThis() {
        return this;
    }

    /**
     * Params that are required.
     * @return Essential params.
     * @checkstyle NonStaticMethodCheck (5 lines)
     */
    protected final List<String> essentialKeys() {
        return new ArrayList<>(0);
    }
}
