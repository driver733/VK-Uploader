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
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentFakeAudio;
import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.fields.AttachmentArrays;
import com.driver733.vkmusicuploader.wallpost.wallpost.file.RecoverableFile;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 */
public final class WallPostWithAttachmentsTest {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 161929264;

    /**
     * Test properties.
     */
    private final Path properties =
        Paths.get("src/test/resources/wallPostWithAttachmentsTest.properties");

    @Test
    public void test() throws IOException {
        final RecoverableFile props = new RecoverableFile(
            Files.readAllBytes(this.properties),
            this.properties
        );
        MatcherAssert.assertThat(
            "Incorrect query map produced.",
            new WallPostWithAttachments(
                new WallPostBase(
                    new VkApiClient(
                        new TransportClientCached("")
                    ),
                    new UserActor(
                        0,
                        "1"
                    )
                ),
                new AttachmentArrays(
                    new UserActor(
                        0,
                        "1"
                    ),
                    new ImmutableProperties(
                        this.properties.toFile()
                    ),
                    WallPostWithAttachmentsTest.GROUP_ID,
                    new AttachmentFakeAudio(
                        1, 2
                    )
                )
            ).construct().build(),
            Matchers.allOf(
                Matchers.hasEntry("access_token", "1"),
                Matchers.hasEntry("v", "5.63"),
                Matchers.hasEntry(
                    "attachments",
                    "audio-161929264_1,audio-161929264_2"
                )
            )
        );
        props.recover();
    }

}
