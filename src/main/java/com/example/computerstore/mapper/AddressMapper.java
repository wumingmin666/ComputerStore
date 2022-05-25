package com.example.computerstore.mapper;

import com.example.computerstore.entity.Address;

import java.util.Date;
import java.util.List;

/**
 收货地址持久层的接口
 */
public interface AddressMapper {
    /**
     * 插入用户的收货地址数据
     * @param address 收货地址数据
     * @return 受影响行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址数量
     * @param uid 用户的id
     * @return 当前用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据id查询用户收货地址数据
     * @param uid 用户的id
     * @return 用户的收货地址的集合
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据收货地址id查询地址数据
     * @param aid 收货地址id
     * @return 收货地址
     */
    Address findByAid(Integer aid);

    /**
     * 根据uid将其所有收货地址改为非默认（is_default=0）
     * @param uid 收货地址id
     * @return 返回收影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据aid将该收货地址设置为默认（is_default=1）
     * @param aid 收货地址id
     * @param modifiedUser 修改用户名称
     * @param modifiedTime 修改时间
     * @return 收影响的行数
     */
    Integer updateDefault(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 工具aid删除收货地址数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据uid寻找最近被修改的收货地址
     * @param uid 用户id
     * @return收货地址
     */
    Address findLastModified(Integer uid);
}
