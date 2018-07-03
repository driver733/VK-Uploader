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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.strings.AttachmentStringsFromJson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link AttachmentStringsFromJson}.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (100 lines)
 */
@Immutable
public final class AttachmentStringsFromJsonTest {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 161929264;

    @Test
    public void testObject() throws IOException {
        MatcherAssert.assertThat(
            "Failed to form an attachment attachmentString from JsonObject",
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        "{"
                            + "\"owner_id\"  : 1111111,"
                            + "\"id\"        : 1000000,"
                            + "\"artist\"    : \"Test Artist1\","
                            + "\"title\"     : \"Test Title1\","
                            + "\"url\"       : \"http://test1.com\" "
                            + "}",
                        JsonObject.class
                    )
                    .getAsJsonObject(),
                AttachmentStringsFromJsonTest.GROUP_ID
            ).attachmentStrings(),
            Matchers.containsInAnyOrder(
                "audio1111111_1000000"
            )
        );
    }

    @Test
    public void testArray() throws IOException {
        MatcherAssert.assertThat(
            "Failed to form an attachment attachmentString from JsonArray.",
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        "["
                            + "{"
                            + "\"owner_id\"  : 111111,"
                            + "\"id\"        : 3000000,"
                            + "\"artist\"    : \"Test Artist2\","
                            + "\"title\"     : \"Test Title2\","
                            + "\"url\"       : \"http://test2.com\" "
                            + "},"
                            + "{"
                            + "\"owner_id\"  : 2222222,"
                            + "\"id\"        : 2000000,"
                            + "\"artist\"    : \"Test Artist3\","
                            + "\"title\"     : \"Test Title3\","
                            + "\"url\"       : \"http://test3.com\" "
                            + "}"
                            + "]",
                        JsonArray.class
                    )
                    .getAsJsonArray(),
                AttachmentStringsFromJsonTest.GROUP_ID
            ).attachmentStrings(),
            Matchers.containsInAnyOrder(
                "audio111111_3000000", "audio2222222_2000000"
            )
        );
    }

    @Test(expected = IOException.class)
    public void testException() throws IOException {
        MatcherAssert.assertThat(
            "Failed to form an attachment attachmentString from JsonArray.",
            new AttachmentStringsFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        "["
                            + "{"
                            + "\"ppowner_id\" : 111111,"
                            + "\"iid\"        : 30000001,"
                            + "\"martist\"    : \"Test Artist2\","
                            + "\"dtitle\"     : \"Test Title2\","
                            + "\"furl\"       : \"http://test2.com\" "
                            + "},"
                            + "{"
                            + "\"downer_id\"  : 2222222,"
                            + "\"fid\"        : 20000001,"
                            + "\"dartist\"    : \"Test Artist3\","
                            + "\"ftitle\"     : \"Test Title3\","
                            + "\"durl\"       : \"http://test3.com\" "
                            + "}"
                            + "]",
                        JsonArray.class
                    )
                    .getAsJsonArray(),
                AttachmentStringsFromJsonTest.GROUP_ID
            ).attachmentStrings(),
            Matchers.containsInAnyOrder(
                "audio111111_30000001",
                "audio2222222_20000001"
            )
        );
    }

}
