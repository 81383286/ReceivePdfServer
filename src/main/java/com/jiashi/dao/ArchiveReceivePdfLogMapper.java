package com.jiashi.dao;

import com.jiashi.entity.FileInfo;

import java.util.List;

public interface ArchiveReceivePdfLogMapper {
    int insertSelective(FileInfo record);

    int updateByPrimaryKeySelective(FileInfo record);

    List<FileInfo> selectAllByError();
}
