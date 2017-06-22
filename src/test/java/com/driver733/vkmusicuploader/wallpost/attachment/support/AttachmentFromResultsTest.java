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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.IOException;
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
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (50 lines)
 * @checkstyle StringLiteralsConcatenationCheck (100 lines)
 * @checkstyle CascadeIndentationCheck (50 lines)
 */
public final class AttachmentFromResultsTest {

    @Test
    public void statusUploadedAudio() throws IOException {
        MatcherAssert.assertThat(
            new AttachmentsFromResults(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        "["
                                + "{"
                                    + "\"owner_id\"  : 1111111,"
                                    + "\"id\"        : 1000000,"
                                    + "\"artist\"    : \"Test Artist1\","
                                    + "\"title\"     : \"Test Title1\","
                                    + "\"url\"       : \"http://test1.com\" "
                                + "},"
                                + "{"
                                    + "\"owner_id\"  : 2222222,"
                                    + "\"id\"        : 2000000,"
                                    + "\"artist\"    : \"Test Artist2\","
                                    + "\"title\"     : \"Test Title2\","
                                    + "\"url\"       : \"http://test2.com\" "
                                + "}"
                            + "]",
                        JsonArray.class
                    )
                    .getAsJsonArray()
            ).attachments(),
            Matchers.containsInAnyOrder(
                "audio1111111_1000000", "audio2222222_2000000"
            )
        );
    }

    @Test
    public void statusUploadedPhoto() throws IOException {
        MatcherAssert.assertThat(
            new AttachmentsFromResults(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        "["
                            + "{"
                            + "\"owner_id\"     : 111111,"
                            + "\"id\"           : 333333,"
                            + "\"album_id\"     : 121212,"
                            + "\"user_id\"      : 100,"
                            + "\"date\"         : 14981391923,"
                            + "\"text\"         : \"Test description\","
                            + "\"photo_75\"     : \"http://vk.com/test67567\","
                            + "\"photo_130\"    : \"http://vk.com/test11223\","
                            + "\"photo_604\"    : \"http://vk.com/test2323\","
                            + "\"photo_807\"    : \"http://vk.com/test345344\","
                            + "\"photo_1280\"   : \"http://vk.com/test34534\","
                            + "\"photo_2560\"   : \"http://vk.com/test345435\","
                            + "\"width\"        : 12803,"
                            + "\"heigth\"       : 10254"
                            + "},"
                            + "{"
                            + "\"owner_id\"     : 222222,"
                            + "\"id\"           : 444444,"
                            + "\"album_id\"     : 1212123,"
                            + "\"user_id\"      : 101,"
                            + "\"date\"         : 1498139192,"
                            + "\"text\"         : \"Test description2\","
                            + "\"photo_75\"     : \"http://vk.com/test34\","
                            + "\"photo_130\"    : \"http://vk.com/test456\","
                            + "\"photo_604\"    : \"http://vk.com/test4564\","
                            + "\"photo_807\"    : \"http://vk.com/test34654\","
                            + "\"photo_1280\"   : \"http://vk.com/test99\","
                            + "\"photo_2560\"   : \"http://vk.com/test534656\","
                            + "\"width\"        : 12801,"
                            + "\"heigth\"       : 10243"
                            + "}"
                            + "]",
                        JsonArray.class
                    )
                    .getAsJsonArray()
            ).attachments(),
            Matchers.containsInAnyOrder(
                "photo111111_333333", "photo222222_444444"
            )
        );
    }

    @Test
    public void statusAdded() throws IOException {
        MatcherAssert.assertThat(
            new AttachmentsFromResults(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson("[123456789, 987654321]", JsonArray.class)
                    .getAsJsonArray()
            ).attachments(),
            Matchers.containsInAnyOrder(
                "audio-92444715_123456789", "audio-92444715_987654321"
            )
        );
    }

}
