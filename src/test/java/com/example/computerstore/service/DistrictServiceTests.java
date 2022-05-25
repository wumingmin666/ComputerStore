package com.example.computerstore.service;

import com.example.computerstore.entity.Address;
import com.example.computerstore.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRuner的实例类型
@RunWith(SpringRunner.class)
@MapperScan("com.example.computerstore.mapper")
public class DistrictServiceTests {
    @Autowired
    private IDistrictService districtService;
    @Test
    public void getParent(){
        List<District> list=districtService.getByParent("86");
        for (District d:list) {
            System.out.println(d);
        }
    }

}
