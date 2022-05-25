package com.example.computerstore.service.impl;

import com.example.computerstore.entity.Address;
import com.example.computerstore.mapper.AddressMapper;
import com.example.computerstore.service.IAddressService;
import com.example.computerstore.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 新增收货地址的实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收货地址统计的方法
        Integer count=addressMapper.countByUid(uid);
        if(count>=maxCount){
            throw new AddressCountLimitException("用户的地址数量达到上限");
        }
        //补全uid,isDelete
        address.setUid(uid);
        Integer isDefault=count == 0 ? 1 : 0;//1表示默认地址
        address.setIsDefault(isDefault);
        //补全4项日志
        address.setModifiedTime(new Date());
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setCreatedUser(username);

        //插入收货地址
        Integer rows=addressMapper.insert(address);
        if(rows!=1){
            throw new InsertException("插入用户的收货地址产生未知异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list=addressMapper.findByUid(uid);
        for (Address address:list) {
            address.setCreatedUser(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
            address.setCreatedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer uid, Integer aid, String username) {
        Address result=addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("该地址不存在");
        }
        Integer rows=addressMapper.updateNonDefault(uid);
        if(rows<1){
            throw new UpdateException("更新异常");
        }
        Integer re=addressMapper.updateDefault(aid,username,new Date());
        if(re!=1){
            throw new UpdateException("更改出现未知异常");
        }
    }

    @Override
    public void deleteAddress(Integer uid, Integer aid,String username) {
        Address result=addressMapper.findByAid(aid);
        if(result==null){
            throw new AddressNotFoundException("该地址不存在");
        }
        if(!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows=addressMapper.deleteByAid(aid);
        if(rows!=1){
            throw new DeleteException("删除数据出现未知异常");
        }
        Integer count=addressMapper.countByUid(uid);
        if(count==0){
            return;
        }
        Address lastModifiedAddress=addressMapper.findLastModified(uid);
        Integer newAid=lastModifiedAddress.getAid();
        addressMapper.updateDefault(newAid,username,new Date());
        if(rows!=1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }
}
