package com.fei.db.dao;

import com.fei.db.entity.po.SysLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface SysLogMapper extends Mapper<SysLog> {
}