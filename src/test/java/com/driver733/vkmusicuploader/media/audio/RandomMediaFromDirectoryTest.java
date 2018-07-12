package com.driver733.vkmusicuploader.media.audio;

import com.driver733.vkmusicuploader.media.RandomMediaFromDirectory;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link RandomMediaFromDirectory}.
 *
 * @checkstyle AnonInnerLengthCheck (500 lines
 * @checkstyle JavadocMethodCheck (500 lines)
 * @checkstyle StringLiteralsConcatenationCheck (500 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (50 lines)
 * @checkstyle MethodLength (500 lines)
 * @since 0.1
 */
public final class RandomMediaFromDirectoryTest {

    @Test
    public void testSingle() throws IOException {
        final Path root = Paths.get("src/test/resources/random/testPhotoAlbum/");
        MatcherAssert.assertThat(
            "Returned path does not match the expected one.",
            new RandomMediaFromDirectory(
                root
            ).file(),
            Matchers.anyOf(
                Matchers.equalTo(
                    root.resolve("1.jpg")
                ),
                Matchers.equalTo(
                    root.resolve("2.jpg")
                ),
                Matchers.equalTo(
                    root.resolve("3.jpg")
                ),
                Matchers.equalTo(
                    root.resolve("4.jpg")
                ),
                Matchers.equalTo(
                    root.resolve("5.jpg")
                ),
                Matchers.equalTo(
                    root.resolve("expected.properties")
                )
            )
        );
    }

    @Test
    public void testShuffledList() throws IOException {
        final Path root = Paths.get("src/test/resources/random/testPhotoAlbum/");
        MatcherAssert.assertThat(
            "Returned path does not match the expected one.",
            new RandomMediaFromDirectory(
                root
            ).files(),
                Matchers.containsInAnyOrder(
                    root.resolve("1.jpg"),
                    root.resolve("2.jpg"),
                    root.resolve("3.jpg"),
                    root.resolve("4.jpg"),
                    root.resolve("5.jpg"),
                    root.resolve("expected.properties")
                )
        );
    }

}
