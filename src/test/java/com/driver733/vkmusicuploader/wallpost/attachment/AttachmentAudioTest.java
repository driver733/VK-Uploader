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
package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.wallpost.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadAudio;
import com.driver733.vkmusicuploader.wallpost.support.AbstractVkUnitTest;
import com.driver733.vkmusicuploader.wallpost.support.RecoverableFile;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import java.io.File;
import java.util.HashMap;
import org.cactoos.io.BytesOf;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link AttachmentAudioWithProps}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (50 lines)
 */
@Immutable
public final class AttachmentAudioTest extends AbstractVkUnitTest {

    @SuppressWarnings({
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule"
        })
    @Test
    public void test() throws Exception {
        final File props = new File(
            "src/test/resources/attachmentAudioTest.properties"
        );
        final RecoverableFile original = new RecoverableFile(
            new BytesOf(
                props
            ).asBytes(),
            props.toPath()
        );
        Assert.assertThat(
            new AttachmentAudioWithProps(
                new VkApiClient(
                    new TransportClientFake(
                        new HashMap<String, TransportClient>() {
                            {
                                put(
                                    "audio.uploadServer",
                                    new TransportClientCached(
                                        "{"
                                            + "\"hash\"     : \"hash123\","
                                            + "\"audio\"    : \"fnknjkasd\","
                                            + "\"server\"   : 123546,"
                                            + "\"redirect\" : \"redirect.com\""
                                            + "}"
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.AUDIO_SAVE_URL,
                                    new TransportClientCached(
                                        "{"
                                            + "\"id\"       : 1,"
                                            + "\"owner_id\" : 2,"
                                            + "\"artist\"   : \"Clean Tears\","
                                            + "\"title\"    : \"Dragon\","
                                            + "\"url\"      : \"url1.com\""
                                            + "}"
                                    )
                                );
                            }
                        }
                    )
                ),
                new UserActor(
                    0,
                    "1"
                ),
                new ImmutableProperties(
                    props
                ),
                AbstractVkUnitTest.GROUP_ID,
                new UploadAudio(
                    new VkApiClient(
                        new TransportClientCached(
                            "{"
                                + "\"hash\"     : \"hash123\","
                                + "\"audio\"    : \"fnknjkasd\","
                                + "\"server\"   : 123546,"
                                + "\"redirect\" : \"redirect.com\""
                                + "}"
                        )
                    ),
                    "audio.uploadServer",
                    new File("src/test/resources/album/test.mp3")
                )
            ).upload()
                .get(0)
                .build(),
            Matchers.equalTo(
                new AudioAddQuery(
                    new VkApiClient(
                        new TransportClientCached(
                            "{ }"
                        )
                    ),
                    new UserActor(
                        0,
                        "1"
                    ),
                    1,
                    2
                ).groupId(AbstractVkUnitTest.GROUP_ID)
                    .build()
            )
        );
        original.recover();
    }

}
