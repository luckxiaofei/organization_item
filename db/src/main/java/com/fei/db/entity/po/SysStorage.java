package com.fei.db.entity.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_storage")
public class SysStorage {
    @Id
    private Integer id;

    /**
     * 文件的唯一索引
     */
    private String key;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件访问链接
     */
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文件的唯一索引
     *
     * @return key - 文件的唯一索引
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置文件的唯一索引
     *
     * @param key 文件的唯一索引
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * 获取文件名
     *
     * @return name - 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文件名
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取文件类型
     *
     * @return type - 文件类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置文件类型
     *
     * @param type 文件类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取文件大小
     *
     * @return size - 文件大小
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 获取文件访问链接
     *
     * @return url - 文件访问链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文件访问链接
     *
     * @param url 文件访问链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取创建时间
     *
     * @return add_time - 创建时间
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置创建时间
     *
     * @param addTime 创建时间
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取逻辑删除
     *
     * @return deleted - 逻辑删除
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * 设置逻辑删除
     *
     * @param deleted 逻辑删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}