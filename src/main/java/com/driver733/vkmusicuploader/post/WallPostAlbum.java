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

import com.driver733.vkmusicuploader.support.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.WallPost;
import com.driver733.vkmusicuploader.wallpost.WallPostBase;
import com.driver733.vkmusicuploader.wallpost.WallPostFromGroup;
import com.driver733.vkmusicuploader.wallpost.WallPostWithAttachments;
import com.driver733.vkmusicuploader.wallpost.WallPostWithMessage;
import com.driver733.vkmusicuploader.wallpost.WallPostWithOwnerId;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallPhoto;
import com.driver733.vkmusicuploader.wallpost.attachment.CachedAttachmentAudio;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AttachmentArrays;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
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
public final class WallPostAlbum implements WallPost {

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
     * Properties that contain the {@link AudioStatus} of audio files.
     */
    private final ImmutableProperties properties;
    /**
     * Ctor.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param audios Audio files.
     * @param servers Upload servers
     *  that provide upload URLs for attachmentsFields.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audio files.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostAlbum(
        final UserActor actor,
        final File[] audios,
        final UploadServers servers,
        final ImmutableProperties properties
    ) {
        this.audios = Arrays.copyOf(audios, audios.length);
        this.actor = actor;
        this.servers = servers;
        this.properties = properties;
    }

    /**
     * Constructs a WallPostQuery for a wall WallPostAlbum.
     * @return WallPostQuery.
     * @throws IOException If an exception occurs
     *  while constructing the {@link WallPost}.
     */
    public WallPostQuery construct() throws IOException {
        final byte[] image = albumImage(this.audios[0]);
        final String message = new AudioInfo(this.audios[0]).toString();
        return new WallPostWithOwnerId(
            new WallPostFromGroup(
                new WallPostWithMessage(
                    new WallPostWithAttachments(
                        new WallPostBase(this.actor),
                        new AttachmentArrays(
                            this.actor,
                            this.properties,
                            new AttachmentWallPhoto(
                                this.actor,
                                image,
                                this.servers.uploadUrl(
                                    UploadServers.Type.WALL_PHOTO
                                )
                            ),
                            new CachedAttachmentAudio(
                                this.actor,
                                this.servers.uploadUrl(
                                    UploadServers.Type.AUDIO
                                ),
                                this.properties,
                                this.audios
                            )
                        )
                    ),
                    message
                )
            ),
            -WallPostAlbum.GROUP_ID
        ).construct();
    }

    /**
     * Acquires album image from the provided mp3 file.
     * @param audio Audio file.
     * @return Album image.
     * @throws IOException If the album cover image cannot be extracted.
     */
    private static byte[] albumImage(final File audio) throws IOException {
        try {
            return new Mp3File(audio).getId3v2Tag().getAlbumImage();
        } catch (
        final IOException
            | UnsupportedTagException
            | InvalidDataException ex
        ) {
            throw new IOException(
                "Failed to extract album cover image file from audio file",
                ex
            );
        }
    }

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
    private static final class AudioInfo {

        /**
         * Audio file to get info from.
         */
        private final File audio;
        /**
         * Ctor.
         * @param audio Audio File.
         */
        AudioInfo(final File audio) {
            this.audio = audio;
        }

        @Override
        public String toString() {
            try {
                final Mp3File info = new Mp3File(this.audio);
                return String.format(
                    "Artist: %s%nAlbum: %s",
                    info.getId3v2Tag().getArtist(),
                    info.getId3v2Tag().getGenre()
                );
            } catch (
            final IOException
                | InvalidDataException
                | UnsupportedTagException ex
            ) {
                throw new IllegalStateException(ex);
            }
        }

    }

}
