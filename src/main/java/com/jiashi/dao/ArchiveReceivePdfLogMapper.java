package com.jiashi.dao;

import com.jiashi.entity.FileInfo;

public interface ArchiveReceivePdfLogMapper {
    int insertSelective(FileInfo record);

    int updateByPrimaryKeySelective(FileInfo record);
}
