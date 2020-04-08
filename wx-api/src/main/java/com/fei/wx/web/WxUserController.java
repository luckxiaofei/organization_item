package com.fei.wx.web;

import com.fei.db.dao.SysUserMapper;
import com.fei.db.entity.po.SysUser;
import com.fei.wx.dto.UserInfo;
import com.fei.wx.service.UserInfoService;
import com.fei.wx.util.BussinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.fei.common.util.ResponseUtil;
import com.fei.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 */
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户个人页面数据
     * <p>
     * 目前是用户订单统计信息
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("getUserInfo")
    public Object getUserInfo(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        SysUser info = null;
        try {
            info = userInfoService.getInfo(userId);
        } catch (BussinessException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
        return ResponseUtil.ok(info);
    }

    @GetMapping("updateUserInfo")
    @ResponseBody
    public Object updateUserInfo(@LoginUser Integer userId, SysUser sysUser) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        sysUser.setId(userId);
        int info = userInfoService.updateById(sysUser);
        return ResponseUtil.ok(info);
    }
}
