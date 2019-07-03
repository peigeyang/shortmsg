package com.qbb.shortmsg.shortmsg.controller;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.service.UserService;
import com.qbb.shortmsg.shortmsg.smsdemo.SmsDemo;
import com.qbb.shortmsg.shortmsg.util.RedisUtil;
import com.qbb.shortmsg.shortmsg.util.ReturnObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class
loginController {

    @Autowired
    private SmsDemo smsMsg;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;


    /**
     * 获得验证码
     * @param
     * @return
     */
    @PostMapping(value="/shortMsg",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @SuppressWarnings("all")
    public ReturnObject shortMsg(@RequestBody  Map mapModel){
        String phone = mapModel.get("phone").toString();
        String status = mapModel.get("status").toString();
//        if(userService.verifyUser(phone)!=null){
//            return new ReturnObject(ReturnObject.REGISTER_ERROR,ReturnObject.REGISTER_MSG,userService.verifyUser(phone));
//        }
        ReturnObject returnObject = smsMsg.sendMsg(phone,status);
        if(returnObject.getStatusCode().equals(ReturnObject.SUCCESS)){
            return returnObject;
        }else
            return returnObject;
    }

    @RequestMapping(value="/find")
    @ResponseBody
    public Object findUesr() throws Exception{
      // return userService.registerUser(new User("15652151371"));
        return null;
    }


    /**
     * 注册
     * @param phone
     * @param code
     * @return
     */
    @PostMapping(value="/register",produces = MediaType.APPLICATION_JSON_VALUE)
    @SuppressWarnings("all")
    @ResponseBody
    public ReturnObject register(@RequestBody Map params){
        String phone =  params.get("phone").toString();
        String shortCode = params.get("shortCode").toString();
        String password = params.get("password").toString();
        String phoneCode = (String) redisUtil.get(RedisUtil.SHORTRE_REGISTER_MSG + phone);
        if(userService.verifyUser(phone)!=null){
            return new ReturnObject(ReturnObject.REGISTER_ERROR,ReturnObject.REGISTER_MSG,userService.verifyUser(phone));
        }
        if(phoneCode==null&&!shortCode.equals(phoneCode)){
            return new ReturnObject(ReturnObject.SHORTMSG,ReturnObject.SHORTMSG_MSG,"");
        }
        return  userService.registerUser(new User(phone,password));
    }




}
