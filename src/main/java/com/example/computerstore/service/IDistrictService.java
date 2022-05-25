package com.example.computerstore.service;

import com.example.computerstore.entity.District;

import java.util.List;

/**
 * @param
 * @return
 */
public interface IDistrictService {
    /**
     * 根据父代号来查询区域信息
     * @param parent 父代号
     * @return 多个区域的信息
     */
    List<District> getByParent(String parent);
}
