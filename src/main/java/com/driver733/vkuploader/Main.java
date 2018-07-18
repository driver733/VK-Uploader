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
package com.driver733.vkuploader;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.TransportClientHttp;

/**
 * Main class.
 *
 *
 * @since 0.1
*/
final class Main {

    /**
     * Prevent Instantiation.
     *
     * @since 0.1
     */
    private Main() { }

    /**
     * Entry point.
     * @param args Command line arguments.
     */
    public static void main(final String... args) throws Exception {

    // Если Вы превысите частотное ограничение, сервер вернет ошибку с кодом 6: "Too many requests per second.".

    // restart on delete (option)



//        Mp3File file;
//        try {
//            file = new Mp3File("/Users/mikhailyakushin/Downloads/Music/Ese honmono : Fake Real/01. xi.mp3");
//        } catch (final UnsupportedTagException | InvalidDataException e) {
//            throw new IllegalStateException(e);
//        }





        final UserActor actor = new UserActor(
            438005213,
            "54e613bb0ad0def0b5af29d2417d082dbd356930c36cca4b2201ee8e2e9d5c2243ab1cad1f2eb31229a94"
        );

        final int group = 161929264;

        final VkApiClient vkApiClient = new VkApiClient(
            new TransportClientHttp()
        );

//        new EntranceDirectory(
//            new PostsBasic(
//                new WallPostsPhotoAlbum()
//
//                vkApiClient,
//                actor,
//                new UploadServers(
//                    vkApiClient,
//                    actor,
//                    group
//                ),
//                group
//            ),
//            new File("/Users/mikhailyakushin/Downloads/photos")
//         ).start();





//       String url = client.audios()
//            .getUploadServer(actor)
//            .execute()
//            .getUploadUrl();
//
//       AudioUploadResponse response = client.upload().audios(url, new File("/Users/mikhailyakushin/Downloads/Music/testRoot/1.mp3")).execute();
//
//
//      Audio audios = client.audios().save(actor, response.getServer(),response.getAudio(),response.getHash()).execute();
//
//       AudioAddQuery query = client.audios()
//            .add(
//                actor,
//                audios.getId(),
//                audios.getOwnerId()
//            ).groupId(161929264);
//
//        ExecuteBatchQuery batchQuery = client.execute().batch(actor,query);
//
//
//       JsonElement jsonElement =  batchQuery.execute();





//    String url = new UploadServers(client, actor).uploadUrl(UploadServers.Type.WALL_DOC);



//
//    DocUploadResponse docUploadResponse = client.upload().doc(url,
//        new File("/Users/mikhailyakushin/Downloads/Music/testRoot/3.zip")).execute();
//
//
//    List<Doc> list = client.docs().save(actor, docUploadResponse.getFile()).execute();
//
//
//    client.wall().post(actor).attachments("doc"+list.get(0).getOwnerId().toString()+"_"+list.get(0).getId()).ownerId(-161929264).execute();
//
//






    }




}
