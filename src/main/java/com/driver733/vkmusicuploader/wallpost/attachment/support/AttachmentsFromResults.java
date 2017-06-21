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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
final class AttachmentsFromResults {

    /**
     * JsonArray that contains the
     *  {@link QueryResultsBasic}
     *  of the queries.
     */
    private final JsonArray root;

    /**
    * Ctor.
    * @param root JsonArray that contains the
    *  {@link QueryResultsBasic}
    *  of the queries.
    */
    AttachmentsFromResults(final JsonArray root) {
        this.root = root;
    }

    /**
     * Maps queries queriesResults to Attachment strings.
     * @return Attachment strings.
     * @throws IOException If unknown Attachment format is found.
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public Array<String> attachments() throws IOException {
        final List<String> list = new ArrayList<>(this.root.size());
        for (final JsonElement element : this.root) {
            list.addAll(
                new AttachmentFormatStrings(element)
                    .attachmentStrings()
            );
        }
        return new Array<>(list);
    }

}
