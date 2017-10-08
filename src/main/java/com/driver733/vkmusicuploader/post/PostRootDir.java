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

import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.wallpost.wallposts.WallPostsAlbum;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import java.io.File;
import java.io.IOException;

/**
 * Class or Interface description Test.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class PostRootDir implements Post {

    /**
     * Root directory that contains directories with albums.
     */
    private final File directory;

    /**
     * {@link VkApiClient} for all requests.
     */
    private final VkApiClient client;

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
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param dir Root directory that contains directories with albums.
     * @param servers Upload servers
     *  that provide upload URLs for attachmentsFields.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public PostRootDir(
        final VkApiClient client,
        final UserActor actor,
        final File dir,
        final UploadServers servers
    ) {
        this.client = client;
        this.directory = dir;
        this.actor = actor;
        this.servers = servers;
    }

    @Override
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public void post() throws IOException {
        final File[] dirs = this.directory.listFiles(File::isDirectory);
        if (dirs == null) {
            throw new IOException(
                "Invalid directory specified (No subdirectories found)."
            );
        }
        for (final File dir : dirs) {
            new UploadVerification(
                new WallPostsAlbum(
                    this.client,
                    this.actor,
                    dir,
                    this.servers,
                    new ImmutableProperties(
                        new File(
                            String.format(
                                "%s/vkmu.properties",
                                dir.getAbsolutePath()
                            )
                        )
                    )
                )
            ).execute();
        }
    }

}
