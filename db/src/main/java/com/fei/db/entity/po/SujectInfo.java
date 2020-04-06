package com.fei.db.entity.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "suject_info")
public class SujectInfo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 主题
     */
    private String name;

    /**
     * 是否需要实名
     */
    @Column(name = "need_name")
    private boolean needName;

    /**
     * 总人数
     */
    @Column(name = "people_sum")
    private int peopleSum;

    /**
     * 分组数
     */
    @Column(name = "group_sum")
    private int groupSum;

    /**
     * 类型（0排序，1分组）
     */
    private Long type;

    /**
     * 创建时间
     */
    @Column(name = "add_time")
    private Date addTime;

    /**
     * 创建人
     */
    @Column(name = "add_user_id")
    private Integer addUserId;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 逻辑删除
     */
    private Boolean deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getNeedName() {
        return needName;
    }

    public void setNeedName(boolean needName) {
        this.needName = needName;
    }

    public int getPeopleSum() {
        return peopleSum;
    }

    public void setPeopleSum(int peopleSum) {
        this.peopleSum = peopleSum;
    }

    public int getGroupSum() {
        return groupSum;
    }

    public void setGroupSum(int groupSum) {
        this.groupSum = groupSum;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(Integer addUserId) {
        this.addUserId = addUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}