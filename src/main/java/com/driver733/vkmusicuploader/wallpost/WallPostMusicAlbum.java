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
package com.driver733.vkmusicuploader.wallpost;

import com.driver733.vkmusicuploader.post.UploadServers;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentCachedAudio;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallPhoto;
import com.driver733.vkmusicuploader.wallpost.attachment.message.ID3v1AnnotatedSafe;
import com.driver733.vkmusicuploader.wallpost.attachment.message.MessageBasic;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagFromMp3File;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.advancedtag.AdvancedTagVerifiedAlbumImage;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.basictag.BasicTagFromMp3File;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayFromFile;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.ByteArrayImageFromAdvancedTag;
import com.driver733.vkmusicuploader.wallpost.attachment.mp3filefromfile.bytearray.fallback.FallbackBytes;
import com.driver733.vkmusicuploader.wallpost.attachment.support.AudioStatus;
import com.driver733.vkmusicuploader.wallpost.attachment.support.fields.AttachmentArraysWithProps;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallPhoto;
import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.queries.wall.WallPostQuery;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Creates a {@link WallPost} with the specified
 *  audio files and an album cover (from an audio file tag or
 *  cover.jpg).
 *
 *
 * @since 0.1
 *
 * @checkstyle ClassDataAbstractionCouplingCheck (2 lines)
 */
@Immutable
public final class WallPostMusicAlbum implements WallPost {

    /**
     * Group ID.
     */
    private final int group;

    /**
     * {@link VkApiClient} for all requests.
     */
    private final VkApiClient client;

    /**
     * Audio files.
     */
    private final List<Path> audios;

    /**
     * UserActor on behalf of which all requests will be sent.
     */
    private final UserActor actor;

    /**
     * Upload servers that provide upload URLs for attachmentsFields.
     */
    private final UploadServers servers;

    /**
     * Properties that contain the {@link AudioStatus} of audios files.
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
     *  {@link AudioStatus} of audios files.
     * @param group Group ID.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public WallPostMusicAlbum(
        final VkApiClient client,
        final UserActor actor,
        final List<Path> audios,
        final UploadServers servers,
        final ImmutableProperties properties,
        final int group
    ) {
        this.client = client;
        this.audios = audios;
        this.actor = actor;
        this.servers = servers;
        this.properties = properties;
        this.group = group;
    }

    /**
     * Constructs a WallPostQuery for a wall WallPostMusicAlbum.
     * @return WallPostQuery.
     * @throws Exception If an exception occurs
     *  while constructing the {@link WallPost}.
     */
    public WallPostQuery construct() throws Exception {
        final Mp3File file;
        try {
            file = new Mp3File(
                this.audios.get(0)
            );
        } catch (final UnsupportedTagException | InvalidDataException ex) {
            throw new IOException(
                String.format(
                    "Failed to get Mp3File from File %s",
                    this.audios.get(0)
                        .toAbsolutePath()
                ),
                ex
            );
        }
        return new WallPostWithOwnerId(
            new WallPostFromGroup(
                new WallPostWithMessage(
                    new WallPostWithAttachments(
                        new WallPostBase(
                            this.client,
                            this.actor
                        ),
                        new AttachmentArraysWithProps(
                            this.actor,
                            this.properties,
                            this.group,
                            new AttachmentWallPhoto(
                                this.client,
                                this.actor,
                                this.group,
                                new UploadWallPhoto(
                                    this.client,
                                    this.servers
                                        .wallPhoto(),
                                    new FallbackBytes(
                                        new ByteArrayImageFromAdvancedTag(
                                            new AdvancedTagVerifiedAlbumImage(
                                                new AdvancedTagFromMp3File(file)
                                            )
                                        ),
                                        new ByteArrayFromFile(
                                            Paths.get(
                                                String.format(
                                                    "%s/cover.jpg",
                                                    this.audios.get(0)
                                                        .getParent()
                                                )
                                            )
                                        )
                                    ).asBytes()
                                )
                            ),
                            new AttachmentCachedAudio(
                                this.client,
                                this.actor,
                                this.servers.audios(),
                                this.properties,
                                this.audios,
                                this.group
                            )
                        )
                    ),
                    new MessageBasic(
                        new ID3v1AnnotatedSafe(
                            new BasicTagFromMp3File(file)
                                .construct()
                        ).getAlbum(),
                        new ID3v1AnnotatedSafe(
                            new BasicTagFromMp3File(file)
                                .construct()
                        ).getArtist()
                    )
                )
            ),
            -this.group
        ).construct();
    }

}
