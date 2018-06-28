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
package com.driver733.vkmusicuploader.post;

import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

/**
 * Acquires upload servers for various files.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class UploadUrls {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param group Group ID.
     */
    public UploadUrls(
        final VkApiClient client,
        final UserActor actor,
        final int group
    ) {
        this.client = client;
        this.actor = actor;
        this.group = group;
    }

    /**
     * Acquires an upload URL for audio files
     * (or used a cached one if it is available).
     * @return Upload URL for audio files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    @Cacheable(forever = true)
    public String audio() throws ClientException, ApiException {
        return this.client.audio()
            .getUploadServer(this.actor)
            .execute()
            .getUploadUrl();
    }

    /**
     * Acquires an upload URL for wall docs
     * (or uses a cached one if it is available).
     * @return Upload URL for wall files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    @Cacheable(forever = true)
    public String wallDoc() throws ClientException, ApiException {
        return this.client.docs()
            .getWallUploadServer(this.actor)
            .groupId(this.group)
            .execute()
            .getUploadUrl();
    }

    /**
     * Acquires an upload URL for wall files
     * (or used a cached one if it is available).
     * @return Upload URL for wall files.
     * @throws ClientException VK client error.
     * @throws ApiException VK API error.
     */
    @Cacheable(forever = true)
    public String wallPhoto() throws ClientException, ApiException {
        return this.client.photos()
            .getWallUploadServer(this.actor)
            .groupId(this.group)
            .execute()
            .getUploadUrl();
    }

}