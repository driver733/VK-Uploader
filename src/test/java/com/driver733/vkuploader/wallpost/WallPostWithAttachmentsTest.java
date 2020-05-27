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
package com.driver733.vkuploader.wallpost;

import com.driver733.vkuploader.wallpost.attachment.AttachmentFakeAudio;
import com.driver733.vkuploader.wallpost.attachment.support.fields.AttachmentArraysWithProps;
import com.driver733.vkuploader.wallpost.support.AbstractVkUnitTest;
import com.driver733.vkuploader.wallpost.support.RecoverableFile;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link WallPostWithAttachments}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 */
@Immutable
public final class WallPostWithAttachmentsTest extends AbstractVkUnitTest {

    /**
     * Test properties.
     */
    private final Path properties =
        Paths.get("src/test/resources/wallPostWithAttachmentsTest.properties");

    @Test
    public void test() throws Exception {
        final RecoverableFile props = new RecoverableFile(
            Files.readAllBytes(
                this.properties
            ),
            this.properties
        );
        Assertions.assertThat(
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
                new AttachmentArraysWithProps(
                    new UserActor(
                        0,
                        "1"
                    ),
                    new PropsFile(
                        this.properties
                    ),
                    GROUP_ID,
                    new AttachmentFakeAudio(
                        1, 2
                    )
                )
            ).construct()
                .build()
        ).containsOnly(
            Assertions.entry("access_token", "1"),
            Assertions.entry("v", "5.63"),
            Assertions.entry(
                "attachments",
                "audio-161929264_1,audio-161929264_2"
            )
        );
        props.recover();
    }

}
