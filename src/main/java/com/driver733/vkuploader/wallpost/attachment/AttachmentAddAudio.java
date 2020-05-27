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
package com.driver733.vkuploader.wallpost.attachment;

import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.audio.AudioAddQuery;
import java.util.Collections;
import java.util.List;

// @checkstyle MemberNameCheck (50 lines)

/**
 * An {@link Attachment} with audios added
 *  to a page. (with the specified user ID).
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class AttachmentAddAudio implements Attachment {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * VKAPIClient that is used for all VK API requests.
     */
    private final VkApiClient client;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Audio`s owner ID.
     */
    private final int ownerId;

    /**
     * Audio`s media ID.
     */
    private final int mediaId;

    // @checkstyle ParameterNameCheck (20 lines)

    /**
     * Ctor.
     * @param client VKAPIClient that is used for all VK API requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param ownerId Audio`s owner ID.
     * @param mediaId Audio`s media ID.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public AttachmentAddAudio(
        final VkApiClient client,
        final UserActor actor,
        final int ownerId,
        final int mediaId,
        final int group
    ) {
        this.actor = actor;
        this.ownerId = ownerId;
        this.mediaId = mediaId;
        this.client = client;
        this.group = group;
    }

    @Override
    public List<AbstractQueryBuilder<AudioAddQuery, Integer>> upload() {
        return Collections.singletonList(
            this.client.audio()
                .add(
                    this.actor,
                    this.mediaId,
                    this.ownerId
                )
                .groupId(this.group)
        );
    }

}
