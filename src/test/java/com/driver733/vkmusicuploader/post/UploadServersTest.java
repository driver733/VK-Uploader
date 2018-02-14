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
package com.driver733.vkmusicuploader.post;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.io.IOException;
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
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UploadServersTest {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 161929264;

    @Test
    public void audio() throws IOException {
        MatcherAssert.assertThat(
            new UploadServers(
                new VkApiClient(
                    new TransportClientCached(
                        "{ \"upload_url\" : \"http://test.com/audio\" }"
                    )
                ),
                new UserActor(1, ""),
                UploadServersTest.GROUP_ID
            ).uploadUrl(UploadServers.Type.AUDIO),
            Matchers.equalTo("http://test.com/audio")
        );
    }

    @Test
    public void wallPhoto() throws IOException {
        MatcherAssert.assertThat(
            new UploadServers(
                new VkApiClient(
                    new TransportClientCached(
                        "{ \"upload_url\" : \"http://test.com/wallPhoto\" }"
                    )
                ),
                new UserActor(1, ""),
                UploadServersTest.GROUP_ID
            ).uploadUrl(UploadServers.Type.WALL_PHOTO),
            Matchers.equalTo("http://test.com/wallPhoto")
        );
    }

}
