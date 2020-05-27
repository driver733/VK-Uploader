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
package com.driver733.vkuploader.wallpost.attachment.support;

import com.driver733.vkuploader.wallpost.attachment.support.string.AttachmentStringFromJson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link AttachmentStringFromJson}.
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@Immutable
public final class AttachmentStringFromJsonTest {

    @Test
    public void test() throws IOException {
        Assertions.assertThat(
            new AttachmentStringFromJson(
                new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .fromJson(
                        String.join(
                            "",
                            "{",
                                "\"owner_id\"  : 1111111,",
                                "\"id\"        : 1000000,",
                                "\"artist\"    : \"Test Artist1\",",
                                "\"title\"     : \"Test Title1\",",
                                "\"url\"       : \"http://test1.com\"",
                                "}"
                        ),
                        JsonObject.class
                    ).getAsJsonObject(),
                1
            ).attachmentString()
        ).isEqualTo(
            "audio1111111_1000000"
        );
    }

}
