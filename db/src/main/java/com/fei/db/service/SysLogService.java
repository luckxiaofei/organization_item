package com.fei.db.service;

import com.fei.db.dao.SysLogMapper;
import com.fei.db.entity.po.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    public void add(SysLog sysLog){
        sysLogMapper.insertSelective(sysLog);
    }
}
