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
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientHttp;
import org.cactoos.Scalar;
import org.cactoos.scalar.StickyScalar;

/**
 * Creates {@link VkApiClient} and {@link UserActor}
 *  for ITs.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 */
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public abstract class AbstractEntrance extends AbstractCredentials {

    /**
     * Exit delay before next IT.
     */
    private final int delay;

    /**
     * {@link VkApiClient}.
     */
    private final Scalar<VkApiClient> client;

    /**
     * VK {@link UserActor}.
     */
    private final Scalar<UserActor> actor;

    /**
     * Ctor.
     */
    AbstractEntrance() {
        super();
        this.client = new StickyScalar<>(
            () -> new VkApiClient(
                new TransportClientHttp()
            )
        );
        this.actor = new StickyScalar<>(
            () -> new UserActor(
                userId(),
                token()
            )
        );
        this.delay = 1;
    }

    /**
     * Actor for tests.
     * @return The {@link UserActor}.
     * @throws Exception If {@link UserActor} cannot be created.
     */
    protected final UserActor actor() throws Exception {
        return this.actor.value();
    }

    /**
     * Client for tests.
     * @return The {@link VkApiClient}.
     * @throws Exception If {@link UserActor} cannot be created.
     */
    protected final VkApiClient client() throws Exception {
        return this.client.value();
    }

    /**
     * Delay before next IT.
     * @throws InterruptedException If thread sleep fails.
     * @checkstyle MagicNumberCheck (5 lines)
     */
    protected final void exit() throws InterruptedException {
        Thread.sleep(this.delay * 1000);
    }

}
