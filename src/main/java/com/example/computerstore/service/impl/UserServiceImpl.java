package com.example.computerstore.service.impl;

import com.example.computerstore.entity.User;
import com.example.computerstore.mapper.UserMapper;
import com.example.computerstore.service.IUserService;
import com.example.computerstore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 用户模块业务层实现
 */
@Service//@Service注解：将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void reg(User user) {
        //通过User参数来获取传递过来的username
        String username = user.getUsername();

        //调用findByUsername(username)判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null,不为null则抛出用户名被占用的异常
        if (result != null) {
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //密码加密处理的实现：md5算法
        //串+password+串--------》md5算法加密,连续加密三次
        //盐值+password+盐值-------盐值就是一个随机的字符串
        String oldPassword=user.getPassword();
        //获取盐值
        String salt=UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密,忽略原有密码强度提升了数据的安全性
        String md5Password=getMD5Password(oldPassword,salt);
        //将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        //补全数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date =new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        user.setAvatar("../images/index/user.png");//默认图像的路径
        //执行注册业务功能的实现(rows==1时正常)
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }


    @Override
    public User login(String username, String password) {
        //根据用户名称来查询用户是否存在，如果不在则抛出异常
        User result=userMapper.findByUsername(username);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户密码是否匹配
        //1、先获取到数据库的加密之后的的密码
        String oldPassword=result.getPassword();
        //2、和用户传递过来的密码进行比较
        //2.1、先获取盐值：上一次载注册时所自动生成的盐值
        String salt=result.getSalt();
        //2.2、将用户的密码按照相同的md5算法的规则加密
        String newMd55Password=getMD5Password(password,salt);
        //3、将密码进行比较
        if(!newMd55Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断is_delete字段的值是否为1表示被标记为删除
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //减少数据量提高性能
        User user=new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        //将当前的用户数据返回，返回的数据是为了辅助其它页面做数据展示使用
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result=userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中的密码进行比较
        String oldMd5Password=getMD5Password(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新密码设置大数据库中，将新密码进行加密再去更新
        String newMd5Password=getMD5Password(newPassword,result.getSalt());
        Integer rows=userMapper.updatePasswordByUid(uid,newMd5Password,result.getUsername(),new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result=userMapper.findByUid(uid);
        if(result==null ||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        User user=new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setGender(result.getGender());
        user.setEmail(result.getEmail());
        return user;
    }

    /**
     *user对象中的数据phone\email\gender,手动再将uid、username封装user对象中
     */
    @Override
    public void changeInfo(Integer uid, User user, String username) {
        User result=userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows=userMapper.updateInfoByUid(user);
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }

    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前用户的数据是否存在
        User result=userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }

        Integer rows=userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新用户头像时产生未知的异常");
        }
    }


    /**
     * 定义一个md5算法的加密处理
     */
    public String getMD5Password(String password,String salt){
        //md5加密算法方法的调用（加密三次）
        for(int i=0; i<3;i++) {
            password=DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
