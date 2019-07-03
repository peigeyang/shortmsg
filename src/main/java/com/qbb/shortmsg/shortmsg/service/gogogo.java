package com.qbb.shortmsg.shortmsg.service;

import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
public class gogogo {

    @Autowired
    UserMapper userMapper;

    public void registerUser(){
      //  userMapper.add(new User("12356788"));
        int i = 1/0;
    }
}
