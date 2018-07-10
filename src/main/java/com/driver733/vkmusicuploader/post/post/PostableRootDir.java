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

package com.driver733.vkmusicuploader.post.post;

import com.driver733.vkmusicuploader.post.execution.UploadVerification;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.wallpost.wallposts.WallPosts;
import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.IOException;

/**
 * Constructs {@link com.driver733.vkmusicuploader.post.posts.Posts}
 * from the specified director.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class PostableRootDir implements Postable {

    /**
     * Root directory that contains directories with albums.
     */
    private final File directory;

    /**
     * WallPosts.
     */
    private final WallPosts wallposts;

    /**
     * Ctor.
     * @param dir Root directory that contains directories with albums.
     * @param wallposts WallPosts.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public PostableRootDir(
        final File dir,
        final WallPosts wallposts
    ) {
        this.directory = dir;
        this.wallposts = wallposts;
    }

    @Override
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.ExceptionAsFlowControl",
        "PMD.PreserveStackTrace"})
    public void post() throws Exception {
        final File[] dirs = this.directory.listFiles(File::isDirectory);
        if (dirs == null) {
            throw new IOException(
                "Invalid directory specified (No subdirectories found)."
            );
        }
        for (final File dir : dirs) {
            final ImmutableProperties props = new ImmutableProperties(
                new File(
                    String.format(
                        "%s/vkmu.properties",
                        dir.getAbsolutePath()
                    )
                )
            );
            try {
                props.load();
            } catch (final IOException ex) {
                try {
                    props.store();
                } catch (final IOException exx) {
                    throw new IOException("Failed to init props", exx);
                }
            }
            new UploadVerification(
                this.wallposts
            ).execute();
        }
    }

}
