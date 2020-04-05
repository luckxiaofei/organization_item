package com.fei.db.entity.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_admin")
public class SysAdmin {
    @Id
    private Integer id;

    /**
     * 管理员名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 最近一次登录IP地址
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 最近一次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 头像图片
     */
    private String avatar;

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
     * 获取管理员名称
     *
     * @return user_name - 管理员名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置管理员名称
     *
     * @param userName 管理员名称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取管理员密码
     *
     * @return password - 管理员密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置管理员密码
     *
     * @param password 管理员密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取最近一次登录IP地址
     *
     * @return last_login_ip - 最近一次登录IP地址
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置最近一次登录IP地址
     *
     * @param lastLoginIp 最近一次登录IP地址
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * 获取最近一次登录时间
     *
     * @return last_login_time - 最近一次登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最近一次登录时间
     *
     * @param lastLoginTime 最近一次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取头像图片
     *
     * @return avatar - 头像图片
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像图片
     *
     * @param avatar 头像图片
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
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