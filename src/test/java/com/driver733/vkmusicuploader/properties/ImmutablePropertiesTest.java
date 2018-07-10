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
package com.driver733.vkmusicuploader.properties;

import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link ImmutableProperties}.
 *
 *
 *
 * @since 0.1
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle LocalFinalVariableNameCheck (50 lines)
 * @checkstyle AvoidDuplicateLiterals (100 lines)
 */
@Immutable
@SuppressWarnings(
    {
        "PMD.AvoidDuplicateLiterals",
        "PMD.ProhibitPlainJunitAssertionsRule",
        "PMD.NonStaticInitializer"
    }
    )
public final class ImmutablePropertiesTest {

    @Test
    public void storeAndRead() throws IOException {
        final File file = new File("src/test/resources/testTemp.properties");
        file.deleteOnExit();
        final ImmutableProperties props = new ImmutableProperties(file);
        props.setPropertyAndStore("key1", "value1");
        props.setPropertyAndStore("key2", "value2");
        final ImmutableProperties loadedProps = new ImmutableProperties(file);
        loadedProps.load();
        final Map<Object, Object> result = new HashMap<>();
        result.put("key1", "value1");
        result.put("key2", "value2");
        Assert.assertThat(
            loadedProps.entrySet(),
            Matchers.everyItem(
                Matchers.isIn(result.entrySet())
            )
        );
    }

    @Test(expected = IOException.class)
    public void testStore() throws IOException {
        final File file = new File("anyStream1-2");
        file.deleteOnExit();
        final ImmutableProperties props = new ImmutableProperties(
            new File("anyFile1-1")
        );
        props.store(
            new FileOutputStream(file),
            ""
        );
    }

    @Test(expected = IOException.class)
    public void testStoreInvalidFile() throws IOException, URISyntaxException {
        final ImmutableProperties props = new ImmutableProperties(
            new File(
                new URI("file:///home/username/RomeoAndJuliet.pdf")
            )
        );
        props.store();
    }

    @Test(expected = IOException.class)
    public void testLoadValid() throws IOException {
        final ImmutableProperties props = new ImmutableProperties(
            new File("anyFile2-1")
        );
        props.load(
            new FileInputStream("anyFile2-2")
        );
    }

    @Test(expected = IOException.class)
    public void testLoadInvalidFile() throws IOException, URISyntaxException {
        final ImmutableProperties props = new ImmutableProperties(
            new File(
                new URI("file:///home/username/RomeoAndJuliet.pdf")
            )
        );
        props.load();
    }

    @Test(expected = IOException.class)
    public void testLoadUnsupportedMethod()
        throws IOException {
        final ImmutableProperties props = new ImmutableProperties(
            new File(
                "audiosTest.properties"
            )
        );
        props.load(
            new FileInputStream(
                new File(
                    "src/test/resources/audiosTest.properties"
                )
            )
        );
    }

    @Test
    public void testLoaded() throws IOException {
        final ImmutableProperties expected = new ImmutableProperties(
            new File(
                "src/test/resources/audiosTest.properties"
            )
        );
        expected.load();
        final ImmutableProperties actual = expected.loaded();
        MatcherAssert.assertThat(
            "returned object does not match the original one",
            actual,
            Matchers.equalTo(
                expected
            )
        );
        MatcherAssert.assertThat(
            "Properties have not been loaded",
            actual.entrySet(),
            Matchers.equalTo(
                expected.entrySet()
            )
        );
    }

    @Test
    public void testStored() throws IOException {
        final String key = "key";
        final String value = "value";
        final File props = new File(
            "src/test/resources/testingProps.properties"
        );
        props.deleteOnExit();
        final ImmutableProperties expected = new ImmutableProperties(
            props
        );
        final ImmutableProperties actual = new ImmutableProperties(
            props
        ).stored();
        actual.put(key, value);
        MatcherAssert.assertThat(
            "Returned object does not match the original one",
            actual,
            Matchers.equalTo(
                expected
            )
        );
        MatcherAssert.assertThat(
            "Properties have not been stored",
            actual.entrySet(),
            Matchers.equalTo(
                new HashMap<String, String>() {
                    {
                        put(key, value);
                    }
                }.entrySet()
            )
        );
    }

}
