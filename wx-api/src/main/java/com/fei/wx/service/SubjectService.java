package com.fei.wx.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import com.aliyun.oss.common.utils.DateUtil;
import com.fei.db.dao.GroupDetailMapper;
import com.fei.db.dao.SortDetailMapper;
import com.fei.db.dao.SujectInfoMapper;
import com.fei.db.dao.SysUserMapper;
import com.fei.db.entity.po.GroupDetail;
import com.fei.db.entity.po.SortDetail;
import com.fei.db.entity.po.SujectInfo;
import com.fei.db.entity.po.SysUser;
import com.fei.db.entity.vo.SubjectInfoVO;
import com.fei.db.util.Collections3;
import com.fei.wx.util.BussinessException;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.DateUtils;
import jodd.util.StringUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private static final Log logger = LogFactory.getLog(SubjectService.class);
    @Autowired
    private SujectInfoMapper sujectInfoMapper;
    @Autowired
    private GroupDetailMapper groupDetailMapper;
    @Autowired
    private SortDetailMapper sortDetailMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    WxMaService wxMaService;


    public int addSubject(SujectInfo sujectInfo) {
        sujectInfo.setAddTime(new Date());
        sujectInfoMapper.insertSelective(sujectInfo);

        Integer subjectId = sujectInfo.getId();
        if (sujectInfo.getType().equals(1L)) {
            int minSize = sujectInfo.getPeopleSum() / sujectInfo.getGroupSum();
            List<GroupDetail> groupDetailList = Lists.newArrayList();
            for (int i = 0; i < sujectInfo.getGroupSum(); i++) {
                for (int j = 0; j < minSize; j++) {
                    GroupDetail groupDetail = new GroupDetail();
                    groupDetail.setSujectInfoId(subjectId);
                    groupDetail.setUserId(0);
                    groupDetail.setUserIamgeUrl("");
                    groupDetail.setGroupNumber(i + 1);
                    groupDetail.setAddTime(new Date());
                    groupDetail.setUpdateTime(new Date());
                    groupDetail.setDeleted(false);
                    groupDetailList.add(groupDetail);
                    groupDetailMapper.insertSelective(groupDetail);
                }
            }
            int surplusSum = sujectInfo.getPeopleSum() % sujectInfo.getGroupSum();
            if (surplusSum > 0) {
                for (int i = 0; i < surplusSum; i++) {
                    GroupDetail groupDetail = new GroupDetail();
                    groupDetail.setSujectInfoId(subjectId);
                    groupDetail.setUserId(0);
                    groupDetail.setUserIamgeUrl("");
                    groupDetail.setGroupNumber(i + 1);
                    groupDetail.setAddTime(new Date());
                    groupDetail.setUpdateTime(new Date());
                    groupDetail.setDeleted(false);
                    groupDetailList.add(groupDetail);
                    groupDetailMapper.insertSelective(groupDetail);
                }
            }
        }
        return subjectId;
    }

    /**
     * 获取主题详细
     *
     * @param subjectId
     * @param userId
     * @return
     */
    public SubjectInfoVO getSubjectDetail(Integer subjectId, Integer userId) {
        SubjectInfoVO subjectInfoVO = sujectInfoMapper.getSubjectInfoVO(subjectId);
        //判断是否参加
        boolean isJoin = isJoin(subjectId, userId);
        subjectInfoVO.setIsJoin(isJoin);
        //判断是否实名
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        if (StringUtil.isBlank(sysUser.getUserName())) {
            subjectInfoVO.setRealName(false);
        } else {
            subjectInfoVO.setRealName(true);
        }
        if (subjectInfoVO.getType().equals(0l)) {
            if (isJoin) {
                SortDetail sortDetail = subjectInfoVO.getSortDetailList().stream().filter(p -> p.getUserId().equals(userId)).findFirst().get();
                subjectInfoVO.setSortNumber(sortDetail.getSort());
            }
            subjectInfoVO.setJoinSum(Optional.ofNullable(subjectInfoVO.getSortDetailList().size()).orElse(0));
        } else if (subjectInfoVO.getType().equals(1l)) {
            if (isJoin) {
                GroupDetail groupDetail = subjectInfoVO.getGroupDetailList().stream().filter(p -> p.getUserId().equals(userId)).findFirst().get();
                subjectInfoVO.setGroupNumber(groupDetail.getGroupNumber());
            }
            List<GroupDetail> groupDetails = subjectInfoVO.getGroupDetailList().stream().filter(p -> p.getUserId() != null && p.getUserId() > 0).collect(Collectors.toList());
            subjectInfoVO.setJoinSum(Optional.ofNullable(groupDetails.size()).orElse(0));
        }

        //组装 分组数据
        if (Collections3.isNotEmpty(subjectInfoVO.getGroupDetailList())) {
            Map<Integer, List<GroupDetail>> listMap = subjectInfoVO.getGroupDetailList().stream().sorted(new Comparator<GroupDetail>() {
                @Override
                public int compare(GroupDetail o1, GroupDetail o2) {
                    if (o1.getUserId() > o2.getUserId()) {
                        return 1;
                    } else if (o1.getUserId() < o2.getUserId()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }).collect(Collectors.groupingBy(GroupDetail::getGroupNumber));
            subjectInfoVO.setGroupDetailMap(listMap);
            subjectInfoVO.setGroupMaxNumber(0);
            for (Integer key : listMap.keySet()) {
                int size = listMap.get(key).size();
                if (size > subjectInfoVO.getGroupMaxNumber()) {
                    subjectInfoVO.setGroupMaxNumber(size);
                }
            }
            subjectInfoVO.setGroupDetailList(Collections.EMPTY_LIST);
        }

        //排序
        if (Collections3.isNotEmpty(subjectInfoVO.getSortDetailList())) {
            List<SortDetail> collect = subjectInfoVO.getSortDetailList().stream().sorted(new Comparator<SortDetail>() {
                @Override
                public int compare(SortDetail o1, SortDetail o2) {
                    if (o1.getSort() > o2.getSort()) {
                        return 1;
                    } else if (o1.getSort() < o2.getSort()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }).collect(Collectors.toList());
            subjectInfoVO.setSortDetailList(collect);
        }

        return subjectInfoVO;
    }

    /**
     * 判断用户是否参加的主题
     *
     * @param subjectId
     * @param userId
     * @return
     */
    private boolean isJoin(Integer subjectId, Integer userId) {
        Example exampleGroupDetail = new Example(GroupDetail.class);
        exampleGroupDetail.createCriteria().andEqualTo("userId", userId).andEqualTo("sujectInfoId", subjectId);
        List<GroupDetail> groupDetailList = groupDetailMapper.selectByExample(exampleGroupDetail);
        if (Collections3.isNotEmpty(groupDetailList)) {
            return true;
        }

        Example exampleSortDetail = new Example(SortDetail.class);
        exampleSortDetail.createCriteria().andEqualTo("userId", userId).andEqualTo("sujectInfoId", subjectId);
        List<SortDetail> sortDetailList = sortDetailMapper.selectByExample(exampleSortDetail);
        if (Collections3.isNotEmpty(sortDetailList)) {
            return true;
        }
        return false;
    }

    public int addSortDetail(Integer subjectId, Integer userId) throws BussinessException {
        SubjectInfoVO subjectInfoVO = sujectInfoMapper.getSubjectInfoVO(subjectId);
        List<SortDetail> sortDetailList = subjectInfoVO.getSortDetailList();
        if (sortDetailList.stream().anyMatch(p -> p.getUserId() == userId)) {
            logger.info(">>>>已参加,不能重复参加");
            throw new BussinessException("不能重复参加");
        }
        int peopleSum = subjectInfoVO.getPeopleSum();
        Integer size = Optional.ofNullable(sortDetailList.size()).orElse(0);
        if (size == peopleSum) {
            logger.info(">>>>排序已满员");
            throw new BussinessException("已满员");
        }
        ArrayList<Integer> sortList = Lists.newArrayList();
        for (int i = 0; i < peopleSum; i++) {
            int finalI = i + 1;
            if (!sortDetailList.stream().anyMatch(p -> p.getSort() == finalI)) {
                sortList.add(finalI);
            }
        }
        int random = new Random().nextInt(sortList.size());
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        SortDetail sortDetail = new SortDetail();
        sortDetail.setSujectInfoId(subjectId);
        sortDetail.setUserId(userId);
        if (subjectInfoVO.getNeedName()) {
            sortDetail.setUserName(sysUser.getNickname());
        } else {
            sortDetail.setUserName(sysUser.getUserName());
        }
        sortDetail.setUserIamgeUrl(sysUser.getAvatar());
        sortDetail.setSort(sortList.get(random));
        sortDetail.setAddTime(new Date());
        sortDetail.setUpdateTime(new Date());
        sortDetail.setDeleted(false);
        int res = sortDetailMapper.insertSelective(sortDetail);
        return res;
    }

    //发送订阅消息
    public void sendMsg(Integer subjectId) {
        SubjectInfoVO infoVO = sujectInfoMapper.getSubjectInfoVO(subjectId);
        List<SortDetail> details = infoVO.getSortDetailList();
        List<GroupDetail> groupDetailList = infoVO.getGroupDetailList();
        if ((Collections3.isNotEmpty(details) && details.size() == infoVO.getPeopleSum())
                || (Collections3.isNotEmpty(groupDetailList) && groupDetailList.size() == infoVO.getPeopleSum())) {
            List<Integer> userIdList = details.stream().map(SortDetail::getUserId).collect(Collectors.toList());
            List<Integer> userIdList2 = groupDetailList.stream().map(GroupDetail::getUserId).collect(Collectors.toList());
            userIdList.addAll(userIdList2);
            for (Integer so : userIdList) {
                SysUser sysUser = sysUserMapper.selectByPrimaryKey(so);
                WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
                wxMaSubscribeMessage.setToUser(sysUser.getWeixinOpenid());
                wxMaSubscribeMessage.setTemplateId("xB8PAlZ9Qc1-USZFaKV2tKBM9ak6tTe4GISXvwSuFJc");
                if (infoVO.getType().equals(0l)) {
                    wxMaSubscribeMessage.setPage("/pages/group/group?subjectId=" + infoVO.getId());
                } else {
                    wxMaSubscribeMessage.setPage("/pages/sort/sort_detail?subjectId=" + infoVO.getId());
                }
                ArrayList<WxMaSubscribeMessage.Data> dataArrayList = Lists.newArrayList();
                dataArrayList.add(new WxMaSubscribeMessage.Data("thing1", infoVO.getName()));
                dataArrayList.add(new WxMaSubscribeMessage.Data("thing2", "已全员到齐"));
                wxMaSubscribeMessage.setData(dataArrayList);
                wxMaSubscribeMessage.setMiniprogramState(WxMaConstants.MiniprogramState.DEVELOPER);
                try {
                    wxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
                } catch (WxErrorException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public int addGroupDetail(Integer subjectId, Integer userId) throws BussinessException {
        SubjectInfoVO subjectInfoVO = sujectInfoMapper.getSubjectInfoVO(subjectId);
        List<GroupDetail> groupDetailList = subjectInfoVO.getGroupDetailList();
        if (groupDetailList.stream().anyMatch(p -> p.getUserId() == userId)) {
            logger.info(">>>>已参加,不能重复参加");
            throw new BussinessException("不能重复参加");
        }
        //没有分配的坑位
        List<GroupDetail> groupDetails = groupDetailList.stream().filter(p -> p.getUserId() == null || p.getUserId() == 0).collect(Collectors.toList());
        Integer size = Optional.ofNullable(groupDetails.size()).orElse(0);
        int peopleSum = subjectInfoVO.getPeopleSum();
        if (size == 0) {
            logger.info(">>>>排序已满员");
            throw new BussinessException("已满员");
        }

        int random = new Random().nextInt(groupDetails.size());
        GroupDetail groupDetail = groupDetails.get(random);

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        groupDetail.setUserId(userId);
        groupDetail.setUserIamgeUrl(sysUser.getAvatar());

        if (subjectInfoVO.getNeedName()) {
            groupDetail.setUserName(sysUser.getUserName());
        } else {
            groupDetail.setUserName(sysUser.getNickname());
        }
        groupDetail.setUpdateTime(new Date());
        return groupDetailMapper.updateByPrimaryKeySelective(groupDetail);
    }

    public String groupExprot(Integer subjectId) {
        //【hhhh】
        //【总人数 9】
        //【分组组数 3】
        //【未参与人数 8】
        //【分组 1：】
        //【分组 2：】
        //【分组 3：陈小飞 】
        SubjectInfoVO vo = sujectInfoMapper.getSubjectInfoVO(subjectId);
        int size = vo.getGroupDetailList().stream().filter(p -> p.getUserId().equals(0)).collect(Collectors.toList()).size();
        StringBuffer builder = new StringBuffer();
        builder.append("【").append(vo.getName()).append("】").append("\n");
        builder.append("【").append("总人数").append(vo.getPeopleSum()).append("】").append("\n");
        builder.append("【").append("分组组数").append(vo.getGroupSum()).append("】").append("\n");
        builder.append("【").append("未参与人数").append(size).append("】").append("\n");
        for (int i = 0; i < vo.getGroupSum(); i++) {
            int groupNumber = i + 1;
            String groupUserName = vo.getGroupDetailList().stream()
                    .filter(p -> p.getGroupNumber().equals(groupNumber))
                    .filter(p -> StringUtil.isNotBlank(p.getUserName()))
                    .map(GroupDetail::getUserName).collect(Collectors.joining(","));
            builder.append("【").append("分组").append(groupNumber).append(": ").append(groupUserName).append("】").append("\n");
        }
        return builder.toString();
    }


    public List<SubjectInfoVO> getMyCreateSubject(Integer userId) {
        Example example = new Example(SujectInfo.class);
        example.createCriteria().andEqualTo("addUserId", userId).andEqualTo("deleted", false);
        List<SujectInfo> sujectInfoList = sujectInfoMapper.selectByExample(example);

        List<SubjectInfoVO> subjectInfoVOList = sujectInfoList.stream().map(p -> {
            SubjectInfoVO subjectInfoVO = new SubjectInfoVO();
            BeanUtils.copyProperties(p, subjectInfoVO);
            if (p.getType().equals(0l)) {
                subjectInfoVO.setDetailUrl("/pages/group/group?subjectId=" + p.getId());
            } else {
                subjectInfoVO.setDetailUrl("/pages/sort/sort_detail?subjectId=" + p.getId());
            }
            return subjectInfoVO;
        }).collect(Collectors.toList());
        return subjectInfoVOList;
    }

    public List<SubjectInfoVO> getMyJoinSubject(Integer userId) {
        ArrayList<Integer> subjectIdList = Lists.newArrayList();

        Example exampleGroupDetail = new Example(GroupDetail.class);
        exampleGroupDetail.createCriteria().andEqualTo("userId", userId);
        List<GroupDetail> groupDetailList = groupDetailMapper.selectByExample(exampleGroupDetail);
        List<Integer> groupSubIdList = groupDetailList.stream().map(GroupDetail::getSujectInfoId).collect(Collectors.toList());
        subjectIdList.addAll(groupSubIdList);

        Example exampleSortDetail = new Example(SortDetail.class);
        exampleSortDetail.createCriteria().andEqualTo("userId", userId);
        List<SortDetail> sortDetailList = sortDetailMapper.selectByExample(exampleSortDetail);
        List<Integer> sortSubIdList = sortDetailList.stream().map(SortDetail::getSujectInfoId).collect(Collectors.toList());
        subjectIdList.addAll(sortSubIdList);

        Example example = new Example(SujectInfo.class);
        example.createCriteria().andIn("id", subjectIdList).andEqualTo("deleted", false);
        List<SujectInfo> sujectInfoList = sujectInfoMapper.selectByExample(example);

        List<SubjectInfoVO> subjectInfoVOList = sujectInfoList.stream().map(p -> {
            SubjectInfoVO subjectInfoVO = new SubjectInfoVO();
            BeanUtils.copyProperties(p, subjectInfoVO);
            if (p.getType().equals(0l)) {
                subjectInfoVO.setDetailUrl("/pages/group/group?subjectId=" + p.getId());
            } else {
                subjectInfoVO.setDetailUrl("/pages/sort/sort_detail?subjectId=" + p.getId());
            }
            return subjectInfoVO;
        }).collect(Collectors.toList());

        return subjectInfoVOList;
    }

    public String sortExprot(Integer subjectId) {
        SubjectInfoVO vo = sujectInfoMapper.getSubjectInfoVO(subjectId);
        List<SortDetail> sortDetailList = vo.getSortDetailList();
        vo.setJoinSum(Optional.ofNullable(sortDetailList.size()).orElse(0));
        //【dddd】
        //【1/2 张卡片被抽取】
        //【陈小飞  04-07 00:58:43 第 2 位】
        StringBuffer builder = new StringBuffer();
        builder.append("【").append(vo.getName()).append("】").append("\n");
        builder.append("【").append(vo.getJoinSum()).append("/").append(vo.getPeopleSum()).append("张卡片被抽取").append("】").append("\n");
        sortDetailList = sortDetailList.stream().sorted(new Comparator<SortDetail>() {
            @Override
            public int compare(SortDetail o1, SortDetail o2) {
                if (o1.getSort() > o2.getSort()) {
                    return 1;
                } else if (o1.getSort() < o2.getSort()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }).collect(Collectors.toList());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (SortDetail sortDetail : sortDetailList) {
            builder.append("【").append(sortDetail.getUserName())
                    .append(" ")
                    .append(dateFormat.format(sortDetail.getAddTime()))
                    .append(" ")
                    .append("第").append(sortDetail.getSort()).append("名】").append("\n");
        }
        return builder.toString();
    }

    public int deleteSubject(Integer subjectId) {
        SujectInfo sujectInfo = new SujectInfo();
        sujectInfo.setId(subjectId);
        sujectInfo.setDeleted(true);
        return sujectInfoMapper.updateByPrimaryKeySelective(sujectInfo);
    }

}
