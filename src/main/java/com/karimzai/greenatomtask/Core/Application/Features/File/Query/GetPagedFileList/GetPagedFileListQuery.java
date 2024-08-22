package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList;

public class GetPagedFileListQuery {
    private int page;
    private int size;
    private boolean orderByCreation;

    public GetPagedFileListQuery() {
    }

    public GetPagedFileListQuery(int page, int size, boolean orderByCreation) {
        this.page = page;
        this.size = size;
        this.orderByCreation = orderByCreation;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public boolean isOrderByCreation() {
        return orderByCreation;
    }
}
