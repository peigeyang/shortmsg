package com.qbb.shortmsg.shortmsg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public final static String SHORTRE_REGISTER_MSG = "srm";
    public final static String SHORTRE_LOGIN_MSG = "slm";

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean expire(String key, long time) {

        try {

            if (time > 0) {

                redisTemplate.expire(key, time, TimeUnit.SECONDS);

            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }



    }

    public boolean hasKey(String key) {

        try {

            return redisTemplate.hasKey(key);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }

    public void delete(String key){

        redisTemplate.delete(key);
    }
}
