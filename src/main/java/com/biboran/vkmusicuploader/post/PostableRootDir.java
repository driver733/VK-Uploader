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

package com.biboran.vkmusicuploader.post;

import com.jcabi.aspects.Async;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import java.io.File;
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
 */
public final class PostableRootDir implements Postable {

    /**
     * Root directory that contains directories with albums.
     */
    private final File directory;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServers servers;

    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param rootDir Root directory that contains directories with albums.
     * @param uploadServers Upload servers
     *  that provide upload URLs for attachmentsFields.
     */
    public PostableRootDir(
        final UserActor actor,
        final File rootDir,
        final UploadServers uploadServers
    ) {
        this.directory = rootDir;
        this.actor = actor;
        this.servers = uploadServers;
    }

    /**
     * Constructs album posts and asynchronously executes them.
     * @throws IOException If no subdirectories with albums are found.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void post() throws IOException {
        final File[] directories = this.directory.listFiles(File::isDirectory);
        if (directories == null) {
            throw new IOException("No subdirectories found");
        }
        for (final File subDirectory : directories) {
            final List<ExecuteBatchQuery> queries = new AlbumPosts(
                this.actor,
                subDirectory,
                this.servers
            ).posts();
            for (final ExecuteBatchQuery query : queries) {
                execute(query);
            }
        }
    }

    /**
     * Asynchronously executes the provided query.
     * @param query ExecuteBatchQuery.
     */
    @Async
    private static void execute(final ExecuteBatchQuery query) {
        try {
            query.execute();
        } catch (final ApiException | ClientException exception) {
            throw new IllegalStateException(exception);
        }
    }

}
