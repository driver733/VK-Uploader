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
package com.driver733.vkmusicuploader.wallpost.attachment.support.mp3filefromfile.bytearray.fallback;

import com.jcabi.aspects.Immutable;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Class or Interface description.
 * <p>
 * Additional info
 *
 * @author Mikhail Yakushin (driver733@me.com)
 * @version $Id$
 * @param <E> Return type.
 * @since 0.1
 */
@Immutable
public final class SingletonList<E> extends AbstractList<E> {

    /**
     * Element.
     */
    private final List<E> list;

    /**
     * Ctor for an empty {@link SingletonList}.
     */
    public SingletonList() {
        this(
            Collections.emptyList()
        );
    }

    /**
     * Ctor.
     * @param element Element.
     */
    public SingletonList(final E element) {
        this(
            Collections.singletonList(element)
        );
    }

    /**
     * Ctor.
     * @param list List.
     */
    private SingletonList(final List<E> list) {
        super();
        this.list = list;
    }

    @Override
    public E get(final int index) {
        if (index == 0) {
            return this.list.get(0);
        }
        throw new IndexOutOfBoundsException("Index has to be equal to zero");
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void replaceAll(final UnaryOperator<E> operator) {
        throw new UnsupportedOperationException("This list is immutable");
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public boolean removeIf(final Predicate<? super E> filter) {
        throw new UnsupportedOperationException("This list is immutable");
    }

    @Override
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void sort(final Comparator<? super E> cmp) {
        throw new UnsupportedOperationException("This list is immutable");
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

}
