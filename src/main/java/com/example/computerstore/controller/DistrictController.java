package com.example.computerstore.controller;

import com.example.computerstore.entity.District;
import com.example.computerstore.service.IDistrictService;
import com.example.computerstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @param
 * @return
 */
@RequestMapping("districts")
@RestController
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data=districtService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
