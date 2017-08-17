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
package com.driver733.vkmusicuploader.wallpost.attachment;

import com.vk.api.sdk.client.ClientResponse;
import com.vk.api.sdk.client.TransportClient;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
public final class TransportClientComplex implements TransportClient {

    /**
     * {@link TransportClient} for each API endpoint.
     */
    private final Map<String, TransportClient> clients;

    /**
     * Ctor.
     * @param clients A {@link TransportClient} for each API endpoint.
     */
    public TransportClientComplex(final Map<String, TransportClient> clients) {
        this.clients = clients;
    }

    @Override
    public ClientResponse post(
        final String url,
        final String body
    ) throws IOException {
        return this.clients.get(url)
            .post(url, body);
    }

    // @checkstyle ParameterNameCheck (5 lines)
    @Override
    public ClientResponse post(
        final String url, final String fileName,
        final File file
    ) throws IOException {
        return this.clients.get(url)
            .post(url, fileName, file);
    }

    @Override
    public ClientResponse post(
        final String url
    ) throws IOException {
        return this.clients.get(url)
            .post(url);
    }

    @Override
    public boolean isCached() {
        return true;
    }
}
