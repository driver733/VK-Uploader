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

import com.driver733.vkuploader.post.UploadServersBasic;
import com.driver733.vkuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkuploader.wallpost.support.AbstractVkUnitTest;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link WallPostPhotoAlbum}.
 *
 * @since 0.2
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (500 lines)
 */
public final class WallPostPhotoAlbumTest extends AbstractVkUnitTest {

    @Test
    @SuppressWarnings({
        "PMD.ExcessiveMethodLength",
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals"
        })
    public void test() throws Exception {
        final List<Path> photos = Files.walk(
            Paths.get("src/test/resources/photos/testPhotoAlbum")
        ).filter(
            path -> path.toString().endsWith(".jpg")
        ).collect(
            Collectors.toList()
        );
        Assertions.assertThat(
            new WallPostPhotoAlbum(
                new VkApiClient(
                    new TransportClientFake(
                        new HashMap<String, TransportClient>() {
                            {
                                put(
                                    "photos.wallUploadServer",
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"hash\"      : \"hash123\",",
                                            "\"photo\"     : \"fnknjkasd\",",
                                            "\"server\"    : 123546",
                                            "}"
                                        )
                                    )
                                );
                                put(
                                    PHOTO_SAVE_URL,
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"id\"          : 123456,",
                                            "\"album_id\"    : 5674,",
                                            "\"owner_id\"    : 6785,",
                                            "\"user_id\"     : 4356,",
                                            "\"sizes\"       : [",
                                            "{",
                                            "\"src\": \"src\",",
                                            "\"width\": 100,",
                                            "\"height\": 100",
                                            "}",
                                            "],",
                                            "\"photo_75\"    : \"url1.com\",",
                                            "\"photo_130\"   : \"url1.com\",",
                                            "\"photo_604\"   : \"url1.com\",",
                                            "\"photo_807\"   : \"url1.com\",",
                                            "\"photo_1280\"  : \"url1.com\",",
                                            "\"photo_2560\"  : \"url1.com\",",
                                            "\"photo_id\"    : 3456,",
                                            "\"width\"       : 500,",
                                            "\"height\"      : 500,",
                                            "\"date\"        : 1502919105,",
                                            "\"lat\"         : 56.3456,",
                                            "\"long\"        : 54.9645,",
                                            "\"access_key\"  : \"sjdkfk\"",
                                            "}"
                                        )
                                    )
                                );
                                put(
                                    EXECUTE_URL,
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"response\": { \"post_id\": 3 }",
                                            "}"
                                        )
                                    )
                                );
                            }
                        }
                        )
                ),
                new UserActor(
                    1,
                    "1"
                ),
                photos,
                new UploadServersBasic(
                    new VkApiClient(
                        new TransportClientFake(
                            new HashMap<String, TransportClient>() {
                                {
                                    put(
                                        PHOTO_WALL_URL,
                                        new TransportClientCached(
                                            String.join(
                                                "",
                                                "{",
                                                "\"response\" : {",
                                                "\"upload_url\" :",
                                                "\"photos.wallUploadServer\",",
                                                "\"album_id\"   : 169819278,",
                                                "\"user_id\"    : 185014513",
                                                "}",
                                                "}"
                                            )
                                        )
                                    );
                                }
                            }
                        )
                    ),
                    new UserActor(
                        1, "1"
                    ),
                    GROUP_ID
                ),
                GROUP_ID
            ).construct()
                .build()
        ).containsOnly(
            Assertions.entry("access_token", "1"),
            Assertions.entry("v", "5.63"),
            Assertions.entry(
                "attachments",
                StringUtils.join(
                    Collections.nCopies(
                        photos.size(),
                        "photo6785_123456"
                    ),
                    ","
                )
            ),
            Assertions.entry("owner_id", "-161929264"),
            Assertions.entry("from_group", "1")
        );
    }

}
