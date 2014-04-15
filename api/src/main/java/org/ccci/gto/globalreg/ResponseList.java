package org.ccci.gto.globalreg;

import com.google.common.collect.ForwardingList;

import java.util.ArrayList;
import java.util.List;

public class ResponseList<T> extends ForwardingList<T> {
    private Meta meta = new Meta();
    private final List<T> results = new ArrayList<>();

    public void setMeta(final Meta meta) {
        this.meta = meta;
    }

    public Meta getMeta() {
        return this.meta;
    }

    @Override
    protected List<T> delegate() {
        return this.results;
    }

    public static final class Meta {
        private int total;
        private int from;
        private int to;
        private int page;
        private int totalPages;

        public int getTotal() {
            return this.total;
        }

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

        public int getTotalPages() {
            return this.totalPages;
        }

        public void setTotalPages(final int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
