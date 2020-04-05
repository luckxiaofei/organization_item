package com.fei.db.service;

import com.fei.db.dao.SysStorageMapper;
import com.fei.db.entity.po.SysStorage;
import com.fei.db.entity.po.SysStorage;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SysStorageService {
    @Autowired
    private SysStorageMapper storageMapper;

    public void deleteByKey(String key) {
        Example example = new Example(SysStorage.class);
        example.createCriteria().andEqualTo("key", key);
        storageMapper.deleteByExample(example);
    }

    public void add(SysStorage storageInfo) {
        storageInfo.setAddTime(new Date());
        storageInfo.setUpdateTime(new Date());
        storageMapper.insertSelective(storageInfo);
    }

    public SysStorage findByKey(String key) {
        Example example = new Example(SysStorage.class);
        example.createCriteria().andEqualTo("key", key).andEqualTo("deleted", false);
        return storageMapper.selectOneByExample(example);
    }

    public int update(SysStorage storageInfo) {
        storageInfo.setUpdateTime(new Date());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public SysStorage findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<SysStorage> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        Example example = new Example(SysStorage.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andEqualTo("key", key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andLike("name", "%" + name + "%");
        }
        criteria.andEqualTo("deleted", false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
