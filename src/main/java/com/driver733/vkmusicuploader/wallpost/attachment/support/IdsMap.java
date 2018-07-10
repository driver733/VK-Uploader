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
package com.driver733.vkmusicuploader.wallpost.attachment.support;

import com.driver733.vkmusicuploader.wallpost.attachment.Attachment;
import com.driver733.vkmusicuploader.wallpost.attachment.support.queries.QueriesFromAttachments;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Forms a map of with  index (queries) - audio_id pairs
 *  from the audios queries.
 *
 *
 *
 * @since 0.1
 */
@Immutable
public final class IdsMap {

    /**
     * {@link Attachment} array.
     */
    @Immutable.Array
    private final Array<Attachment> attachments;

    /**
     * Ctor.
     * @param attachments An {@link Attachment} array.
     */
    public IdsMap(final List<Attachment> attachments) {
        this.attachments = new Array<>(attachments);
    }

    /**
     * Forms a map of with index-audio_id pairs from the audios queries.
     * @return Map.
     * @throws Exception If queries` results cannot to be obtained.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public Map<Integer, String> idsMap() throws Exception {
        final List<AbstractQueryBuilder> queries;
        try {
            queries = new QueriesFromAttachments(
                this.attachments
            ).queries();
        } catch (final IOException ex) {
            throw new IOException("Failed to obtain queries` results", ex);
        }
        int index = 0;
        final Map<Integer, String> ids = new HashMap<>();
        for (final AbstractQueryBuilder query : queries) {
            if (query.getMethod().contains("audio.add")) {
                ids.put(
                    index,
                    query.build().get("audio_id").toString()
                );
            }
            index += 1;
        }
        return ids;
    }

}
