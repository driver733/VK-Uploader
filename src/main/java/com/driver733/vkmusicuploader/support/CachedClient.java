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
package com.driver733.vkmusicuploader.support;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
final class CachedClient implements TransportClient {

    /**
     * Cached request`s response.
     */
    private final JsonElement result;

    /**
     * Request with cached response.
     * @param result Cached request`s response.
     * @checkstyle ParameterNameCheck (50 lines)
     */
    CachedClient(final JsonElement result) {
        this.result = result;
    }

    @Override
    public ClientResponse post(
        final String url,
        final String body
    ) throws IOException {
        return new ClientResponse(
            HttpStatus.SC_OK,
            this.result.toString(),
            headers()
        );
    }

    @Override
    public ClientResponse post(
        final String url, final String fileName,
        final File file
    ) throws IOException {
        return new ClientResponse(
            HttpStatus.SC_OK,
            this.result.toString(),
            headers()
        );
    }

    @Override
    public ClientResponse post(
        final String url
    ) throws IOException {
        return new ClientResponse(
            HttpStatus.SC_OK,
            this.result.toString(),
            headers()
        );
    }

    /**
     * Basic HTTP headers that satisfy {@link com.vk.api.sdk.client.ApiRequest}.
     * @return A {@link Map} with HTTP headers.
     */
    private static Map<String, String> headers() {
        final Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

}
