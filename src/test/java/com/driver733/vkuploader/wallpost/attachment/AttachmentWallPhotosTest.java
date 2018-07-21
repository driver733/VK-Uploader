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

import com.driver733.vkuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkuploader.wallpost.support.AbstractVkUnitTest;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.photos.PhotosSaveWallPhotoQuery;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link AttachmentWallPhotos}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (50 lines)
 */
public final class AttachmentWallPhotosTest extends AbstractVkUnitTest {

    @Test
    @SuppressWarnings({
        "PMD.ExcessiveMethodLength",
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals"
        })
    public void test() throws Exception {
        new File(
            "src/test/resources/photos/testPhotoAlbum/vkmu.properties"
        ).deleteOnExit();
        final String dir = "src/test/resources/photos/testPhotoAlbum";
        final List<Path> photos = new ListOf<>(
            Paths.get(String.format("%s/1.jpg", dir)),
            Paths.get(String.format("%s/2.jpg", dir)),
            Paths.get(String.format("%s/3.jpg", dir)),
            Paths.get(String.format("%s/4.jpg", dir)),
            Paths.get(String.format("%s/5.jpg", dir)),
            Paths.get(String.format("%s/6.jpg", dir)),
            Paths.get(String.format("%s/7.jpg", dir)),
            Paths.get(String.format("%s/8.jpg", dir)),
            Paths.get(String.format("%s/9.jpg", dir)),
            Paths.get(String.format("%s/10.jpg", dir)),
            Paths.get(String.format("%s/11.jpg", dir)),
            Paths.get(String.format("%s/12.jpg", dir))
        );
        final List<AbstractQueryBuilder> queries = new AttachmentWallPhotos(
            new VkApiClient(
                new TransportClientFake(
                    new HashMap<String, TransportClient>() {
                        {
                            put(
                                AbstractVkUnitTest.PHOTO_SAVE_URL,
                                new TransportClientCached(
                                    String.join(
                                        "",
                                        "{",
                                        " \"photo\"  : \"testPhoto\",",
                                        " \"server\" : 1,",
                                        " \"hash\"   : \"testHash\"",
                                        " }"
                                    )
                                )
                            );
                        }
                    }
                )
            ),
            new UserActor(0, "1"),
            AbstractVkUnitTest.PHOTO_SAVE_URL,
            photos,
            AbstractVkUnitTest.GROUP_ID
           ).upload();
        final ArrayList<Map<String, String>> list = new ArrayList<>(12);
        for (final AbstractQueryBuilder query : queries) {
            list.add(
                query.build()
            );
        }
        MatcherAssert.assertThat(
            "",
            list,
            Matchers.equalTo(
            Collections.nCopies(
                photos.size(),
                new PhotosSaveWallPhotoQuery(
                    new VkApiClient(
                        new TransportClientCached(
                            String.join(
                                "",
                                "{",
                                " \"photo\"  : \"testPhoto\",",
                                " \"server\" : 1,",
                                " \"hash\"   : \"testHash\"",
                                " }"
                            )
                        )
                   ),
                   new UserActor(0, "1"),
                   "testPhoto"
               ).hash("testHash")
                   .groupId(AbstractVkUnitTest.GROUP_ID)
                   .server(1)
                   .build()
                )
           )
        );
    }

}
