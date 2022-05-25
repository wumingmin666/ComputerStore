package com.example.computerstore.mapper;

import com.example.computerstore.entity.Address;
import com.example.computerstore.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRuner的实例类型
@RunWith(SpringRunner.class)
@MapperScan("com.example.computerstore.mapper")
public class AddressMapperTests {
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address=new Address();
        address.setUid(39);
        address.setPhone("11111");
        address.setName("nnnnn");
        addressMapper.insert(address);
    }
    @Test
    public void countByUid(){
        Integer count=addressMapper.countByUid(39);
        System.out.println(count);
    }
    @Test
    public void findByUid(){
        List<Address> list=addressMapper.findByUid(39);
        System.out.println(list);
    }
    @Test
    public void update(){
        System.out.println(addressMapper.updateDefault(2,"管理员",new Date()));
    }

}
