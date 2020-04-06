package com.fei.wx.service;

import com.fei.db.dao.SysUserMapper;
import com.fei.db.entity.po.SysUser;
import com.fei.wx.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserInfoService {
    @Autowired
    private SysUserMapper sysUserMapper;


    public UserInfo getInfo(Integer userId) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        Assert.state(sysUser == null, "用户不存在");
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(sysUser.getNickname());
        userInfo.setAvatarUrl(sysUser.getAvatar());
        return userInfo;
    }

    public SysUser getUserByWeixinOpenid(String openid) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("weixinOpenid", openid);
        SysUser sysUser = sysUserMapper.selectOneByExample(example);
        return sysUser;
    }

    public void initUserInfo(SysUser sysUser) {
        sysUserMapper.insertSelective(sysUser);
    }

    public int updateById(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


}
