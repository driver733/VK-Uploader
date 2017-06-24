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
package com.driver733.vkmusicuploader.wallpost.attachment;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioAddQueryCached;
import com.driver733.vkmusicuploader.wallpost.attachment.support.CachedExecuteBatchQueryClient;
import com.driver733.vkmusicuploader.wallpost.attachment.support.QueryResultsBasic;
import com.driver733.vkmusicuploader.wallpost.attachment.support.TransportClientCached;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.audio.AudioAddQuery;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class AttachmentAddAudioTest {

    @Test
    public void testBasic() throws ClientException, ApiException {
        MatcherAssert.assertThat(
             new AttachmentAddAudio(
                new UserActor(123, "testToken"), 111, 222,
                new VkApiClient(
                    new HttpTransportClient()
                )
            ).upload().get(0).build(),
            Matchers.equalTo(
                new AudioAddQuery(
                    new VkApiClient(
                        new HttpTransportClient()
                    ),
                    new UserActor(123, "testToken"),
                    222,
                    111
                ).groupId(92444715).build()
            )
        );
    }

    @Test
    public void cached() throws ClientException, ApiException {
        MatcherAssert.assertThat(
            new AttachmentAddAudio(
                new UserActor(123, "testToken"), 111, 222,
                new VkApiClient(
                    new TransportClientCached(
                        new JsonParser().parse(
                            new JsonReader(
                                new StringReader(
                                    String.format(
                                        "{\"response\" : %d}",
                                        123456
                                    )
                                )
                            )
                        )
                    )
                )
            ).upload().get(0).execute(),
            Matchers.equalTo(
            123456
            )
        );
    }





}
