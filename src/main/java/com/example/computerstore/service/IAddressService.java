package com.example.computerstore.service;

import com.example.computerstore.entity.Address;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @param
 * @return
 */
public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);
    List<Address> getByUid(Integer uid);
    void setDefault(Integer uid, Integer aid, String username);
    void deleteAddress(Integer uid,Integer aid,String username);
}
