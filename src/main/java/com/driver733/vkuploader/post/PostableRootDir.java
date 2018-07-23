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

package com.driver733.vkuploader.post;

import com.driver733.vkuploader.post.execution.UploadExecVerification;
import com.driver733.vkuploader.wallpost.ImmutableProps;
import com.driver733.vkuploader.wallpost.wallposts.WallPosts;
import com.jcabi.aspects.Immutable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Constructs {@link com.driver733.vkuploader.post.posts.Posts}
 * from the specified director.
 *
 * @since 0.1
 */
@Immutable
@SuppressWarnings("PMD.NonStaticInitializer")
public final class PostableRootDir implements Postable {

    /**
     * Root directory that contains directories with albums.
     */
    private final Path directory;

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
        final Path dir,
        final WallPosts wallposts
    ) {
        this.directory = dir;
        this.wallposts = wallposts;
    }

    @Override
    // @checkstyle RequireThisCheck (50 lines)
    @SuppressWarnings({"PMD.AvoidInstantiatingObjectsInLoops",
        "PMD.ExceptionAsFlowControl",
        "PMD.PreserveStackTrace"})
    public void post() throws Exception {
        Stream.concat(
            Files.list(
                this.directory
            ),
            new ArrayList<Path>(1) {
                {
                    add(directory);
                }
            }.stream()
        ).filter(
            file -> Files.isDirectory(file)
        ).forEach(
            path -> {
                final File file = new File(
                    String.format(
                        "%s/vkmu.properties",
                        path.toAbsolutePath()
                    )
                );
                final ImmutableProps props = new ImmutableProps(
                    file
                );
                props.containsKey("");
            }
            );
        new UploadExecVerification(
            this.wallposts
        ).execute();
    }

}
