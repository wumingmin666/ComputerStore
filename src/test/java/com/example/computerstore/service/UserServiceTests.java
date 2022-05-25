package com.example.computerstore.service;

import com.example.computerstore.entity.User;
import com.example.computerstore.mapper.UserMapper;
import com.example.computerstore.service.ex.ServiceException;
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
public class UserServiceTests {
    //idea有检测功能，接口不能够直接创建Bean的（动态代理技术来解决）
    @Autowired(required = true)
    private IUserService userService;
    /**
     * 单元测试方法:就可以单独独立运行，不用启动整个项目，可以做单元测试，提升代码的测试效率
     * 1、必须被@Test让注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user=new User();
            user.setUsername("liming");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类对象，再获取类名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user=userService.login("liming","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(31,"444","789","444");
    }

    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(31));
    }
    @Test
    public void changeInfo(){
        User user=new User();
        user.setPhone("1385098");
        user.setEmail("26554897");
        user.setGender(0);
        userService.changeInfo(31,user,"管理员");
    }
    @Test
    public void changeAvatar(){
        userService.changeAvatar(31,"/ajdk/0","管理员");
    }


}
