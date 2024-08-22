package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList;

import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;

import java.util.List;

public class PagedFilesListVm {
    private int count;
    private int page;
    private int size;
    private List<FileInfo> files;

    public PagedFilesListVm() {
    }

    public PagedFilesListVm(int count, int page, int size, List<FileInfo> files) {
        this.count = count;
        this.page = page;
        this.size = size;
        this.files = files;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfo> files) {
        this.files = files;
    }
}
