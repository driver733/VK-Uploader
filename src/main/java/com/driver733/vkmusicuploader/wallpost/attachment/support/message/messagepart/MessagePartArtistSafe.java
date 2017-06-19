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
package com.driver733.vkmusicuploader.wallpost.attachment.support.message.messagepart;

import com.driver733.vkmusicuploader.wallpost.attachment.support.mp3filefromfile.basictag.BasicTag;
import com.jcabi.aspects.Immutable;
import com.mpatric.mp3agic.ID3v1;
import java.io.IOException;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class MessagePartArtistSafe implements MessagePart {

    /**
     * The {@link BasicTag} with {@link ID3v1} tags.
     */
    private final BasicTag tag;

    /**
     * Ctor.
     * @param tag The {@link BasicTag} with {@link ID3v1} tags.
     */
    public MessagePartArtistSafe(final BasicTag tag) {
        this.tag = tag;
    }

    @Override
    public String construct() throws IOException {
        final String result;
        final String str = this.tag.construct().getArtist();
        if (str == null) {
            result = "";
        } else {
            result = String.format("Artist: %s", str);
        }
        return result;
    }

}
