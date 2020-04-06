package com.fei.db.entity.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sort_detail")
public class SortDetail {
    @Id
    private Integer id;

    /**
     * 主题id
     */
    @Column(name = "suject_info_id")
    private Integer sujectInfoId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户名（需要名字时就是输入的名字）
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户头像
     */
    @Column(name = "user_iamge_url")
    private String userIamgeUrl;

    /**
     * 序号
     */
    private Integer sort;

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
     * 获取主题id
     *
     * @return suject_info_id - 主题id
     */
    public Integer getSujectInfoId() {
        return sujectInfoId;
    }

    /**
     * 设置主题id
     *
     * @param sujectInfoId 主题id
     */
    public void setSujectInfoId(Integer sujectInfoId) {
        this.sujectInfoId = sujectInfoId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名（需要名字时就是输入的名字）
     *
     * @return user_name - 用户名（需要名字时就是输入的名字）
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名（需要名字时就是输入的名字）
     *
     * @param userName 用户名（需要名字时就是输入的名字）
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取用户头像
     *
     * @return user_iamge_url - 用户头像
     */
    public String getUserIamgeUrl() {
        return userIamgeUrl;
    }

    /**
     * 设置用户头像
     *
     * @param userIamgeUrl 用户头像
     */
    public void setUserIamgeUrl(String userIamgeUrl) {
        this.userIamgeUrl = userIamgeUrl == null ? null : userIamgeUrl.trim();
    }

    /**
     * 获取序号
     *
     * @return sort - 序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置序号
     *
     * @param sort 序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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