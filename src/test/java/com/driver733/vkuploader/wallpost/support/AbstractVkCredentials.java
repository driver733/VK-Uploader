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
package com.driver733.vkuploader.wallpost.support;

import com.jcabi.aspects.Immutable;

/**
 * VK credentials for IT.
 *
 * @checkstyle NonStaticMethodCheck (500 lines)
 * @since 0.2
 */
@Immutable
@SuppressWarnings({
    "PMD.AbstractClassWithoutAbstractMethod",
    "PMD.AbstractClassWithoutAnyMethod"
    })
public abstract class AbstractVkCredentials {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.UncommentedEmptyConstructor")
    protected AbstractVkCredentials() { }

    /**
     * VK group ID.
     * @return Group ID.
     * @throws Exception If properties cannot be read.
     */
    protected final int groupId() {
        return Integer.parseInt(
            System.getProperty("vk.groupId")
        );
    }

    /**
     * VK user ID.
     * @return User ID.
     * @throws Exception If properties cannot be read.
     */
    protected final int userId() {
        return Integer.parseInt(
            System.getProperty("vk.userId")
        );
    }

    /**
     * VK token.
     * @return Token.
     * @throws Exception If properties cannot be read.
     */
    protected final String token() {
        return String.valueOf(
            System.getProperty("vk.token")
        );
    }

}
