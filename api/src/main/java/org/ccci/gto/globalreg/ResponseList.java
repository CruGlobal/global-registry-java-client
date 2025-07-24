package org.ccci.gto.globalreg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ResponseList<T> implements List<T> {
    private Meta meta = new Meta();
    private final List<T> results = new ArrayList<>();

    public void setMeta(final Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return this.meta;
    }

    public boolean hasMore() {
        return this.meta != null && (this.meta.hasMore || this.meta.totalPages > this.meta.page);
    }

    protected List<T> delegate() {
        return this.results;
    }

    // List implementation methods
    @Override
    public int size() {
        return results.size();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return results.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return results.iterator();
    }

    @Override
    public Object[] toArray() {
        return results.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return results.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return results.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return results.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return results.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return results.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return results.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return results.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return results.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        results.replaceAll(operator);
    }

    @Override
    public void sort(java.util.Comparator<? super T> c) {
        results.sort(c);
    }

    @Override
    public void clear() {
        results.clear();
    }

    @Override
    public boolean equals(Object o) {
        return results.equals(o);
    }

    @Override
    public int hashCode() {
        return results.hashCode();
    }

    @Override
    public T get(int index) {
        return results.get(index);
    }

    @Override
    public T set(int index, T element) {
        return results.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        results.add(index, element);
    }

    @Override
    public T remove(int index) {
        return results.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return results.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return results.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return results.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return results.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return results.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<T> spliterator() {
        return results.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return results.removeIf(filter);
    }

    @Override
    public Stream<T> stream() {
        return results.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return results.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        results.forEach(action);
    }

    public static final class Meta {
        private boolean hasMore = false;
        @Deprecated
        private int total;
        private int from;
        private int to;
        private int page;
        @Deprecated
        private int totalPages;

        public boolean hasMore() {
            return hasMore;
        }

        public void setHasMore(final boolean hasMore) {
            this.hasMore = hasMore;
        }

        @Deprecated
        public int getTotal() {
            return this.total;
        }

        @Deprecated
        public void setTotal(final int total) {
            this.total = total;
        }

        public int getFrom() {
            return this.from;
        }

        public void setFrom(final int from) {
            this.from = from;
        }

        public int getTo() {
            return this.to;
        }

        public void setTo(final int to) {
            this.to = to;
        }

        public int getPage() {
            return page;
        }

        public void setPage(final int page) {
            this.page = page;
        }

        @Deprecated
        public int getTotalPages() {
            return this.totalPages;
        }

        @Deprecated
        public void setTotalPages(final int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
