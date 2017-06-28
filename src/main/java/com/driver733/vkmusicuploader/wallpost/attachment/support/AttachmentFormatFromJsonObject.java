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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jcabi.aspects.Immutable;
import java.io.IOException;

/**
 * Class or Interface description.
 * <p>
 * Additional info.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class AttachmentFormatFromJsonObject {

    /**
     * JsonElement that contains the response of the query.
     */
    private final JsonElement element;

    /**
     * Ctor.
     * @param element JsonElement that contains the response of the query.
     */
    public AttachmentFormatFromJsonObject(final JsonElement element) {
        this.element = element;
    }

    /**
     * Constructs an Attachment string based on the JsonObject.
     * @return Attachment string.
     * @throws IOException if unknown Attachment format is found.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public String attachmentFormat() throws IOException {
        final AttachmentFormat format;
        final JsonObject object;
        if (this.element.isJsonPrimitive()) {
            format = new AttachmentFormat(
                AttachmentType.AUDIO,
                -AttachmentFormat.GROUP_ID,
                this.element.getAsInt()
            );
        } else if (this.element.isJsonObject()) {
            object = this.element.getAsJsonObject();
            if (object.has("artist")) {
                format = new AttachmentFormat(
                    AttachmentType.AUDIO,
                    object.get("owner_id").getAsInt(),
                    object.get("id").getAsInt()
                );
            } else if (object.has("photo_75")) {
                format = new AttachmentFormat(
                    AttachmentType.PHOTO,
                    object.get("owner_id").getAsInt(),
                    object.get("id").getAsInt()
                );
            } else {
                throw new IOException("Unknown AttachmentFormat");
            }
        } else {
            throw new IOException("Unknown AttachmentFormat");
        }
        return format.toString();
    }

    /**
     * Attachment Type Enum.
     */
    @SuppressWarnings("unused")
    private enum AttachmentType {

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
     * Class or Interface description.
     * <p>
     * Additional info
     *
     * @author Mikhail Yakushin (driver733@me.com)
     * @version $Id$
     * @since 0.1
     */
    private static final class AttachmentFormat {

        /**
         * Group ID.
         * @checkstyle ConstantUsageCheck (3 lines)
         */
        private static final int GROUP_ID = 92444715;

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
        AttachmentFormat(
            final AttachmentType type,
            final int owner,
            final int media
        ) {
            this.type = type;
            this.owner = owner;
            this.media = media;
        }

        @Override
        public String toString() {
            return String.format(
                "%s%d_%d",
                this.type.toString(),
                this.owner,
                this.media
            );
        }

    }

}
