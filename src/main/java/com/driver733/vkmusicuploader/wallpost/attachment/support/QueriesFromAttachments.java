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

import com.driver733.vkmusicuploader.wallpost.attachment.Attachment;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
final class QueriesFromAttachments {

    /**
     * {@link Attachment} array.
     */
    @Immutable.Array
    private final Array<Attachment> attachments;

    /**
     * Ctor.
     * @param attachments An {@link Attachment} array.
     */
    QueriesFromAttachments(final List<Attachment> attachments) {
        this.attachments = new Array<>(attachments);
    }

    /**
     * Forms a map of with index-audio_id pairs from the audio queries.
     * @return Map.
     * @throws IOException If queries` results cannot to be obtained.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public Map<Integer, String> idsMap() throws IOException {
        final List<AbstractQueryBuilder> queries;
        try {
            queries = this.queries();
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

    /**
     * Finds which queries have no cache and thus need to be executed.
     * @param cached Whether to return cached or non-cached queries.
     * @return A {@link List} of {@link AbstractQueryBuilder} to be executed.
     * @throws IOException If queries cannot to be obtained.
     */
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public List<AbstractQueryBuilder> queries(final boolean cached)
        throws IOException {
        final List<AbstractQueryBuilder> queries;
        try {
            queries = this.queries();
        } catch (final IOException ex) {
            throw new IOException("Failed to obtain queries` results", ex);
        }
        final List<AbstractQueryBuilder> list = new ArrayList<>(queries.size());
        for (final AbstractQueryBuilder query : queries) {
            if (
                cached && query.isCached()
                    || !cached && !query.isCached()
                ) {
                list.add(query);
            }
        }
        return list;
    }

    /**
     * Executes all queries associated with attachments
     *  and accumulates their results in a {@link List}.
     * @return A {@link List} with all attachments` results.
     * @throws IOException If any of the attachments fail to upload.
     */
    @Cacheable(forever = true)
    private List<AbstractQueryBuilder> queries() throws IOException {
        final List<AbstractQueryBuilder> list =
            new ArrayList<>(this.attachments.size());
        for (final Attachment attachment : this.attachments) {
            final List<AbstractQueryBuilder> queries;
            try {
                queries = attachment.upload();
            } catch (final ApiException | ClientException | IOException ex) {
                throw new IOException("Failed to upload attachments", ex);
            }
            list.addAll(queries);
        }
        return list;
    }

}
