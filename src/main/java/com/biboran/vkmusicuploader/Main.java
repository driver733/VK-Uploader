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
package com.biboran.vkmusicuploader;

import com.biboran.vkmusicuploader.Auth.Auth;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.biboran.vkmusicuploader.Post.AlbumPosts;
import com.biboran.vkmusicuploader.Post.Post;
import com.biboran.vkmusicuploader.Post.UploadServers;

import com.google.gson.JsonElement;
import com.jcabi.aspects.Loggable;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.audio.responses.AudioUploadResponse;
import com.vk.api.sdk.queries.execute.ExecuteBatchQuery;
import com.vk.api.sdk.queries.wall.WallPostQuery;


/**
 * Main class.
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
*/
final class Main {

    /**
     * Prevent Instantiation.
     * @author Mikhail Yakushin (driver733@me.com)
     * @version $Id$
     * @since 0.1
     */
    private Main() { }

    /**
     * Entry point.
     * @param args Command line arguments.
     */
    public static void main(final String... args) throws ClientException, ApiException, IOException {
        final int port = 8082;
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        Mp3File mp3file = null;
        try {
            mp3file = new Mp3File("/Users/AlexanderAbdulov/IdeaProjects/VKMusicUploader/test1.mp3");
            final Path path = Files.write(File.createTempFile("albumCover", ".jpg").toPath(), mp3file.getId3v2Tag().getAlbumImage());
//            System.out.println(mp3file.getId3v1Tag().getArtist());
          path.toFile().deleteOnExit();

        } catch (UnsupportedTagException | InvalidDataException | IOException e) {
            throw new IllegalStateException(e);
        }


//        final ServerSocket serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(10000);
//
//        final AuthCode authCode = new AuthCallback(
//            new Server(serverSocket), new AuthRequest()
//        ).authCode();
//


        final VkApiClient client = new VkApiClient(new HttpTransportClient());
        final UserActor actor = new Auth(client).userActor();


        final UploadServers uploadServers = new UploadServers(client, actor);


//        final AlbumPosts posts = new AlbumPosts(actor,  "/Volumes/Data/Downloads/Music/Miami Nights 1984 - Turbulence", uploadServers);
//        for (final ExecuteBatchQuery query : posts.posts()) {
//            query.execute();
//        }



//        AudioUploadResponse response = client.upload().audio(
//            client.audio().getUploadServer(actor).execute().getUploadUrl(),
//            new File("/Users/AlexanderAbdulov/IdeaProjects/VKMusicUploader/test1.mp3")
//        ).execute();
//
//        Audio response1 = client.audio().save(actor, response.getServer(), response.getAudio(), response.getHash()).execute();
//
//        Integer response2 = client.audio().add(actor, response1.getId(), response1.getOwnerId()).execute();


//        Если Вы превысите частотное ограничение, сервер вернет ошибку с кодом 6: "Too many requests per second.".


//         /Users/AlexanderAbdulov/IdeaProjects/VKMusicUploader
//        /Volumes/Data/Downloads/Music/[mikudb] Vocaloid Discography -All of MikuDB- V2 (1440 Albums) [MP3]/8HIT

        // RootDirPoster(String rootDirPath)



    }



}


