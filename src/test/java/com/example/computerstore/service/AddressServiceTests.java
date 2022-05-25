package com.example.computerstore.service;

import com.example.computerstore.entity.Address;
import com.example.computerstore.entity.User;
import com.example.computerstore.service.ex.ServiceException;
import com.example.computerstore.service.impl.AddressServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRuner的实例类型
@RunWith(SpringRunner.class)
@MapperScan("com.example.computerstore.mapper")
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;
    @Test
    public void addNewAddress(){
        Address address=new Address();
        address.setUid(39);
        address.setPhone("222");
        address.setName("nnndsafnn");
        addressService.addNewAddress(39,"管理员",address);
    }
    @Test
    public void update(){
        addressService.deleteAddress(39,5,"管理员");
    }
}
