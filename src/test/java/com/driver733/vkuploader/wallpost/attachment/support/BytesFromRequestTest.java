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

import com.jcabi.http.request.FakeRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Test for {@link BytesFromRequest}.
 *
 *
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class BytesFromRequestTest {

    /**
     * Test HTTP request body.
     */
    private static final String REQ_BODY = "Test request body";

    @Test
    public void testString() throws IOException {
        Assertions.assertThat(
            new String(
                new BytesFromRequest(
                    new FakeRequest()
                        .withBody(
                            BytesFromRequestTest.REQ_BODY.getBytes()
                        )
                ).asBytes(),
                StandardCharsets.UTF_8
            )
        ).isEqualTo(BytesFromRequestTest.REQ_BODY);
    }

}
