package com.example.computerstore.mapper;

import com.example.computerstore.entity.District;

import java.util.List;

/**
 * @param
 * @return
 */
public interface DistrictMapper {
    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区列表
     */
    List<District> findByParent(String parent);
}
