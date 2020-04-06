package com.fei.db.dao;

import com.fei.db.entity.po.SujectInfo;
import com.fei.db.entity.vo.SubjectInfoVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SujectInfoMapper extends Mapper<SujectInfo> {

    SubjectInfoVO getSubjectInfoVO(Integer sujectId);




}