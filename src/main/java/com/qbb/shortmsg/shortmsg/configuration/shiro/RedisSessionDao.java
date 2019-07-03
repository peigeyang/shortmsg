package com.qbb.shortmsg.shortmsg.configuration.shiro;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.qbb.shortmsg.shortmsg.util.RedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis实现共享session
 */

public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    // session 在redis过期时间是30分钟30*60
    private static int expireTime = 1800;

    private static String prefix = "weiyou-shiro-session:";

    @Autowired
    private RedisUtil redisUtil;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
        redisUtil.set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisUtil.get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        logger.debug("获取session:{}", session.getId());
        String key = prefix + session.getId().toString();
        if (!redisUtil.hasKey(key)) {
            redisUtil.set(key, session);
        }
        redisUtil.expire(key, expireTime);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
        redisUtil.delete(prefix + session.getId().toString());
    }
}
