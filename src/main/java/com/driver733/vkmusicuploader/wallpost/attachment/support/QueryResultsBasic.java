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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.safe.QueriesSafe;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.QueryResults;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Combines the responses of cached
 *  {@link AbstractQueryBuilder}s.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class QueryResultsBasic implements QueryResults {

    /**
     * Queries.
     */
    private final QueriesSafe queries;

    /**
     * Ctor.
     * @param queries Queries.
     */
    public QueryResultsBasic(final QueriesSafe queries) {
        this.queries = queries;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public List<JsonElement> results() throws IOException {
        final List<AbstractQueryBuilder> constructed = this.queries.queries();
        final List<JsonElement> results =
            new ArrayList<>(constructed.size());
        for (final AbstractQueryBuilder query : constructed) {
            if (query.isCached()) {
                final String response;
                try {
                    response = query.executeAsString();
                } catch (final ClientException ex) {
                    throw new IOException("Failed to execute the query", ex);
                }
                results.add(
                    new JsonParser().parse(
                        new JsonReader(
                            new StringReader(response)
                        )
                    )
                );
            }
        }
        return new Array<>(results);
    }

    @Override
    public boolean fullyCached() {
        final List<AbstractQueryBuilder> list = this.queries.queries();
        boolean result = true;
        if (list.isEmpty()) {
            result = false;
        }
        for (final AbstractQueryBuilder query : list) {
            if (!query.isCached()) {
                result = false;
            }
        }
        return result;
    }
}
