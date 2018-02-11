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

import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback.Fallback;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.photos.PhotosSaveWallPhotoQuery;
import java.io.IOException;
import java.nio.file.Files;
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
 * @todo #15 Replace constant with maven parameter.
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (20 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 */
public final class AttachmentWallPhotoTest {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void test() throws ClientException, ApiException, IOException {
        MatcherAssert.assertThat(
            new AttachmentWallPhoto(
                new VkApiClient(
                    new TransportClientCached("1")
                ),
                new UserActor(0, ""),
                new UploadWallPhoto(
                    new VkApiClient(
                        new TransportClientCached(
                        "{"
                            + " \"photo\" : \"testPhoto\","
                            + " \"server\" : 1,"
                            + " \"hash\" : \"testHash\""
                                + " }"
                        )
                    ),
                    "",
                    new Fallback<byte[]>() {
                        @Override
                        public byte[] firstValid() throws IOException {
                            return Files.readAllBytes(
                                Paths.get(
                                    "src/test/resources/album/albumCover.jpg"
                                )
                            );
                        }
                    }
                )
            ).upload().get(0).build(),
            Matchers.equalTo(
                new PhotosSaveWallPhotoQuery(
                    new VkApiClient(
                        new TransportClientCached(
                            "{"
                                + " \"photo\" : \"testPhoto\","
                                + " \"server\" : 1,"
                                + " \"hash\" : \"testHash\""
                                + " }"
                        )
                    ),
                    new UserActor(0, ""),
                    "testPhoto"
                ).hash("testHash")
                    .groupId(AttachmentWallPhotoTest.GROUP_ID)
                    .server(1)
                    .build()
            )
        );
    }

}
