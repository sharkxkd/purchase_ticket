package com.sharkxkd.ticket.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;


import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Redis实现分布式锁
 *
 * @author zc
 * @date 2024/11/21 16:45
 **/
public class RedisLock {
    private StringRedisTemplate stringRedisTemplate;
    private String key;
    private static final String LOCK_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID() + "-";
    public RedisLock(StringRedisTemplate stringRedisTemplate,String name){
        this.key = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static{
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("lua/unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
    /**
     * 构建分布式锁，并且设置过期时间
     * 锁的key为锁前缀+对应业务，value为一串随机id+当前线程id保证全局唯一
     * @param timeSec   过期时间
     * @return          返回是否成功获取锁
     */
    public boolean tryLock(Long timeSec){
        String threadId =  ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(LOCK_PREFIX + key, threadId, timeSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    /**
     * 使用lua脚本实现原子操作，防止在判断和删除之间，锁被其他线程释放
     */
    public void unLock(){
        stringRedisTemplate.execute(UNLOCK_SCRIPT,
                Collections.singletonList(LOCK_PREFIX + key),
                ID_PREFIX + Thread.currentThread().getId());
    }
}
