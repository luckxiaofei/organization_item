package com.fei.wx.service;

import java.util.Date;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
            int surplusSum = sujectInfo.getPeopleSum() / sujectInfo.getGroupSum();
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
        boolean isJoin = isJoin(subjectId, userId);
        subjectInfoVO.setIsJoin(isJoin);
        if (isJoin && subjectInfoVO.getType().equals(0l)) {
            SortDetail sortDetail = subjectInfoVO.getSortDetailList().stream().filter(p -> p.getUserId().equals(userId)).findFirst().get();
            subjectInfoVO.setSortNumber(sortDetail.getSort());
            subjectInfoVO.setJoinSum(Optional.ofNullable(subjectInfoVO.getSortDetailList().size()).orElse(0));
        } else if (isJoin && subjectInfoVO.getType().equals(1l)) {
            GroupDetail groupDetail = subjectInfoVO.getGroupDetailList().stream().filter(p -> p.getUserId().equals(userId)).findFirst().get();
            subjectInfoVO.setGroupNumber(groupDetail.getGroupNumber());
        }
        if (Collections3.isNotEmpty(subjectInfoVO.getGroupDetailList())) {
            Map<Integer, List<GroupDetail>> listMap = subjectInfoVO.getGroupDetailList().stream().collect(Collectors.groupingBy(GroupDetail::getGroupNumber));
            subjectInfoVO.setGroupDetailMap(listMap);
            subjectInfoVO.setGroupDetailList(Collections.EMPTY_LIST);
            List<GroupDetail> groupDetails = subjectInfoVO.getGroupDetailList().stream().filter(p -> p.getUserId() != null && p.getUserId() > 0).collect(Collectors.toList());
            subjectInfoVO.setJoinSum(Optional.ofNullable(groupDetails.size()).orElse(0));
            subjectInfoVO.setGroupMaxNumber(0);
            for (Integer key : listMap.keySet()) {
                int size = listMap.get(key).size();
                if (size>subjectInfoVO.getGroupMaxNumber()){
                    subjectInfoVO.setGroupMaxNumber(size);
                }
            }
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
        SubjectInfoVO subjectDetail = this.getSubjectDetail(subjectId, subjectId);
        List<SortDetail> sortDetailList = subjectDetail.getSortDetailList();
        if (sortDetailList.stream().anyMatch(p -> p.getUserId() != userId)) {
            logger.info(">>>>已参加,不能重复参加");
            throw new BussinessException("不能重复参加");
        }
        int peopleSum = subjectDetail.getPeopleSum();
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
        int random = new Random().nextInt(peopleSum);
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        SortDetail sortDetail = new SortDetail();
        sortDetail.setSujectInfoId(subjectId);
        sortDetail.setUserId(userId);
        sortDetail.setUserIamgeUrl(sysUser.getAvatar());
        sortDetail.setSort(sortList.get(random));
        sortDetail.setAddTime(new Date());
        sortDetail.setUpdateTime(new Date());
        sortDetail.setDeleted(false);
        int res = sortDetailMapper.insertSelective(sortDetail);
        return res;
    }

    public int addGroupDetail(Integer subjectId, Integer userId) throws BussinessException {
        SubjectInfoVO subjectDetail = this.getSubjectDetail(subjectId, subjectId);
        List<GroupDetail> groupDetailList = subjectDetail.getGroupDetailList();
        if (groupDetailList.stream().anyMatch(p -> p.getUserId() != userId)) {
            logger.info(">>>>已参加,不能重复参加");
            throw new BussinessException("不能重复参加");
        }
        int peopleSum = subjectDetail.getPeopleSum();
        Integer size = Optional.ofNullable(groupDetailList.size()).orElse(0);
        if (size == peopleSum) {
            logger.info(">>>>排序已满员");
            throw new BussinessException("已满员");
        }
        //没有分配的坑位
        List<GroupDetail> groupDetails = groupDetailList.stream().filter(p -> p.getUserId() == null || p.getUserId() == 0).collect(Collectors.toList());
        int random = new Random().nextInt(groupDetails.size());
        GroupDetail groupDetail = groupDetails.get(random);

        SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
        groupDetail.setUserId(userId);
        groupDetail.setUserIamgeUrl(sysUser.getAvatar());
        groupDetail.setUpdateTime(new Date());
        return groupDetailMapper.insertSelective(groupDetail);
    }


    public List<SujectInfo> getMyCreateSubject(Integer userId) {
        Example example = new Example(SujectInfo.class);
        example.createCriteria().andEqualTo("addUserId", userId);
        List<SujectInfo> sujectInfoList = sujectInfoMapper.selectByExample(example);
        return sujectInfoList;
    }

    public List<SujectInfo> getMyJoinSubject(Integer userId) {
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
        example.createCriteria().andIn("id", subjectIdList);
        List<SujectInfo> sujectInfoList = sujectInfoMapper.selectByExample(example);
        return sujectInfoList;
    }

}
