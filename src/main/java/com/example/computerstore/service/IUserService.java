package com.example.computerstore.service;

import com.example.computerstore.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;

/**
 用户模块业务层接口
 */
public interface IUserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 用户密码
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username,String password);

    /**
     * 修改密码
     * @param uid 用户的uid
     * @param username 用户名
     * @param oldPassword 原来的密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据u用户id查询用户的数据
     * @param uid 用户的id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据操作
     * @param user 用户对象数据
     * @param uid 用户的id
     * @param username 用户的名称
     */
    void changeInfo(Integer uid,User user,String username);

    /**
     * 修改用户头像
     * @param uid 用户的id
     * @param avatar 用户头像路径
     * @param username 用户的名称
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
