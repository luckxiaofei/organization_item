package com.fei.db.entity.po;

import javax.persistence.*;

@Table(name = "sys_admin_role")
public class SysAdminRole {
    @Id
    private Integer id;

    /**
     * 管理员id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

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
     * 获取管理员id
     *
     * @return user_id - 管理员id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置管理员id
     *
     * @param userId 管理员id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}