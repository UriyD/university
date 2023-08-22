package com.degtyaruk.university.dao;

public class Page {
    private final Integer limit;
    private final Integer offset;

    public Page(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }
    public Integer getLimit() {
        return limit;
    }
    public Integer getOffset() {
        return offset;
    }
}

