/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Mikhail Yakushin
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

import com.driver733.vkmusicuploader.wallpost.attachment.TransportClientComplex;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientCached;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle AnonInnerLengthCheck (500 lines)
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (50 lines)
 */
public final class PostRootDirTest {

    /**
     * VK API endpoint - photos.saveWallPhoto.
     */
    private static final String PHOTO_SAVE_URL =
        "https://api.vk.com/method/photos.saveWallPhoto";
    /**
     * VK API endpoint - audio.save.
     */
    private static final String AUDIO_SAVE_URL =
        "https://api.vk.com/method/audio.save";

    /**
     * VK API endpoint - photos.getWallUploadServer.
     */
    private static final String PHOTO_WALL_URL =
        "https://api.vk.com/method/photos.getWallUploadServer";

    /**
     * VK API endpoint - audio.getUploadServer.
     */
    private static final String AUDIO_UPLOAD_URL =
        "https://api.vk.com/method/audio.getUploadServer";

    /**
     * VK API endpoint - audio.add.
     */
    private static final String AUDIO_ADD_URL =
        "https://api.vk.com/method/audio.add";

    /**
     * VK API endpoint - execute.
     */
    private static final String EXECUTE_URL =
        "https://api.vk.com/method/execute";

    @SuppressWarnings({
        "PMD.ExcessiveMethodLength",
        "PMD.NonStaticInitializer",
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule"
        })
    @Test
    public void test() throws IOException {
        new PostRootDir(
            new VkApiClient(
                new TransportClientComplex(
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
                                PostRootDirTest.AUDIO_SAVE_URL,
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
                                PostRootDirTest.PHOTO_SAVE_URL,
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
                                PostRootDirTest.AUDIO_ADD_URL,
                                new TransportClientCached(
                                    "{ \"response\" : 123456789 }"
                                )
                            );
                            put(
                                PostRootDirTest.EXECUTE_URL,
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
            new File(
                "src/test/resources/"
            ),
            new UploadServers(
                new VkApiClient(
                    new TransportClientComplex(
                        new HashMap<String, TransportClient>() {
                            {
                                put(
                                    PostRootDirTest.PHOTO_WALL_URL,
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
                                    PostRootDirTest.AUDIO_UPLOAD_URL,
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
                )
            )
        ).post();
        Assert.assertTrue(
            "The files differ",
            FileUtils.contentEquals(
                new File("src/test/resources/album/vkmu.properties"),
                new File("src/test/resources/testAlbum.properties")
            )
        );
    }

}
