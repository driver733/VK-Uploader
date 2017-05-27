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

package com.biboran.vkmusicuploader.wallpost.attachment;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
public final class AttachmentFormat {

    /**
     * Group ID.
     */
    private static final int GROUP_ID = 92444715;

    /**
     * Attachment Type Enum.
     */
    public enum AttachmentType {

        /**
         * PHOTO.
         */
        PHOTO("photo"),

        /**
         * VIDEO.
         */
        VIDEO("video"),

        /**
         * AUDIO.
         */
        AUDIO("audio"),

        /**
         * DOCUMENT.
         */
        DOC("doc"),

        /**
         * WIKI-PAGE.
         */
        PAGE("page"),

        /**
         * NOTE.
         */
        NOTE("note"),

        /**
         * POLL.
         */
        POLL("poll"),

        /**
         * PHOTO ALBUM.
         */
        ALBUM("album");

        /**
         * String for the corresponding Attachment type.
         */
        private final String type;

        /**
         * Ctor.
         * @param type String for the corresponding Attachment type.
         */
        AttachmentType(final String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return this.type;
        }
    }

    /**
     * Attachment type.
     */
    private final AttachmentType type;

    /**
     * Owner ID.
     */
    private final int owner;

    /**
     * Media ID.
     */
    private final int media;

    /**
     * Ctor.
     * @param type Attachment Type.
     * @param owner Owner ID.
     * @param media Media ID.
     */
    public AttachmentFormat(
        final AttachmentType type,
        final int owner,
        final int media
    ) {
        this.type = type;
        this.owner = owner;
        this.media = media;
    }

    /**
     * Ctor for audio that was added to the group page
     * and thus does not have an owner ID in the response.
     * @param type Attachment type.
     * @param media Media ID.
     */
    public AttachmentFormat(final AttachmentType type, final int media) {
        this(type, -AttachmentFormat.GROUP_ID, media);
    }

    @Override
    public String toString() {
        return String.format("%s%d_%d", this.type, this.owner, this.media);
    }

}

