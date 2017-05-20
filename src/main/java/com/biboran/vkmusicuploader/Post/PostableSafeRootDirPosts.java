///**
// * The MIT License (MIT)
// *
// * Copyright (c) 2017 Mikhail Yakushin
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included
// * in all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// * SOFTWARE.
// */
//
//package com.biboran.vkmusicuploader.Post;
//
///**
// * Class or Interface description.
// * <p>
// * Additional info
// *
// * @author Mikhail Yakushin (driver733@me.com)
// * @version $Id$
// * @since 0.1
// */
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import com.jcabi.aspects.Async;
//import com.vk.api.sdk.client.actors.UserActor;
//import com.vk.api.sdk.exceptions.ApiException;
//import com.vk.api.sdk.exceptions.ClientException;
//import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
//
//public class RootDirPosts implements Postable {
//
//    private final File rootDir;
//
//    private final UserActor actor;
//
//    private final UploadServers servers;
//
//    public RootDirPosts(final UserActor actor, final File rootDir, final UploadServers uploadServers) {
//        this.rootDir = rootDir;
//        this.actor = actor;
//        this.servers = uploadServers;
//    }
//
//    public void post() throws IOException {
//        final File[] directories = rootDir.listFiles(File::isDirectory);
//
//        }
//    }
//
//
//}
