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
package com.driver733.vkuploader.wallpost.attachment;

import com.driver733.vkuploader.wallpost.ImmutableProperties;
import com.driver733.vkuploader.wallpost.attachment.support.AudioStatus;
import com.jcabi.aspects.Immutable;
import com.vk.api.sdk.objects.audio.Audio;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test for {@link AttachmentAudioProps}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (500 lines)
 */
@Immutable
public final class AttachmentAudioPropsTest {

    @Test
    public void test() throws IOException {
        final String fileName = "testName";
        final Audio test = Mockito.mock(Audio.class);
        final int mediaId = 123;
        final int ownerId = 321;
        Mockito.when(test.getId()).thenReturn(mediaId);
        Mockito.when(test.getOwnerId()).thenReturn(ownerId);
        final Properties temp = new Properties();
        final File file = new File("temp.properties");
        file.deleteOnExit();
        temp.store(
            new FileOutputStream(file),
            ""
        );
        new AttachmentAudioProps(
            test,
            fileName,
            new ImmutableProperties(
                file
            )
        ).saveProps();
        final ImmutableProperties props = new ImmutableProperties(file);
        props.load();
        MatcherAssert.assertThat(
            props.getProperty(fileName),
            Matchers.equalTo(
                String.format(
                    "%s_%d_%d",
                    AudioStatus.UPLOADED,
                    ownerId,
                    mediaId
                )
            )
        );
    }

}
