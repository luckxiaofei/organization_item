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

}