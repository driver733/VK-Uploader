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
package com.driver733.vkuploader.post.posts;

import com.driver733.vkuploader.post.UploadServers;
import com.driver733.vkuploader.wallpost.ImmutableProperties;
import com.driver733.vkuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkuploader.wallpost.support.AbstractVkUnitTest;
import com.driver733.vkuploader.wallpost.wallposts.WallPostsMusicAlbum;
import com.driver733.vkuploader.wallpost.wallposts.WallPostsPhotoAlbum;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link PostsBasic}.
 *
 * @since 0.2
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (500 lines)
 */
public final class PostsBasicTest implements AbstractVkUnitTest {

    @Test
    @SuppressWarnings({
        "PMD.ExcessiveMethodLength",
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule"
        })
    public void testPhotoAlbum() throws Exception {
        final Path root = Paths.get("src/test/resources/photos/");
        root.resolve("testPhotoAlbum")
            .resolve("vkmu.properties")
            .toFile()
            .deleteOnExit();
        root.resolve("vkmu.properties")
            .toFile()
            .deleteOnExit();
        new PostsBasic(
            new WallPostsPhotoAlbum(
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
                                    AbstractVkUnitTest.PHOTO_SAVE_URL,
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
                                    AbstractVkUnitTest.EXECUTE_URL,
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
                    1, ""
                ),
                root.resolve("testPhotoAlbum"),
                new UploadServers(
                    new VkApiClient(
                        new TransportClientFake(
                            new HashMap<String, TransportClient>() {
                                {
                                    put(
                                        AbstractVkUnitTest.PHOTO_WALL_URL,
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
                                    put(
                                        AbstractVkUnitTest.AUDIO_UPLOAD_URL,
                                        new TransportClientCached(
                                            String.join(
                                                "",
                                                "{",
                                                "\"response\": {",
                                                "\"upload_url\" :",
                                                "\"audio.uploadServer\"",
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
                    AbstractVkUnitTest.GROUP_ID
                ),
                new ImmutableProperties(
                    root.resolve("testPhotoAlbum")
                        .resolve("vkmu.properties")
                        .toFile()
                ),
                AbstractVkUnitTest.GROUP_ID
            )
        ).postFromDir(
            root
        ).post();
        TimeUnit.SECONDS.sleep(1);
        MatcherAssert.assertThat(
            "The properties files differ",
            new ImmutableProperties(
                root.resolve("testPhotoAlbum")
                    .resolve("vkmu.properties")
                    .toFile()
            ).loaded()
                .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue
                )
            ),
                Matchers.allOf(
                    Matchers.hasEntry(
                        "1.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "2.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "3.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "4.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "5.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "6.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "7.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "8.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "9.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "10.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "11.jpg", "1"
                    ),
                    Matchers.hasEntry(
                        "12.jpg", "1"
                    )
                )
        );
    }

    @Test
    @SuppressWarnings({
        "PMD.ExcessiveMethodLength",
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule"
        })
    public void testMusicAlbum() throws Exception {
        final Path root = Paths.get("src/test/resources/music/");
        root.resolve("album")
            .resolve("vkmu.properties")
            .toFile()
            .deleteOnExit();
        new PostsBasic(
            new WallPostsMusicAlbum(
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
                                    "audio.uploadServer",
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"hash\"     : \"hash123\",",
                                            "\"audio\"    : \"fnknjkasd\",",
                                            "\"server\"   : 123546,",
                                            "\"redirect\" : \"redirect.com\"",
                                            "}"
                                        )
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.AUDIO_SAVE_URL,
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"id\"       : 123456,",
                                            "\"owner_id\" : 5674,",
                                            "\"artist\"   : \"Clean Tears\",",
                                            "\"title\"    : \"Dragon\",",
                                            "\"url\"      : \"url1.com\"",
                                            "}"
                                        )
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.PHOTO_SAVE_URL,
                                    new TransportClientCached(
                                        String.join(
                                            "",
                                            "{",
                                            "\"id\"          : 12345,",
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
                                    AbstractVkUnitTest.AUDIO_ADD_URL,
                                    new TransportClientCached(
                                        "{ \"response\" : 123456789 }"
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.EXECUTE_URL,
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
                    1, ""
                ),
                root.resolve("album"),
                new UploadServers(
                    new VkApiClient(
                        new TransportClientFake(
                            new HashMap<String, TransportClient>() {
                                {
                                    put(
                                        AbstractVkUnitTest.PHOTO_WALL_URL,
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
                                    put(
                                        AbstractVkUnitTest.AUDIO_UPLOAD_URL,
                                        new TransportClientCached(
                                            String.join(
                                                "",
                                                "{",
                                                "\"response\": {",
                                                "\"upload_url\" :",
                                                "\"audio.uploadServer\"",
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
                    AbstractVkUnitTest.GROUP_ID
                ),
                new ImmutableProperties(
                    root.resolve("album")
                        .resolve("vkmu.properties")
                        .toFile()
                ),
                AbstractVkUnitTest.GROUP_ID
            )
            ).postFromDir(
                root
        ).post();
        TimeUnit.SECONDS.sleep(1);
        MatcherAssert.assertThat(
            "The properties files differ",
            new ImmutableProperties(
                root.resolve("album")
                    .resolve("vkmu.properties")
                    .toFile()
            ).loaded()
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                    )
                ),
            Matchers.allOf(
                Matchers.hasEntry("test.mp3", "2_123456789"),
                Matchers.hasEntry("testMissingTags.mp3", "2_123456789")
            )
        );
    }

}
