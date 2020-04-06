package com.fei.wx.web;

import com.fei.common.util.ResponseUtil;
import com.fei.db.entity.po.SujectInfo;
import com.fei.wx.annotation.LoginUser;
import com.fei.wx.service.SubjectService;
import com.fei.wx.util.BussinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wx")
@Validated
public class WxSujectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/group/addSubject")
    @ResponseBody
    public Object addGroupSubject(@LoginUser Integer userId, SujectInfo sujectInfo) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        sujectInfo.setType(1L);
        sujectInfo.setAddUserId(userId);
        int result = subjectService.addSubject(sujectInfo);
        return ResponseUtil.ok(result);
    }

    @GetMapping("/group/detail")
    public Object getGroupDetail(@LoginUser Integer userId, Integer subjectId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok(subjectService.getSubjectDetail(subjectId, subjectId));
    }

    @GetMapping("/group/add/detail")
    public Object groupAddDetail(@LoginUser Integer userId, Integer subjectId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        try {
            return ResponseUtil.ok(subjectService.addGroupDetail(subjectId, userId));
        } catch (BussinessException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @GetMapping("sort/addSubject")
    @ResponseBody
    public Object addSortSubject(@LoginUser Integer userId, SujectInfo sujectInfo) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        sujectInfo.setType(0L);
        sujectInfo.setAddUserId(userId);
        int result = subjectService.addSubject(sujectInfo);
        return ResponseUtil.ok(result);
    }

    @GetMapping("sort/detail")
    public Object getSortDetail(@LoginUser Integer userId, Integer subjectId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok(subjectService.getSubjectDetail(subjectId,userId));
    }

    @GetMapping("/sort/add/detail")
    public Object sortAddDetail(@LoginUser Integer userId, Integer subjectId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        try {
            return ResponseUtil.ok(subjectService.addSortDetail(subjectId, userId));
        } catch (BussinessException e) {
            e.printStackTrace();
            return ResponseUtil.fail(e.getMessage());
        }
    }


    @GetMapping("getMyCreateSubject")
    public Object getMyCreateSubject(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        List<SujectInfo> myCreateSubject = subjectService.getMyCreateSubject(userId);
        return ResponseUtil.ok(myCreateSubject);
    }

    @GetMapping("getMyJoinSubject")
    public Object getMyJoinSubject(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        List<SujectInfo> myJoinSubject = subjectService.getMyJoinSubject(userId);
        return ResponseUtil.ok(myJoinSubject);
    }

}