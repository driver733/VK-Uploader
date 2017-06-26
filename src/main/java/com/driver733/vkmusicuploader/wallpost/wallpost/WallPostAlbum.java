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
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentCachedAudio;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallPhoto;
import com.driver733.vkmusicuploader.wallpost.attachment.UploadWallPhoto;
import com.driver733.vkmusicuploader.wallpost.attachment.message.MessageBasic;
import com.driver733.vkmusicuploader.wallpost.attachment.message.messagepart.ID3v1AnnotatedSafe;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagFromMp3File;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagVerifiedAlbumImage;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.basictag.BasicTagFromMp3File;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromAdvancedTag;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromFile;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback.FallbackByteArrayImage;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AttachmentArrays;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
@Immutable
public final class WallPostAlbum implements WallPost {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    /**
     * {@link VkApiClient} for all requests.
     */
    private final VkApiClient client;

    /**
     * Audio files.
     */
    private final Array<File> audios;

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
     * @param client The {@link VkApiClient} for all requests.
     * @param actor UserActor on behalf of which all requests will be sent.
     * @param audios Audio files.
     * @param servers Upload servers
     *  that provide upload URLs for attachmentsFields.
     * @param properties Properties that contain the
     *  {@link AudioStatus} of audio files.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostAlbum(
        final VkApiClient client,
        final UserActor actor,
        final List<File> audios,
        final UploadServers servers,
        final ImmutableProperties properties
    ) {
        this.client = client;
        this.audios = new Array<>(audios);
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
        final Mp3File file;
        try {
            file = new Mp3File(this.audios.get(0));
        } catch (final UnsupportedTagException | InvalidDataException ex) {
            throw new IOException(
                String.format(
                    "Failed to get Mp3File from File %s",
                    this.audios.get(0).getAbsolutePath()
                ),
                ex
            );
        }
        return new WallPostWithOwnerId(
            new WallPostFromGroup(
                new WallPostWithMessage(
                    new WallPostWithAttachments(
                        new WallPostBase(this.actor),
                        new AttachmentArrays(
                            this.actor,
                            this.properties,
                            new AttachmentWallPhoto(
                                this.client,
                                this.actor,
                                new UploadWallPhoto(
                                    this.client,
                                    this.servers.uploadUrl(
                                        UploadServers.Type.WALL_PHOTO
                                    ),
                                    new FallbackByteArrayImage(
                                        new ByteArrayImageFromAdvancedTag(
                                            new AdvancedTagVerifiedAlbumImage(
                                                new AdvancedTagFromMp3File(file)
                                            )
                                        ),
                                        new ByteArrayImageFromFile(
                                            new File(
                                                String.format(
                                                    "%s/cover.jpg",
                                                    this.audios.get(0)
                                                        .getParentFile()
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            new AttachmentCachedAudio(
                                this.client,
                                this.actor,
                                this.servers.uploadUrl(
                                    UploadServers.Type.AUDIO
                                ),
                                this.properties,
                                this.audios
                            )
                        )
                    ),
                    new MessageBasic(
                        new ID3v1AnnotatedSafe(
                            new BasicTagFromMp3File(file).construct()
                        ).getAlbum(),
                        new ID3v1AnnotatedSafe(
                            new BasicTagFromMp3File(file).construct()
                        ).getArtist()
                    ).construct()
                )
            ),
            -WallPostAlbum.GROUP_ID
        ).construct();
    }

}
