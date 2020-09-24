package com.jiashi.dao;

import com.jiashi.entity.Archive_Detail;

import java.util.List;

public interface Archive_DetailMapper {
    int insertSelective(Archive_Detail record);

    int updateByPrimaryKeySelective(Archive_Detail record);

    List<Archive_Detail> selectAllByMasterIdAndFileFlag(Archive_Detail record);
}