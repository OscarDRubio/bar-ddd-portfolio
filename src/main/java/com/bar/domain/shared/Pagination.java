package com.bar.domain.shared;

public class Pagination {
    private final int page;
    private final int size;

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
