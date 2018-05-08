package com.ydemo.base.app.model;

import com.ydemo.base.cores.model.PageQo;

/**
 * Created by 宏宾 on 2017/4/15.
 */
public class SearchQo extends PageQo {
    private Integer pageSize =12;
    private String category;
    private String tag;
    private String title;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
