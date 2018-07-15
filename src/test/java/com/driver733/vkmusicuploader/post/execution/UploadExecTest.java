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
package com.driver733.vkmusicuploader.post.execution;

import com.driver733.vkmusicuploader.wallpost.wallposts.WallPosts;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import java.io.IOException;
import java.util.List;
import org.junit.Test;

/**
 * Test for {@link UploadExecVerification}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
@Immutable
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UncommentedEmptyMethodBody"})
public final class UploadExecTest {

    @Test
    public void valid() throws Exception {
        new UploadExecVerification(
            new WallPostsFake()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void postsQueriesException() throws Exception {
        new UploadExecVerification(
            new WallPostsQueriesEx()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void updatePropertiesException() throws Exception {
        new UploadExecVerification(
            new WallPostsPropsEx()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void apiException() throws Exception {
        new UploadExecVerification(
            new WallPostsFakeApiEx()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void clientException() throws Exception {
        new UploadExecVerification(
            new WallPostsFakeClientEx()
        ).execute();
    }

    /**
     * A fake {@link WallPosts} that fails to return the posts` queries.
     */
    final class WallPostsQueriesEx implements WallPosts {

        @Override
        public List<ExecuteBatchQuery> postsQueries() throws IOException {
            throw new IOException("Test1");
        }

        @Override
        public void updateProperties() { }

    }

    /**
     * An {@link ExecuteBatchQuery} that throws an {@link ApiException}.
     */
    final class ExecuteBatchQueryFakeApiEx extends ExecuteBatchQuery {

        ExecuteBatchQueryFakeApiEx() {
            super(
                new VkApiClient(
                    new TransportClientCached("")
                ),
                new UserActor(0, "")
            );
        }

        @Override
        public JsonElement execute() throws ApiException {
            throw new ApiException(0, "Test2");
        }

    }

    /**
     * An {@link ExecuteBatchQuery} that throws a {@link ClientException}.
     */
    final class ExecuteBatchQueryFakeClientEx extends ExecuteBatchQuery {

        ExecuteBatchQueryFakeClientEx() {
            super(
                new VkApiClient(
                    new TransportClientCached("")
                ),
                new UserActor(0, "")
            );
        }

        @Override
        public JsonElement execute() throws ClientException {
            throw new ClientException("Test3");
        }

    }

    /**
     * Fake {@link ExecuteBatchQuery}.
     */
    final class ExecuteBatchQueryFake extends ExecuteBatchQuery {

        ExecuteBatchQueryFake() {
            super(
                new VkApiClient(
                    new TransportClientCached("{ response : 123 }")
                ),
                new UserActor(0, "")
            );
        }

    }

    /**
     * A {@link WallPosts} that faces an {@link ApiException}.
     */
    final class WallPostsFakeApiEx implements WallPosts {

        @Override
        public List<ExecuteBatchQuery> postsQueries() {
            return new Array<>(
                new ExecuteBatchQueryFakeApiEx()
            );
        }

        @Override
        public void updateProperties() { }

    }

    /**
     * A {@link WallPosts} that faces a {@link ClientException}.
     */
    final class WallPostsFakeClientEx implements WallPosts {

        @Override
        public List<ExecuteBatchQuery> postsQueries() {
            return new Array<>(
                new ExecuteBatchQueryFakeClientEx()
            );
        }

        @Override
        public void updateProperties() { }

    }

    /**
     * A fake {@link WallPosts}.
     */
    final class WallPostsFake implements WallPosts {

        @Override
        public List<ExecuteBatchQuery> postsQueries() {
            return new Array<>(
                new ExecuteBatchQueryFake()
            );
        }

        @Override
        public void updateProperties() { }
    }

    /**
     * A {@link WallPosts} that fails to update the properties..
     */
    final class WallPostsPropsEx implements WallPosts {

        @Override
        public List<ExecuteBatchQuery> postsQueries() {
            return new Array<>(
                new ExecuteBatchQueryFake()
            );
        }

        @Override
        public void updateProperties() throws IOException {
            throw new IOException("Test4");
        }

    }

}