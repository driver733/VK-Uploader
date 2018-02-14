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
package com.driver733.vkmusicuploader.wallpost.attachment.support.queries;

import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentAddAudio;
import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.safe.QueriesSafeCached;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * {@link QueriesSafeCached} test class.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class QueriesSafeCachedTest {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 161929264;

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

    @Test
    public void test() throws IOException, ClientException, ApiException {
        MatcherAssert.assertThat(
            "Queries do not match.",
            new QueriesSafeCached(
                new QueriesFromAttachments(
                    new Array<>(
                        new AttachmentAddAudio(
                            new VkApiClient(
                                new TransportClientHttp()
                            ),
                            new UserActor(
                                0,
                                QueriesSafeCachedTest.TOKEN1
                            ),
                            0,
                            1,
                            QueriesSafeCachedTest.GROUP_ID
                        ),
                        new AttachmentAddAudio(
                            new VkApiClient(
                                new TransportClientCached(
                                    QueriesSafeCachedTest.CACHE
                                )
                            ),
                            new UserActor(
                                0,
                                QueriesSafeCachedTest.TOKEN2
                            ),
                            0,
                            2,
                            QueriesSafeCachedTest.GROUP_ID
                        )
                    )
                ).queries()
            ).queries().get(0).build(),
            Matchers.equalTo(
                new AttachmentAddAudio(
                    new VkApiClient(
                        new TransportClientCached(
                            QueriesSafeCachedTest.CACHE
                        )
                    ),
                    new UserActor(
                        0,
                        QueriesSafeCachedTest.TOKEN2
                    ),
                    0,
                    2,
                    QueriesSafeCachedTest.GROUP_ID
                ).upload().get(0).build()
            )
        );
    }
}
