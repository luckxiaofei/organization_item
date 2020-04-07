package com.fei.db.entity.vo;

import com.fei.db.entity.po.GroupDetail;
import com.fei.db.entity.po.SortDetail;
import com.fei.db.entity.po.SujectInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SubjectInfoVO extends SujectInfo {
    private List<SortDetail> sortDetailList;
    private Integer sortNumber;

    private List<GroupDetail> groupDetailList;
    private Map<Integer, List<GroupDetail>> groupDetailMap;
    private Integer groupNumber;//当前人所在组
    private Integer groupMaxNumber;//组的最大人数



    private Boolean isJoin = false;
    private Integer joinSum;

    private Boolean realName;//实名

}
