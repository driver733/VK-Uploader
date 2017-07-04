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
package com.driver733.vkmusicuploader.post;

import com.driver733.vkmusicuploader.wallpost.wallpost.wallposts.WallPosts;
import com.google.gson.JsonElement;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.TransportClientCached;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class UploadVerificationTest {

    @Test
    public void valid() throws IOException {
         class WallPostsTests implements WallPosts {
             @Override
             public List<ExecuteBatchQuery> postsQueries() throws IOException {
                return new Array<>(
                    new ExecuteBatchQuery(
                        new VkApiClient(
                            new TransportClientCached(
                                "{ \"response\" : 123  }"
                            )
                        ),
                        new UserActor(1, ""),
                        new AudioAddQuery(
                            new VkApiClient(
                                new TransportClientCached(
                                    "{ \"response\" : 123  }"
                                )
                            ),
                            new UserActor(
                                1,
                                ""
                            ),
                            1,
                            1
                        )
                    )
                );
            }

            @Override
            public void updateProperties() throws IOException { }
        }
        WallPosts posts = new WallPostsTests();
        new UploadVerification(posts)
                .execute();
    }

    @Test(expected = IOException.class)
    public void postsQueriesException() throws IOException {
        class WallPostsTests implements WallPosts {
            @Override
            public List<ExecuteBatchQuery> postsQueries() throws IOException {
                throw new IOException("Test");
            }

            @Override
            public void updateProperties() throws IOException { }
        }
        new UploadVerification(
                new WallPostsTests()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void updatePropertiesException() throws IOException {
        class WallPostsTests implements WallPosts {
            @Override
            public List<ExecuteBatchQuery> postsQueries() throws IOException {
                return new Array<>(
                    new ExecuteBatchQuery(
                        new VkApiClient(
                            new TransportClientCached(
                                "{ \"response\" : 123  }"
                            )
                        ),
                        new UserActor(1, ""),
                        new AudioAddQuery(
                            new VkApiClient(
                                new TransportClientCached(
                                    "{ \"response\" : 123  }"
                                )
                            ),
                            new UserActor(
                                1,
                                ""
                            ),
                            1,
                            1
                        )
                    )
                );
            }

            @Override
            public void updateProperties() throws IOException {
                throw new IOException("Test");
            }
        }
        new UploadVerification(
            new WallPostsTests()
        ).execute();
    }



    @Test(expected = IOException.class)
    public void apiException() throws IOException {

        class ExecApiEx extends ExecuteBatchQuery {

            public ExecApiEx() {
                super(
                    new VkApiClient(
                        new TransportClientCached("")
                    ),
                    new UserActor(0, "")
                );
            }

            @Override
            public JsonElement execute() throws ApiException, ClientException {
                throw new ApiException(0, "Test");
            }
        }

        class WallPostsTests implements WallPosts {

            @Override
            public List<ExecuteBatchQuery> postsQueries() throws IOException {
                return new Array<>(
                    new ExecApiEx()
                );
            }

            @Override
            public void updateProperties() throws IOException { }

        }
        new UploadVerification(
            new WallPostsTests()
        ).execute();
    }

    @Test(expected = IOException.class)
    public void clientException() throws IOException {

        class ExecApiEx extends ExecuteBatchQuery {

            public ExecApiEx() {
                super(
                    new VkApiClient(
                        new TransportClientCached("")
                    ),
                    new UserActor(0, "")
                );
            }

            @Override
            public JsonElement execute() throws ApiException, ClientException {
                throw new ClientException("Test");
            }
        }

        class WallPostsTests implements WallPosts {

            @Override
            public List<ExecuteBatchQuery> postsQueries() throws IOException {
                return new Array<>(
                    new ExecApiEx()
                );
            }

            @Override
            public void updateProperties() throws IOException { }
        }
        new UploadVerification(
            new WallPostsTests()
        ).execute();
    }

}
