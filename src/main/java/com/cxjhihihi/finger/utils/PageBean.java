package com.cxjhihihi.finger.utils;

import java.util.List;

public class PageBean<T> {

    public int pageMaxRecord = 20; // 每页显示记录�?默认�?0

    public int page = 1; // 当前页数，默认为1

    public int totalRecord; // 总记录数

    public int totalPage; // 总页�?

    public int startRecord; // 起始记录

    public List<T> recordList; // 记录�?

    public int totalRecordOfCurrentPage; // 当前页�?记录�?

    public PageBean() {}

    public PageBean(int totalRecord) {
        this.setTotalRecord(totalRecord);
    }

    public PageBean(int page, int totalRecord) {
        this.setPage(page);
        this.setTotalRecord(totalRecord);
    }

    public PageBean(int page, int totalRecord, int pageMaxRecord) {
        this.setPage(page);
        this.setPageMaxRecord(pageMaxRecord);
        this.setTotalRecord(totalRecord);
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;

    }

    public int getPageMaxRecord() {
        return pageMaxRecord;
    }

    public void setPageMaxRecord(int pageMaxRecord) {
        this.pageMaxRecord = pageMaxRecord;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page > 1) {
            this.page = page;
        }
    }

    public int getTotalPage() {
        setTotalPage();
        return totalPage;
    }

    public void setTotalPage() {
        totalPage = totalRecord / pageMaxRecord;
        if ((totalRecord % pageMaxRecord) > 0) {
            totalPage += 1;
        }
        if (totalPage <= 0) {
            totalPage = 1;
        }
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        this.setTotalPage();
        this.setStartRecord();
    }

    public int getStartRecord() {
        setStartRecord();
        return startRecord;
    }

    public void setStartRecord() {
        startRecord = (page - 1) * pageMaxRecord;
    }

    public int getLastPage() {
        return getTotalPage();
    }

    public int getNextPage() {
        if ((totalPage - page) > 0) {
            return page + 1;
        } else {
            return page;
        }
    }

    public int getPrevPage() {
        if ((page - 1) > 0) {
            return page - 1;
        } else {
            return page;
        }
    }

    public int getTotalRecordOfCurrentPage() {
        if (recordList == null)
            return 0;
        else
            return recordList.size();
    }

}
