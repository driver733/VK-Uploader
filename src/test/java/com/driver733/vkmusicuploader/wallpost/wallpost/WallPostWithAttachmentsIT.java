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
package com.driver733.vkmusicuploader.wallpost.wallpost;

import com.driver733.vkmusicuploader.post.UploadUrls;
import com.driver733.vkmusicuploader.properties.ImmutableProperties;
import com.driver733.vkmusicuploader.wallpost.attachment.AttachmentWallDocument;
import com.driver733.vkmusicuploader.wallpost.attachment.support.attachment.fields.AttachmentArraysWithProps;
import com.driver733.vkmusicuploader.wallpost.attachment.upload.UploadWallDocument;
import com.vk.api.sdk.objects.wall.WallpostFull;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import net.jcip.annotations.NotThreadSafe;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * {@link WallPostWithAttachments} IT.
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 */
@NotThreadSafe
public final class WallPostWithAttachmentsIT extends AbstractEntrance {

    @Test
    public void test() throws Exception {
        final File props = new File("props.properties");
        props.deleteOnExit();
        new Properties().store(
            new FileOutputStream(props),
            ""
        );
        final UploadUrls servers = new UploadUrls(
            client(),
            actor(),
            groupId()
        );
        final int post =
            new WallPostWithAttachments(
                new WallPostWithOwnerId(
                    new WallPostWithMessage(
                        new WallPostBase(
                            client(),
                            actor()
                        ),
                        "The message."
                    ),
                    -groupId()
                ),
                new AttachmentArraysWithProps(
                    actor(),
                    new ImmutableProperties(
                        props
                    ),
                    groupId(),
                    new AttachmentWallDocument(
                        client(),
                        actor(),
                        new UploadWallDocument(
                            client(),
                            servers.docs(),
                            new File(
                                "src/test/resources/attachment.txt"
                            )
                        )
                    )
                )
            ).construct()
                .execute()
                .getPostId();
        final List<WallpostFull> result = client()
            .wall().getById(
                actor(),
                String.format(
                    "%d_%d",
                    -groupId(),
                    post
                )
            ).execute();
        MatcherAssert.assertThat(
            result.get(0)
                .getAttachments()
                .get(0)
                .getDoc()
                .getTitle(),
            Matchers.equalTo(
                "attachment.txt"
            )
        );
        client().wall()
            .delete(
                actor()
            )
            .ownerId(
                -groupId()
            )
            .postId(post)
            .execute();
        exit();
    }

}
