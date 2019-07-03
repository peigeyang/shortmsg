package com.qbb.shortmsg.shortmsg.service;


import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.mapper.UserMapper;
import com.qbb.shortmsg.shortmsg.util.RedisUtil;
import com.qbb.shortmsg.shortmsg.util.ReturnObject;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public ReturnObject registerUser(User user){
        String Id = UUID.randomUUID()+ RedisUtil.SHORTRE_REGISTER_MSG;
        user.setId(Id);
        user.setPassWord(new SimpleHash("md5", user.getPassWord(), user.getPhone(),2).toString());
        if (userMapper.add(user) > 0) {
            return new ReturnObject(ReturnObject.SUCCESS, "保存成功", user);
        }
        return new ReturnObject(ReturnObject.ERROR,"保存失败",user);
    }
    public User findUserByPhone(String phone){
        return  userMapper.getUserByPhone(phone);
    }

    public User verifyUser(String phone){
        User user = userMapper.getUserByPhone(phone);
        if(user==null){
            return null;
        }
        return user;
    }
}
