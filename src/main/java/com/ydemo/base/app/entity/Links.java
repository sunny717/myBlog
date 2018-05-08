package com.ydemo.base.app.entity;

import javax.persistence.*;

/**
 * Created by babyant on 17/4/11.
 */
@Entity
@Table(name="t_links")
public class Links {
    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 名称
    private String name;

    // link logo
    private String linklogo;

    //link url
    private String linkurl;

    // 项目排序
    private Integer sort;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinklogo() {
        return linklogo;
    }

    public void setLinklogo(String linklogo) {
        this.linklogo = linklogo;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
