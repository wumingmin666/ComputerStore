package com.example.computerstore.service.impl;

import com.example.computerstore.entity.District;
import com.example.computerstore.mapper.DistrictMapper;
import com.example.computerstore.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @param
 * @return
 */
@Service
public class DistrictServiceImpl implements IDistrictService {
    @Autowired
    private DistrictMapper districtMapper;
    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        for (District d:list) {
            d.setParent(null);
            d.setId(null);
        }
        return list;
    }
}
