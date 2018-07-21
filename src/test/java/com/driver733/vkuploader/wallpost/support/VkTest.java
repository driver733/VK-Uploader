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
package com.driver733.vkuploader.wallpost.support;

/**
 * VK API endpoints.
 *
 * @since 0.1
 * @checkstyle AvoidConstantsInterface (5 lines)
 * @checkstyle InterfaceIsTypeCheck (20 lines)
 */
public interface AbstractVkUnitTest {

    /**
     * Group ID.
     */
    int GROUP_ID = 161929264;

    /**
     * VK API endpoint - photos.saveWallPhoto.
     */
    String PHOTO_SAVE_URL =
        "https://api.vk.com/method/photos.saveWallPhoto";

    /**
     * VK API endpoint - audios.save.
     */
    String AUDIO_SAVE_URL =
        "https://api.vk.com/method/audio.save";
    /**
     * VK API endpoint - photos.getWallUploadServer.
     */
    String PHOTO_WALL_URL =
        "https://api.vk.com/method/photos.getWallUploadServer";

    /**
     * VK API endpoint - audios.getUploadServer.
     */
    String AUDIO_UPLOAD_URL =
        "https://api.vk.com/method/audio.getUploadServer";

    /**
     * VK API endpoint - audios.add.
     */
    String AUDIO_ADD_URL =
        "https://api.vk.com/method/audio.add";

    /**
     * VK API endpoint - execute.
     */
    String EXECUTE_URL =
        "https://api.vk.com/method/execute";

}
