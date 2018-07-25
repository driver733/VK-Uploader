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

import com.jcabi.aspects.Immutable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link PropsFile}.
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
public final class PropsFileTest {

    @Test
    public void storeAndRead() throws IOException {
        final File file = new File("src/test/resources/testTemp.properties");
        file.deleteOnExit();
        final PropsFile props = new PropsFile(file);
        props.with("key1", "value1");
        props.with("key2", "value2");
        final PropsFile loadedProps = new PropsFile(file);
        final Map<String, String> result = new HashMap<>();
        result.put("key1", "value1");
        result.put("key2", "value2");
        Assert.assertThat(
            loadedProps.entrySet(),
            Matchers.everyItem(
                Matchers.isIn(
                    result.entrySet()
                )
            )
        );
    }

    @Test
    public void testStore() throws IOException {
        final String key = "key1";
        final String value = "value1";
        final File file = new File(
            "anyFile1"
        );
        file.deleteOnExit();
        final PropsFile props = new PropsFile(
            file
        ).with(
            key,
            value
        );
        MatcherAssert.assertThat(
            String.format(
                "Properties file must contain the property \"%s:%s\"",
                key,
                value
            ),
            props.property(
                key
            ),
            Matchers.equalTo(
                value
            )
        );
    }

    @Test
    public void testContainsKeyTrue() throws IOException {
        final String key = "key2";
        final String value = "value2";
        final File file = new File(
            "anyFile2"
        );
        file.deleteOnExit();
        final PropsFile props = new PropsFile(
            file.toPath()
        ).with(
            key,
            value
        );
        MatcherAssert.assertThat(
            String.format(
                "Properties must contain the property with key \"%s\"",
                key
            ),
            props.containsKey(
                key
            ),
            Matchers.equalTo(
                true
            )
        );
    }

    @Test
    public void testContainsKeyFalse() throws IOException {
        final String key = "key3";
        final String value = "value3";
        final String invalid = "invalid";
        final File file = new File(
            "anyFile3"
        );
        file.deleteOnExit();
        final PropsFile props = new PropsFile(
            file
        ).with(
            key,
            value
        );
        MatcherAssert.assertThat(
            String.format(
                "Properties must not contain the property with key \"%s\"",
                invalid
            ),
            props.containsKey(
                invalid
            ),
            Matchers.equalTo(
                false
            )
        );
    }

    @Test
    public void testEntrySetWithProperties() throws IOException {
        final String fkey = "key3";
        final String fvalue = "value3";
        final String skey = "key4";
        final String svalue = "value4";
        final File file = new File(
            "anyFile4"
        );
        file.deleteOnExit();
        final PropsFile actual = new PropsFile(
            file
        ).with(
            fkey,
            fvalue
        ).with(
            skey,
            svalue
        );
        final Properties result = new Properties();
        result.put(
            fkey,
            fvalue
        );
        result.put(
            skey,
            svalue
        );
        MatcherAssert.assertThat(
            "Entry sets are not equal",
            actual.entrySet(),
            Matchers.equalTo(
                result.entrySet()
            )
        );
    }

    @Test
    public void testEntrySetEmpty() throws IOException {
        final File file = new File(
            "anyFile5"
        );
        file.deleteOnExit();
        final PropsFile actual = new PropsFile(
            file
        );
        final Properties result = new Properties();
        MatcherAssert.assertThat(
            "Entry sets are not equal",
            actual.entrySet(),
            Matchers.equalTo(
                result.entrySet()
            )
        );
    }

}
