package com.qbb.shortmsg.shortmsg;

import com.qbb.shortmsg.shortmsg.entity.User;
import com.qbb.shortmsg.shortmsg.util.MD5Util;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ShortmsgApplicationTests {

    @Test
    public void contextLoads() {
        String s = MD5Util.getPwd("123456");
        SimpleHash sim=new SimpleHash("md5", "123456", "13391277408",2);
        System.out.println(sim.toString());
    }

}
