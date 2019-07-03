package com.qbb.shortmsg.shortmsg.configuration.shiro;

import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


public class CustomRealm  extends AuthorizingRealm {

    
    @Autowired
    private UserService userService;
    

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        stringSet.add("student:list");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        String userName = (String) authenticationToken.getPrincipal();
        User userByPhone = userService.findUserByPhone(userName);
        String userPwd = new String((char[]) authenticationToken.getCredentials());
        //根据用户名从数据库获取密码
        if (userName == null) {
            throw new AccountException("用户名不正确");
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
        return new SimpleAuthenticationInfo(userName, userByPhone.getPassWord()
                ,ByteSource.Util.bytes(userName),getName());
    }
}
