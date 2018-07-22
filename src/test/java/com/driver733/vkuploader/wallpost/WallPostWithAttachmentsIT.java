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
package com.driver733.vkuploader.wallpost;

import com.driver733.vkuploader.post.UploadServers;
import com.driver733.vkuploader.wallpost.attachment.AttachmentWallDocument;
import com.driver733.vkuploader.wallpost.attachment.support.fields.AttachmentArraysWithProps;
import com.driver733.vkuploader.wallpost.attachment.upload.UploadWallDocument;
import com.driver733.vkuploader.wallpost.support.VkCredentials;
import com.vk.api.sdk.objects.wall.WallpostFull;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.jcip.annotations.NotThreadSafe;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

/**
 * {@link WallPostWithAttachments} IT.
 *
 *
 *
 * @since 0.2
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 */
@NotThreadSafe
public final class WallPostWithAttachmentsIT {

    /**
     * VK UserId, GroupId and auth token.
     */
    @Rule
    public final VkCredentials credentials =
        new VkCredentials();

    @After
    public void delay() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void test() throws Exception {
        final File props = new File("props.properties");
        props.deleteOnExit();
        new Properties().store(
            new FileOutputStream(props),
            ""
        );
        final UploadServers servers = new UploadServers(
            this.credentials.client(),
            this.credentials.actor(),
            this.credentials.group()
        );
        final int post =
            new WallPostWithAttachments(
                new WallPostWithOwnerId(
                    new WallPostWithMessage(
                        new WallPostBase(
                            this.credentials.client(),
                            this.credentials.actor()
                        ),
                        "The message."
                    ),
                    -this.credentials.group()
                ),
                new AttachmentArraysWithProps(
                    this.credentials.actor(),
                    new ImmutableProperties(
                        props
                    ),
                    this.credentials.group(),
                    new AttachmentWallDocument(
                        this.credentials.client(),
                        this.credentials.actor(),
                        new UploadWallDocument(
                            this.credentials.client(),
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
        final List<WallpostFull> result = this.credentials.client()
            .wall().getById(
                this.credentials.actor(),
                String.format(
                    "%d_%d",
                    -this.credentials.group(),
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
        this.credentials.client().wall()
            .delete(
                this.credentials.actor()
            )
            .ownerId(
                -this.credentials.group()
            )
            .postId(post)
            .execute();
    }

}
