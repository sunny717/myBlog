package com.ydemo.base.app.entity;

import javax.persistence.*;

//
@Entity
@Table(name="t_relationships")
public class Relationships  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    // 内容主键
    private long cid;

    // meta 主键
    private long mid;

    //meta 类别
    private String type;


    public Relationships() {
    }

    public Relationships(long cid, long mid, String type) {
        this.cid = cid;
        this.mid = mid;
        this.type=type;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}