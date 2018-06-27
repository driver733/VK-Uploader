/**
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
package com.driver733.vkmusicuploader.wallpost.attachment.support.queries;

import com.driver733.vkmusicuploader.wallpost.attachment.Attachment;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Returns all queries for attachments.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public final class QueriesFromAttachments implements Queries {

    /**
     * List of {@link Attachment}s.
     */
    private final List<Attachment> attachments;

    /**
     * List of {@link Attachment}s.
     * @param attachments List of {@link Attachment}s.
     */
    public QueriesFromAttachments(final List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    @Cacheable(forever = true)
    public List<AbstractQueryBuilder> queries() throws Exception {
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
