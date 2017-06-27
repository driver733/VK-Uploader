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

import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentAddAudio;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @todo # 22 Write tests for exceptions.
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class QueriesFromAttachmentsTest {

    /**
     * Constant.
     */
    private static final String TOKEN1 = "TOKEN1";

    /**
     * Constant.
     */
    private static final String TOKEN2 = "TOKEN2";

    /**
     * Constant.
     */
    private static final String CACHE = "cache";

    /**
     * Object for testing purposes.
     */
    private final QueriesFromAttachments queries;

    public QueriesFromAttachmentsTest() {
        this.queries = new QueriesFromAttachments(
            new Array<>(
                new AttachmentAddAudio(
                    new VkApiClient(
                        new TransportClientHttp()
                    ),
                    new UserActor(
                        0,
                        QueriesFromAttachmentsTest.TOKEN1
                    ),
                    0,
                    1
                ),
                new AttachmentAddAudio(
                    new VkApiClient(
                        new TransportClientCached(
                            QueriesFromAttachmentsTest.CACHE
                        )
                    ),
                    new UserActor(
                        0,
                        QueriesFromAttachmentsTest.TOKEN2
                    ),
                    0,
                    2
                )
            )
        );
    }

    @Test
    public void idsMap() throws IOException, ClientException, ApiException {
        final Map<Integer, String> expected = new HashMap<>();
        expected.put(0, "1");
        expected.put(1, "2");
        MatcherAssert.assertThat(
            this.queries.idsMap(),
            Matchers.equalTo(expected)
        );
    }

    @Test
    public void cached() throws IOException, ClientException, ApiException {
        MatcherAssert.assertThat(
            this.queries.queries(true).get(0).build(),
            Matchers.equalTo(
                new AttachmentAddAudio(
                    new VkApiClient(
                        new TransportClientCached(
                            QueriesFromAttachmentsTest.CACHE
                        )
                    ),
                    new UserActor(
                        0,
                        QueriesFromAttachmentsTest.TOKEN2
                    ),
                    0,
                    2
                ).upload().get(0).build()
            )
        );
    }

    @Test
    public void nonCached() throws IOException, ClientException, ApiException {
        MatcherAssert.assertThat(
            this.queries.queries(false).get(0).build(),
            Matchers.equalTo(
                new AttachmentAddAudio(
                    new VkApiClient(
                        new TransportClientHttp()
                    ),
                    new UserActor(
                        0,
                        QueriesFromAttachmentsTest.TOKEN1
                    ),
                    0,
                    1
                ).upload().get(0).build()
            )
        );
    }

}
