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
package com.driver733.vkuploader.wallpost.attachment.message;

import com.jcabi.aspects.Immutable;
import com.jcabi.http.request.FakeRequest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link MessageWithRandomQuote}.
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle AvoidDuplicateLiterals (200 lines)
 */
@Immutable
public final class MessageWithRandomQuoteTest {

    @Test
    public void testQuote() throws Exception {
        MatcherAssert.assertThat(
            "Quote text is does not match the answer",
            new MessageWithRandomQuote(
                new FakeRequest()
                    .withBody(
                        String.join(
                            "",
                            "{",
                            "\"quoteText\":\"Общественной жизни.\",",
                            "\"quoteAuthor\":\"Лев Толстой\",",
                            "\"senderName\":\"test\",",
                            "\"senderLink\":\"test\",",
                            "\"quoteLink\":\"http://forismatic.com/ru/711d/\"",
                            "}"
                        )
                    )
            ).value(),
            Matchers.equalTo(
                String.format(
                    "%s%n© %s",
                    "Общественной жизни.",
                    "Лев Толстой"
                )
            )
        );
    }

}
