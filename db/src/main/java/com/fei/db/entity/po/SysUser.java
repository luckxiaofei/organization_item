package com.fei.db.entity.po;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser {
    @Id
    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 性别：0 未知， 1男， 1 女
     */
    private Byte gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 最近一次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最近一次登录IP地址
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 0 普通用户，1 VIP用户，2 高级VIP用户
     */
    @Column(name = "user_level")
    private Byte userLevel;

    /**
     * 用户昵称或网络名称
     */
    private String nickname;

    /**
     * 用户手机号码
     */
    private String mobile;

    /**
     * 用户头像图片
     */
    private String avatar;

    /**
     * 微信登录openid
     */
    @Column(name = "weixin_openid")
    private String weixinOpenid;

    /**
     * 微信登录会话KEY
     */
    @Column(name = "session_key")
    private String sessionKey;

    /**
     * 0 可用, 1 禁用, 2 注销
     */
    private Byte status;

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
     * 获取用户名称
     *
     * @return username - 用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名称
     *
     * @param username 用户名称
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取性别：0 未知， 1男， 1 女
     *
     * @return gender - 性别：0 未知， 1男， 1 女
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * 设置性别：0 未知， 1男， 1 女
     *
     * @param gender 性别：0 未知， 1男， 1 女
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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
     * 获取0 普通用户，1 VIP用户，2 高级VIP用户
     *
     * @return user_level - 0 普通用户，1 VIP用户，2 高级VIP用户
     */
    public Byte getUserLevel() {
        return userLevel;
    }

    /**
     * 设置0 普通用户，1 VIP用户，2 高级VIP用户
     *
     * @param userLevel 0 普通用户，1 VIP用户，2 高级VIP用户
     */
    public void setUserLevel(Byte userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * 获取用户昵称或网络名称
     *
     * @return nickname - 用户昵称或网络名称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称或网络名称
     *
     * @param nickname 用户昵称或网络名称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取用户手机号码
     *
     * @return mobile - 用户手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置用户手机号码
     *
     * @param mobile 用户手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取用户头像图片
     *
     * @return avatar - 用户头像图片
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置用户头像图片
     *
     * @param avatar 用户头像图片
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取微信登录openid
     *
     * @return weixin_openid - 微信登录openid
     */
    public String getWeixinOpenid() {
        return weixinOpenid;
    }

    /**
     * 设置微信登录openid
     *
     * @param weixinOpenid 微信登录openid
     */
    public void setWeixinOpenid(String weixinOpenid) {
        this.weixinOpenid = weixinOpenid == null ? null : weixinOpenid.trim();
    }

    /**
     * 获取微信登录会话KEY
     *
     * @return session_key - 微信登录会话KEY
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * 设置微信登录会话KEY
     *
     * @param sessionKey 微信登录会话KEY
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey == null ? null : sessionKey.trim();
    }

    /**
     * 获取0 可用, 1 禁用, 2 注销
     *
     * @return status - 0 可用, 1 禁用, 2 注销
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置0 可用, 1 禁用, 2 注销
     *
     * @param status 0 可用, 1 禁用, 2 注销
     */
    public void setStatus(Byte status) {
        this.status = status;
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