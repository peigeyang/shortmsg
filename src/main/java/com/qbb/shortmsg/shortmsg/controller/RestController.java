package com.qbb.shortmsg.shortmsg.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.util.ReturnObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.HashSet;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/loginGo")
    public ReturnObject indexHome(){
        return new ReturnObject("100008","未登录,请先登录系统!","");
    }

    @PostMapping(value = "/loginGo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ReturnObject login(@RequestBody User user) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();

        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(), user.getPassWord());
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return new ReturnObject("100001","未知账户","");
        } catch (IncorrectCredentialsException ice) {
            return new ReturnObject("100002","密码不正确","");
        } catch (LockedAccountException lae) {
            return new ReturnObject("100003","账户已锁定","");
        } catch (ExcessiveAttemptsException eae) {
            return new ReturnObject("100004","用户名或密码错误次数过多","");
        } catch (AuthenticationException ae) {
            return new ReturnObject("100005","用户名或密码不正确！","");
        }
        if (subject.isAuthenticated()) {
            return  new ReturnObject("100006","登录成功","");
        } else {
            token.clear();
            return new ReturnObject("100007","登录失败",new HashMap<>().put("token",subject.getSession().getId()));
        }
    }
}
