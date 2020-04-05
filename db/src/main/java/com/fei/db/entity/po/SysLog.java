package com.fei.db.entity.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_log")
public class SysLog {
    @Id
    private Integer id;

    /**
     * 管理员
     */
    private String admin;

    /**
     * 管理员地址
     */
    private String ip;

    /**
     * 操作分类
     */
    private Integer type;

    /**
     * 操作动作
     */
    private String action;

    /**
     * 操作状态
     */
    private Boolean status;

    /**
     * 操作结果，或者成功消息，或者失败消息
     */
    private String result;

    /**
     * 补充信息
     */
    private String comment;

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
     * 获取管理员
     *
     * @return admin - 管理员
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * 设置管理员
     *
     * @param admin 管理员
     */
    public void setAdmin(String admin) {
        this.admin = admin == null ? null : admin.trim();
    }

    /**
     * 获取管理员地址
     *
     * @return ip - 管理员地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置管理员地址
     *
     * @param ip 管理员地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取操作分类
     *
     * @return type - 操作分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置操作分类
     *
     * @param type 操作分类
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取操作动作
     *
     * @return action - 操作动作
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置操作动作
     *
     * @param action 操作动作
     */
    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    /**
     * 获取操作状态
     *
     * @return status - 操作状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置操作状态
     *
     * @param status 操作状态
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取操作结果，或者成功消息，或者失败消息
     *
     * @return result - 操作结果，或者成功消息，或者失败消息
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置操作结果，或者成功消息，或者失败消息
     *
     * @param result 操作结果，或者成功消息，或者失败消息
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * 获取补充信息
     *
     * @return comment - 补充信息
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置补充信息
     *
     * @param comment 补充信息
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
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