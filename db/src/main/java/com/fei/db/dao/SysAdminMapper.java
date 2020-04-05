package com.fei.db.dao;

import com.fei.db.entity.po.SysAdmin;
import com.fei.db.entity.vo.SysAdminVO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SysAdminMapper extends Mapper<SysAdmin> {

    List<SysAdminVO> listSysAdminVOByName(String name);

}