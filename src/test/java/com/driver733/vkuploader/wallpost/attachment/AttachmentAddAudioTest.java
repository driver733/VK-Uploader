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
package com.driver733.vkuploader.wallpost.attachment;

import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * An {@link Attachment} of an audios
 *  that will be added to a page audios (copied).
 *
 *
 *
 * @since 0.1
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@Immutable
public final class AttachmentAddAudioTest {

    @Test
    public void testBasic() {
        Assertions.assertThat(
            new AttachmentAddAudio(
                new VkApiClient(
                    new TransportClientHttp()
                ),
                new UserActor(
                    0,
                    ""
                ),
                1,
                2,
                1
            ).upload().get(0).build()
        ).isEqualTo(
            new AudioAddQuery(
                new VkApiClient(
                    new TransportClientHttp()
                ),
                new UserActor(0, ""),
                2,
                1
            ).groupId(
                1
            ).build()
        );
    }

    @Test
    public void cached() throws ClientException, ApiException {
        Assertions.assertThat(
            new AttachmentAddAudio(
                new VkApiClient(
                    new TransportClientCached("{ \"response\" : 1 }")
                ),
                new UserActor(
                    0,
                    ""
                ),
                0,
                0,
                1
            ).upload().get(0).execute()
        ).isEqualTo(1);
    }

}
