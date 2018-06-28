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
package com.driver733.vkmusicuploader.wallpost.attachment;

import com.driver733.vkmusicuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallDocument;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.docs.DocsSaveQuery;
import java.io.File;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link AttachmentWallDocument}.
 *
 * @author Mikhail Yakushin (yakushin@terpmail.umd.edu)
 * @version $Id$
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (50 lines)
 */
@Immutable
public final class AttachmentWallDocumentTest {

    @SuppressWarnings({
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule"
        })
    @Test
    public void test() throws Exception {
        Assert.assertThat(
            "Query constructed incorrectly",
            new AttachmentWallDocument(
                new VkApiClient(
                    new TransportClientFake()
                ),
                new UserActor(
                    0,
                    "1"
                ),
                new UploadWallDocument(
                    new VkApiClient(
                        new TransportClientCached(
                            "{"
                                + "\"file\" : \"testingFile\""
                                + "}"
                        )
                    ),
                    "testURL",
                    new File("src/test/resources/album/test.mp3")
                )
            ).upload()
                .get(0)
                .build(),
            Matchers.equalTo(
                new DocsSaveQuery(
                    new VkApiClient(
                        new TransportClientFake()
                    ),
                    new UserActor(
                        0,
                        "1"
                    ),
                    "testingFile"
                ).build()
            )
        );
    }

}
