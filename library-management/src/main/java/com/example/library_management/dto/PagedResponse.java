package com.example.library_management.dto;

import java.util.List;

public class PagedResponse<T> {
    private List<T> items;
    private PageMeta meta;

    public PagedResponse() {}

    public PagedResponse(List<T> items, PageMeta meta) {
        this.items = items;
        this.meta = meta;
    }

    // getters & setters
    public List<T> getItems() {
        return items;
    }
    public void setItems(List<T> items) {
        this.items = items;
    }
    public PageMeta getMeta() {
        return meta;
    }
    public void setMeta(PageMeta meta) {
        this.meta = meta;
    }
}
