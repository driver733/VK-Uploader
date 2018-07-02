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

import com.driver733.vkmusicuploader.post.posts.PostsBasic;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.TransportClientFake;
import com.driver733.vkmusicuploader.wallpost.wallpost.AbstractVkUnitTest;
import com.driver733.vkmusicuploader.wallpost.wallpost.wallposts.WallPostsMusicAlbum;
import com.driver733.vkmusicuploader.wallpost.wallpost.wallposts.WallPostsPhotoAlbum;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link PostsBasic}.
 *
 * @author Mikhail Yakushin (yakushin@terpmail.umd.edu)
 * @version $Id$
 * @since 0.2
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (500 lines)
 */
public final class PostsBasicTest extends AbstractVkUnitTest {

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
        new PostsBasic(
            new WallPostsPhotoAlbum(
                new VkApiClient(
                    new TransportClientFake(
                        new HashMap<String, TransportClient>() {
                            {
                                put(
                                    "photos.wallUploadServer",
                                    new TransportClientCached(
                                        "{"
                                            + "\"hash\"      : \"hash123\","
                                            + "\"photo\"     : \"fnknjkasd\","
                                            + "\"server\"    : 123546"
                                            + "}"
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.PHOTO_SAVE_URL,
                                    new TransportClientCached(
                                        "{"
                                            + "\"id\"          : 123456,"
                                            + "\"album_id\"    : 5674,"
                                            + "\"owner_id\"    : 6785,"
                                            + "\"user_id\"     : 4356,"
                                            + "\"sizes\"       : ["
                                            + "{"
                                            + "\"src\": \"src\","
                                            + "\"width\": 100,"
                                            + "\"height\": 100"
                                            + "}"
                                            + "],"
                                            + "\"photo_75\"    : \"url1.com\","
                                            + "\"photo_130\"   : \"url1.com\","
                                            + "\"photo_604\"   : \"url1.com\","
                                            + "\"photo_807\"   : \"url1.com\","
                                            + "\"photo_1280\"  : \"url1.com\","
                                            + "\"photo_2560\"  : \"url1.com\","
                                            + "\"photo_id\"    : 3456,"
                                            + "\"width\"       : 500,"
                                            + "\"height\"      : 500,"
                                            + "\"date\"        : 1502919105,"
                                            + "\"lat\"         : 56.3456,"
                                            + "\"long\"        : 54.9645,"
                                            + "\"access_key\"  : \"sjdkfk\""
                                            + "}"
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.EXECUTE_URL,
                                    new TransportClientCached(
                                        "{"
                                            + "\"response\": { \"post_id\": 3 }"
                                            + "}"
                                    )
                                );
                            }
                        }
                    )
                ),
                new UserActor(
                    1, ""
                ),
                root.resolve("testPhotoAlbum")
                    .toFile(),
                new UploadUrls(
                    new VkApiClient(
                        new TransportClientFake(
                            new HashMap<String, TransportClient>() {
                                {
                                    put(
                                        AbstractVkUnitTest.PHOTO_WALL_URL,
                                        new TransportClientCached(
                                            "{"
                                                + "\"response\" : {"
                                                + "\"upload_url\" :"
                                                + "\"photos.wallUploadServer\","
                                                + "\"album_id\"   : 169819278,"
                                                + "\"user_id\"    : 185014513"
                                                + "}"
                                                + "}"
                                        )
                                    );
                                    put(
                                        AbstractVkUnitTest.AUDIO_UPLOAD_URL,
                                        new TransportClientCached(
                                            "{"
                                                + "\"response\": {"
                                                + "\"upload_url\" :"
                                                + "\"audio.uploadServer\""
                                                + "}"
                                                + "}"
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
            root.toFile()
        ).post();
        MatcherAssert.assertThat(
            "The properties files differ",
            new ImmutableProperties(
                root.resolve("testPhotoAlbum")
                    .resolve("vkmu.properties")
                    .toFile()
            ).entrySet(),
            Matchers.equalTo(
                new ImmutableProperties(
                    root.resolve("testPhotoAlbum")
                        .resolve("expected.properties")
                        .toFile()
                ).entrySet()
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
    public void testMusicAlbum() {
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
                                        "{"
                                            + "\"hash\"      : \"hash123\","
                                            + "\"photo\"     : \"fnknjkasd\","
                                            + "\"server\"    : 123546"
                                            + "}"
                                    )
                                );
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
                                            + "\"id\"       : 123456,"
                                            + "\"owner_id\" : 5674,"
                                            + "\"artist\"   : \"Clean Tears\","
                                            + "\"title\"    : \"Dragon\","
                                            + "\"url\"      : \"url1.com\""
                                            + "}"
                                    )
                                );
                                put(
                                    AbstractVkUnitTest.PHOTO_SAVE_URL,
                                    new TransportClientCached(
                                        "{"
                                            + "\"id\"          : 12345,"
                                            + "\"album_id\"    : 5674,"
                                            + "\"owner_id\"    : 6785,"
                                            + "\"user_id\"     : 4356,"
                                            + "\"sizes\"       : ["
                                            + "{"
                                            + "\"src\": \"src\","
                                            + "\"width\": 100,"
                                            + "\"height\": 100"
                                            + "}"
                                            + "],"
                                            + "\"photo_75\"    : \"url1.com\","
                                            + "\"photo_130\"   : \"url1.com\","
                                            + "\"photo_604\"   : \"url1.com\","
                                            + "\"photo_807\"   : \"url1.com\","
                                            + "\"photo_1280\"  : \"url1.com\","
                                            + "\"photo_2560\"  : \"url1.com\","
                                            + "\"photo_id\"    : 3456,"
                                            + "\"width\"       : 500,"
                                            + "\"height\"      : 500,"
                                            + "\"date\"        : 1502919105,"
                                            + "\"lat\"         : 56.3456,"
                                            + "\"long\"        : 54.9645,"
                                            + "\"access_key\"  : \"sjdkfk\""
                                            + "}"
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
                                        "{"
                                            + "\"response\": { \"post_id\": 3 }"
                                            + "}"
                                    )
                                );
                            }
                        }
                    )
                ),
                new UserActor(
                    1, ""
                ),
                root.resolve("album")
                    .toFile(),
                new UploadUrls(
                    new VkApiClient(
                        new TransportClientFake(
                            new HashMap<String, TransportClient>() {
                                {
                                    put(
                                        AbstractVkUnitTest.PHOTO_WALL_URL,
                                        new TransportClientCached(
                                            "{"
                                                + "\"response\" : {"
                                                + "\"upload_url\" :"
                                                + "\"photos.wallUploadServer\","
                                                + "\"album_id\"   : 169819278,"
                                                + "\"user_id\"    : 185014513"
                                                + "}"
                                                + "}"
                                        )
                                    );
                                    put(
                                        AbstractVkUnitTest.AUDIO_UPLOAD_URL,
                                        new TransportClientCached(
                                            "{"
                                                + "\"response\": {"
                                                + "\"upload_url\" :"
                                                + "\"audio.uploadServer\""
                                                + "}"
                                                + "}"
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
                root.toFile()
        );
        MatcherAssert.assertThat(
            "The properties files differ",
            new ImmutableProperties(
                root.resolve("album")
                    .resolve("vkmu.properties")
                    .toFile()
            ).entrySet(),
            Matchers.equalTo(
                new ImmutableProperties(
                    root.resolve("album")
                        .resolve("expected.properties")
                        .toFile()
                ).entrySet()
            )
        );
    }

}
