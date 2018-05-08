package com.ydemo.base.app.entity;

import javax.persistence.*;

/**
 * Created by babyant on 17/4/11.
 */
@Entity
@Table(name="t_metas")
public class Metas {
    // 主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    /**标签的分类 link category tags*/
    private String type;

    /**一切从简 省略其他字段*/

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
