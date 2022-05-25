package com.example.computerstore.controller;

import com.example.computerstore.entity.Address;
import com.example.computerstore.service.IAddressService;
import com.example.computerstore.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @param
 * @return
 */
@RestController
@RequestMapping("/addresses")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }
    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        Integer uid=getuidFromSession(session);
        List<Address> list=addressService.getByUid(uid);
        return new JsonResult<>(OK,list);
    }
    @RequestMapping("/set_default/{aid}")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){
        Integer uid=getuidFromSession(session);
        String username=getUsernameFromSession(session);
        addressService.setDefault(uid,aid,username);
        return new JsonResult<>(OK);
    }
    @RequestMapping("/delete/{aid}")
    public JsonResult<Void> deleteAddress(@PathVariable("aid") Integer aid,HttpSession session){
        addressService.deleteAddress(getuidFromSession(session),aid,getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
