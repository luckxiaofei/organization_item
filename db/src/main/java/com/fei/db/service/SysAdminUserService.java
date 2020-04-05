package com.fei.db.service;

import com.fei.db.dao.SysAdminMapper;
import com.fei.db.dao.SysAdminRoleMapper;
import com.fei.db.dao.SysRoleMapper;
import com.fei.db.entity.po.SysAdmin;
import com.fei.db.entity.po.SysAdminRole;
import com.fei.db.entity.po.SysRole;
import com.fei.db.entity.vo.SysAdminVO;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysAdminUserService {
    @Autowired
    private SysAdminMapper sysAdminMapper;
    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    public List<SysAdminVO> getSysAdminVO(String userName) {
        ArrayList<SysAdminVO> result = Lists.newArrayList();
        List<SysAdminVO> sysAdminList = sysAdminMapper.listSysAdminVOByName(userName);
        if (sysAdminList == null || sysAdminList.size() > 1) {
            return result;
        }

        for (SysAdminVO sysAdminVO : sysAdminList) {
            Integer adminId = sysAdminVO.getId();
            Example exampleSysAdminRole = new Example(SysAdminRole.class);
            exampleSysAdminRole.createCriteria().andEqualTo("userId", adminId);
            List<SysAdminRole> sysAdminRoles = sysAdminRoleMapper.selectByExample(exampleSysAdminRole);
            List<Integer> roleIdList = sysAdminRoles.stream().map(SysAdminRole::getRoleId).collect(Collectors.toList());
            sysAdminVO.setSysRoleIdList(roleIdList);


        }
        return result;
    }

}
