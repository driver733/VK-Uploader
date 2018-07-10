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

import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.safe.QueriesSafeCached;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link QueryResultsBasic}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (20 lines)
 */
@Immutable
public final class QueryResultsBasicTest {

    @Test
    public void test() throws IOException {
        MatcherAssert.assertThat(
            new QueryResultsBasic(
                new QueriesSafeCached(
                    new Array<>(
                        new AbstractQueryBuilder(
                            new VkApiClient(
                                new TransportClientCached(
                                    "{ \"response\" : 123 }"
                                )
                            ),
                            "testRoot",
                            AbstractQueryBuilder.class
                        ) {
                            @Override
                            protected Object getThis() {
                                return null;
                            }

                            @Override
                            protected Collection<String> essentialKeys() {
                                return Collections.emptyList();
                            }
                        }
                    )
                )
            ).results(),
            Matchers.containsInAnyOrder(
                new JsonParser().parse(
                    new JsonReader(
                        new StringReader(
                            "{\"response\" : 123}"
                        )
                    )
                )
            )
        );
    }

}
