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

package com.driver733.vkuploader.post;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

/**
 * Acquires upload servers for various files.
 *
 * @since 0.4
 */
public interface UploadServers {

    /**
     * Acquires an upload URL for audios files
     * (or used a cached one if it is available).
     * @return Upload URL for audios files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    String audios() throws ClientException, ApiException;

    /**
     * Acquires an upload URL for wall docs
     * (or uses a cached one if it is available).
     * @return Upload URL for wall files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    String docs() throws ClientException, ApiException;

    /**
     * Acquires an upload URL for wall files
     * (or used a cached one if it is available).
     * @return Upload URL for wall files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    String wallPhoto() throws ClientException, ApiException;

}
