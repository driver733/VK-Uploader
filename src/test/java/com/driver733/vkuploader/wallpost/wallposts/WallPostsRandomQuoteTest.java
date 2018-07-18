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
package com.driver733.vkuploader.wallpost.wallposts;

import com.driver733.vkuploader.post.UploadServers;
import com.driver733.vkuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkuploader.wallpost.support.AbstractVkUnitTest;
import com.driver733.vkuploader.wallpost.support.JsonPattern;
import com.jcabi.aspects.Immutable;
import com.jcabi.matchers.RegexMatchers;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link WallPostsRandomQuoteTest}.
 *
 * @since 0.1
 * @checkstyle AnonInnerLengthCheck (1000 lines)
 * @checkstyle JavadocMethodCheck (1000 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (1000 lines)
 * @checkstyle MethodLength (1000 lines)
 */
@Immutable
@SuppressWarnings({
    "PMD.ExcessiveMethodLength",
    "PMD.NonStaticInitializer",
    "PMD.AvoidDuplicateLiterals"
    })
public final class WallPostsRandomQuoteTest extends AbstractVkUnitTest {

    /**
     * Number of Wallposts to make.
     */
    private static final int N_WALLPOSTS = 2;

    /**
     * A {@link Path} to the directory with audios.
     */
    private static final Path AUDIOS =
        Paths.get("src/test/resources/music/album");

    /**
     * A {@link Path} to the directory with photos.
     */
    private static final Path PHOTOS =
        Paths.get("src/test/resources/photos/testPhotoAlbum");

    @Test
    public void testTextOnly() throws Exception {
        final String exec = new WallPostsRandomQuote(
            new VkApiClient(
                new TransportClientFake(
                    new HashMap<String, TransportClient>() {
                        {
                            put(
                                AbstractVkUnitTest.EXECUTE_URL,
                                new TransportClientCached(
                                    String.join(
                                        "",
                                        "{",
                                        "\"response\": { \"post_id\": 9 }",
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
            AbstractVkUnitTest.GROUP_ID,
            WallPostsRandomQuoteTest.N_WALLPOSTS
        ).postsQueries()
            .get(0)
            .build()
            .get("code");
        MatcherAssert.assertThat(
            "Constructed query does not match the expected one.",
            new JsonPattern(
                exec,
                Pattern.compile("\\{.*:.*}\\),")
            ).json()
                .entrySet()
                .stream()
                .collect(
                    HashMap::new,
                    (map, json) -> map.put(
                        json.getKey(),
                        json.getValue().getAsString()
                    ),
                    (map, mapp) -> { }
                ),
            Matchers.allOf(
                Matchers.hasEntry("owner_id", "-161929264"),
                Matchers.hasEntry("from_group", "1"),
                Matchers.hasValue(
                    RegexMatchers.containsPattern("[а-яА-Я]")
                )
            )
        );
    }

    @Test
    public void testWithPhoto() throws Exception {
        final String exec =  new WallPostsRandomQuote(
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
                                        "\"hash\"      : \"hash1236\",",
                                        "\"photo\"     : \"fnknjkasd\",",
                                        "\"server\"    : 1235464",
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
                                        "\"photo_75\"    : \"url12.com\",",
                                        "\"photo_130\"   : \"url13.com\",",
                                        "\"photo_604\"   : \"url31.com\",",
                                        "\"photo_807\"   : \"url15.com\",",
                                        "\"photo_1280\"  : \"url1e.com\",",
                                        "\"photo_2560\"  : \"url1d.com\",",
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
                                        "\"response\": { \"post_id\": 4 }",
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
                            }
                        }
                    )
                ),
                new UserActor(
                    1, "1"
                ),
                AbstractVkUnitTest.GROUP_ID
            ),
            AbstractVkUnitTest.GROUP_ID,
            WallPostsRandomQuoteTest.N_WALLPOSTS,
            WallPostsRandomQuoteTest.PHOTOS
        ).postsQueries()
            .get(0)
            .build()
            .get("code");
        MatcherAssert.assertThat(
            "Constructed query does not match the expected one.",
            new JsonPattern(
                exec,
                Pattern.compile("\\{.*:.*}\\),")
            ).json()
                .entrySet()
                .stream()
                .collect(
                    HashMap::new,
                    (map, json) -> map.put(
                        json.getKey(),
                        json.getValue().getAsString()
                    ),
                    (map, mapp) -> { }
                ),
            Matchers.allOf(
                Matchers.hasEntry("owner_id", "-161929264"),
                Matchers.hasEntry("from_group", "1"),
                Matchers.hasEntry("attachments", "photo6785_123456"),
                Matchers.hasValue(
                    RegexMatchers.containsPattern("[а-яА-Я]")
                )
            )
        );
    }

    @Test
    public void testWithAudio() throws Exception {
        final String exec =  new WallPostsRandomQuote(
            new VkApiClient(
                new TransportClientFake(
                    new HashMap<String, TransportClient>() {
                        {
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
                                        "\"response\": { \"post_id\": 4 }",
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
            new UploadServers(
                new VkApiClient(
                    new TransportClientFake(
                        new HashMap<String, TransportClient>() {
                            {
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
            AbstractVkUnitTest.GROUP_ID,
            WallPostsRandomQuoteTest.AUDIOS,
            WallPostsRandomQuoteTest.N_WALLPOSTS
        ).postsQueries()
            .get(0)
            .build()
            .get("code");
        MatcherAssert.assertThat(
            "Constructed query does not match the expected one.",
            new JsonPattern(
                exec,
                Pattern.compile("\\{.*:.*}\\),")
            ).json()
                .entrySet()
                .stream()
                .collect(
                    HashMap::new,
                    (map, json) -> map.put(
                        json.getKey(),
                        json.getValue().getAsString()
                    ),
                    (map, mapp) -> { }
                ),
            Matchers.allOf(
                Matchers.hasEntry("owner_id", "-161929264"),
                Matchers.hasEntry("from_group", "1"),
                Matchers.hasEntry("attachments", "audio-161929264_123456789"),
                Matchers.hasValue(
                    RegexMatchers.containsPattern("[а-яА-Я]")
                )
            )
        );
    }

    @Test
    public void testWithPhotoAndAudio() throws Exception {
        final String exec =  new WallPostsRandomQuote(
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
                                        "\"response\": { \"post_id\": 4 }",
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
            AbstractVkUnitTest.GROUP_ID,
            WallPostsRandomQuoteTest.N_WALLPOSTS,
            WallPostsRandomQuoteTest.PHOTOS,
            WallPostsRandomQuoteTest.AUDIOS
        ).postsQueries()
            .get(0)
            .build()
            .get("code");
        MatcherAssert.assertThat(
            "Constructed query does not match the expected one.",
            new JsonPattern(
                exec,
                Pattern.compile("\\{.*:.*}\\),")
            ).json()
                .entrySet()
                .stream()
                .collect(
                    HashMap::new,
                    (map, json) -> map.put(
                        json.getKey(),
                        json.getValue().getAsString()
                    ),
                    (map, mapp) -> { }
            ),
            Matchers.allOf(
                Matchers.hasEntry("owner_id", "-161929264"),
                Matchers.hasEntry("from_group", "1"),
                Matchers.hasEntry(
                    "attachments",
                    "photo6785_123456,audio-161929264_123456789"
                ),
                Matchers.hasValue(
                    RegexMatchers.containsPattern("[а-яА-Я]")
                )
            )
        );
    }

}
