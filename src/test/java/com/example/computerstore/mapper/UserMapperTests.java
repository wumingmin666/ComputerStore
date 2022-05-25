package com.example.computerstore.mapper;

import com.example.computerstore.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

//@SpringBootTest:表示标注当前的类是一个测试类，不会随同项目一块打包
@SpringBootTest
//@RunWith：表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRuner的实例类型
@RunWith(SpringRunner.class)
@MapperScan("com.example.computerstore.mapper")
public class UserMapperTests {
    //idea有检测功能，接口不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法:就可以单独独立运行，不用启动整个项目，可以做单元测试，提升代码的测试效率
     * 1、必须被@Test让注解修饰
     * 2、返回值类型必须是void
     * 3、方法的参数列表不指定任何类型
     * 4、方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user=new User();
        user.setUsername("him");
        user.setPassword("123");
        Integer rows=userMapper.insert(user);
        System.out.println(rows);
    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }
    @Test
    public void updatePasswordByUid(){
        Integer re=userMapper.updatePasswordByUid(25,"888","管理员",new Date());
    }
    @Test
    public void findByUid(){
        User user=userMapper.findByUid(25);
        System.out.println(user);
    }
    @Test
    public void updateInfoByUid(){
        User user=new User();
        user.setUid(31);
        user.setPhone("15059997639");
        user.setEmail("2567029242@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }
    @Test
    public void updateAvatarByUid(){

        userMapper.updateAvatarByUid(31,"13223","管理员", new Date());
    }

}
