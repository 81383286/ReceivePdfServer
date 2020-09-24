package com.jiashi.dao;

import com.jiashi.entity.Archive_Master;

import java.util.List;

public interface Archive_MasterMapper {
    Archive_Master selectByPrimaryKey(String id);

    List<Archive_Master> selectAllByPatientId(Archive_Master master);
}