package com.fei.db.entity.vo;

import com.fei.db.entity.po.SysAdmin;
import com.fei.db.entity.po.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class SysAdminVO extends SysAdmin {

    private List<SysRole> sysRoleList;
    private List<Integer> sysRoleIdList;


}
