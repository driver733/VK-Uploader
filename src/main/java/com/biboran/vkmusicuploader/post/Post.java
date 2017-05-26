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

import com.biboran.vkmusicuploader.audio.AudioInfo;
import com.biboran.vkmusicuploader.wallpost.WallPost;
import com.biboran.vkmusicuploader.wallpost.WallPostBase;
import com.biboran.vkmusicuploader.wallpost.WallPostFromGroup;
import com.biboran.vkmusicuploader.wallpost.WallPostWithAttachments;
import com.biboran.vkmusicuploader.wallpost.WallPostWithMessage;
import com.biboran.vkmusicuploader.wallpost.WallPostWithOwnerId;
import com.biboran.vkmusicuploader.wallpost.attachment.AttachmentArrays;
import com.biboran.vkmusicuploader.wallpost.attachment.AttachmentAudio;
import com.biboran.vkmusicuploader.wallpost.attachment.AttachmentWallPhoto;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
public final class Post implements WallPost {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    /**
     * Audio files.
     */
    private final File[] audios;

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
     * @param audios Audio files.
     * @param servers Upload servers
     *  that provide upload URLs for attachmentsFields.
     */
    public Post(
        final UserActor actor,
        final File[] audios,
        final UploadServers servers
    ) {
        this.audios = Arrays.copyOf(audios, audios.length);
        this.actor = actor;
        this.servers = servers;
    }

    /**
     * Constructs a WallPostQuery for a wall Post.
     * @return WallPostQuery.
     */
    public WallPostQuery construct() {
        final byte[] image = albumImage(this.audios[0]);
        final String message = new AudioInfo(this.audios[0]).toString();
        try {
            return new WallPostWithOwnerId(
                new WallPostFromGroup(
                    new WallPostWithMessage(
                        new WallPostWithAttachments(
                            new WallPostBase(this.actor),
                            new AttachmentArrays(
                                this.actor,
                                new AttachmentWallPhoto(
                                    this.actor,
                                    image,
                                    this.servers.uploadUrl(
                                        UploadServers.UploadServerType
                                            .WALL_PHOTO
                                    )
                                ),
                                new AttachmentAudio(
                                    this.actor,
                                    this.servers.uploadUrl(
                                        UploadServers.UploadServerType
                                            .AUDIO
                                    ),
                                    this.audios
                                )
                            )
                        ),
                        message
                    )
                ),
                -Post.GROUP_ID
            )
            .construct();
        } catch (final IOException exception) {
            throw new IllegalStateException(exception);
        }
    }

    /**
     * Acquires album image from the provided mp3 file.
     * @param audio Audio file.
     * @return Album image.
     */
    private static byte[] albumImage(final File audio) {
        try {
            return new Mp3File(audio).getId3v2Tag().getAlbumImage();
        } catch (
        final IOException
            | UnsupportedTagException
            | InvalidDataException
            exception
        ) {
            throw new IllegalStateException(exception);
        }
    }

}
