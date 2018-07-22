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
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Creates {@link VkApiClient} and {@link UserActor}
 *  for ITs.
 *
 *
 *
 * @since 0.2
 */
@Immutable
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public final class VkCredentials implements TestRule {

    /**
     * VK group ID props key.
     */
    private static final String GROUP_PROPS_KEY = "vk.groupId";

    /**
     * VK user ID props key.
     */
    private static final String USER_PROPS_KEY = "vk.userId";

    /**
     * VK auth token props key.
     */
    private static final String TOKEN_PROPS_KEY = "vk.token";

    /**
     * VK {@link VkApiClient}.
     */
    private final Scalar<VkApiClient> client;

    /**
     * VK {@link UserActor}.
     */
    private final Scalar<UserActor> actor;

    /**
     * VK Group ID.
     */
    private final int group;

    /**
     * Ctor.
     */
    public VkCredentials() {
        super();
        this.group = Integer.parseInt(
            System.getProperty(
                VkCredentials.GROUP_PROPS_KEY
            )
        );
        this.client = new StickyScalar<>(
            () -> new VkApiClient(
                new TransportClientHttp()
            )
        );
        this.actor = new StickyScalar<>(
            () -> new UserActor(
                Integer.parseInt(
                    System.getProperty(
                        VkCredentials.USER_PROPS_KEY
                    )
                ),
                System.getProperty(
                    VkCredentials.TOKEN_PROPS_KEY
                )
            )
        );
    }

    /**
     * Actor for tests.
     * @return The {@link UserActor}.
     * @throws Exception If {@link UserActor} cannot be created.
     */
    public UserActor actor() throws Exception {
        return this.actor.value();
    }

    /**
     * Client for tests.
     * @return The {@link VkApiClient}.
     * @throws Exception If {@link UserActor} cannot be created.
     */
    public VkApiClient client() throws Exception {
        return this.client.value();
    }

    /**
     * VK group ID.
     * @return VK group ID.
     */
    public int group() {
        return this.group;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        throw new UnsupportedOperationException("#apply()");
    }
}
