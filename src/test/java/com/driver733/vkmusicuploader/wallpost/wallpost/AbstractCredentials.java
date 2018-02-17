/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2018 Mikhail Yakushin
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.File;
import java.util.Properties;
import org.cactoos.Scalar;
import org.cactoos.io.InputOf;
import org.cactoos.iterable.PropertiesOf;

/**
 * VK credentials for IT.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 */
public abstract class AbstractCredentials {

    /**
     * Properties with credentials.
     */
    private final Scalar<Properties> props;

    /**
     * Ctor.
     */
    protected AbstractCredentials() {
        this.props = new PropertiesOf(
            new InputOf(
                new File(
                    System.getProperty("credentials")
                )
            )
        );
    }

    /**
     * VK user ID.
     * @return User ID.
     * @throws Exception If properties cannot be read.
     */
    protected int userId() throws Exception {
        return Integer.parseInt(
            String.valueOf(
                this.props.value().get("userId")
            )
        );
    }

    /**
     * VK group ID.
     * @return Group ID.
     * @throws Exception If properties cannot be read.
     */
    protected int groupId() throws Exception {
        return Integer.parseInt(
            String.valueOf(
                this.props.value().get("groupId")
            )
        );
    }

    /**
     * VK token.
     * @return Token.
     * @throws Exception If properties cannot be read.
     */
    protected String token() throws Exception {
        return String.valueOf(
            this.props.value().get("token")
        );
    }

    /**
     * Test method.
     * @throws Exception Properties exceptions.
     * @throws ClientException If VK client error occurs.
     * @throws ApiException If VK API error occurs.
     */
    abstract void test() throws Exception;

}
