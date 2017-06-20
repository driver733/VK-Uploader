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
package com.driver733.vkmusicuploader.post;

// @checkstyle AvoidStaticImportCheck (30 lines)

import com.driver733.vkmusicuploader.post.posts.Posts;
import com.jcabi.aspects.Immutable;
import com.jcabi.immutable.Array;
import com.jcabi.log.Logger;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

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
public final class WatchDirs implements Closeable {

    /**
     * Directories to watch to changes.
     */
    private final Array<File> dirs;

    /**
     * The {@link Posts} that handle the
     *  posting of files in watched directories.
     */
    private final Posts posts;

    /**
     * A {@link WatchService}.
     */
    private final WatchService watcher;

    /**
     * A {@link Map} that stores the keys with directories` paths.
     */
    private final Map<WatchKey, Path> keys;

    /**
     * Ctor.
     * @param posts The {@link Posts} that handle the
     *  posting of files in watched directories.
     * @param dirs Directories to watch for changes.
     * @throws IOException If a {@link WatchService} cannot be created.
     */
    public WatchDirs(
        final Posts posts,
        final File... dirs
    ) throws IOException {
        this.posts = posts;
        this.dirs = new Array<>(dirs);
        this.keys = new HashMap<>();
        this.watcher = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("#close()");
    }

    /**
     * Starts watching the directories for changes.
     * @throws IOException If a directory cannot be registered.
     */
    public void start() throws IOException {
        for (final File dir : this.dirs) {
            this.posts.postFromDir(dir).post();
            this.registerDirectory(dir.toPath());
        }
        this.processEvents();
    }

    /**
     * Process all events for the keys that have new events.
     * @throws IOException If an error occurs while processing
     */
    private void processEvents() throws IOException {
        while (true) {
            final WatchKey key;
            try {
                key = this.watcher.take();
            } catch (final InterruptedException ex) {
                throw new IOException(
                    "Failed to get the signal for directory changes.", ex
                );
            }
            final Path dir = this.keys.get(key);
            if (dir == null) {
                throw new IOException("WatchKey not recognized!");
            }
            this.processSubevents(key, dir);
            if (!key.reset()) {
                if (this.keys.isEmpty()) {
                    throw new IOException(
                        "All subdirectories are inaccessible"
                    );
                }
                this.keys.remove(key);
            }
            this.posts
                .postFromDir(
                    dir.toFile()
                ).post();
        }
    }

    /**
     * Processes subevents of the key.
     * @param key That has new events.
     * @param dir For the key.
     * @throws IOException If a subdirectory cannot be registered.
     */
    private void processSubevents(final WatchKey key, final Path dir)
        throws IOException {
        for (final WatchEvent event : key.pollEvents()) {
            final WatchEvent.Kind kind = event.kind();
            final Path name = (Path) event.context();
            final Path child = dir.resolve(name);
            Logger.debug(
                this,
                "%s: %s%n", event.kind().name(), child
            );
            if (kind == ENTRY_CREATE) {
                try {
                    if (Files.isDirectory(child)) {
                        this.processSubevents(child);
                    }
                } catch (final IOException ex) {
                    throw new IOException(
                        "Failed to register subdirectories.",
                        ex
                    );
                }
            }
        }
    }

    /**
     * Starts watching the directory for changes.
     * @param dir Directory to watch.
     * @throws IOException If the directory cannot be registered.
     */
    private void registerDirectory(final Path dir) throws IOException {
        try {
            final WatchKey key = dir.register(
                this.watcher,
                ENTRY_CREATE,
                ENTRY_DELETE,
                ENTRY_MODIFY
            );
            this.keys.put(key, dir);
        } catch (final IOException ex) {
            throw new IOException("Failed to register directory", ex);
        }
    }

    /**
     * Starts watching the subdirectories of the provided directory for changes.
     * @param root Root directory of the subdirectories to register.
     * @throws IOException If a subdirectory cannot be registered.
     * @checkstyle RequireThisCheck (20 lines)
     * @checkstyle NonStaticMethodCheck (20 lines)
     */
    private void processSubevents(final Path root) throws IOException {
        try {
            Files.walkFileTree(
                root,
                new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(
                        final Path subdir,
                        final BasicFileAttributes attrs
                    ) throws IOException {
                        registerDirectory(subdir);
                        return FileVisitResult.CONTINUE;
                    }
                });
        } catch (final IOException ex) {
            throw new IOException("Failed to register subdirectories", ex);
        }
    }

}
