package com.eco.common.redis.utils;

import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 *  Bcro
 */
@Component
public class RedissonUtils {

    private final static Long DEFAULT_WAITTIME = 30L;

    @Autowired
    private RedissonClient redissonClient;

    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    public RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }


    public void lockExecutor(String lockKey, Runnable fun){
        lockKey = getLockKey(lockKey);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(DEFAULT_WAITTIME, -1L, TimeUnit.SECONDS)) {
                fun.run();
                return;
            }
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKey));
        } catch (InterruptedException e) {
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKey));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 加锁 执行
     *
     * @param lockKey
     * @param fun
     * @param <T>
     * @return
     */
    public <T> T lockExecutor(String lockKey, Supplier<T> fun) {
        lockKey = getLockKey(lockKey);
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (lock.tryLock(DEFAULT_WAITTIME, -1L, TimeUnit.SECONDS)) {
                return fun.get();
            }
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKey));
        } catch (InterruptedException e) {
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKey));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 联锁 执行
     *
     * @param lockKeys
     * @param fun
     * @param <T>
     * @return
     */
    public <T> T multiLockExecutor(List<String> lockKeys, Supplier<T> fun) {
        RLock[] rLocks = lockKeys.stream().map(x -> redissonClient.getLock(getLockKey(x))).toArray(RLock[]::new);
        RedissonMultiLock lock = new RedissonMultiLock(rLocks);
        try {
            if (lock.tryLock(DEFAULT_WAITTIME, -1L, TimeUnit.SECONDS)) {
                return fun.get();
            }
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKeys.toString()));
        } catch (InterruptedException e) {
            throw new RuntimeException(String.format("lock:%s获取锁失败", lockKeys.toString()));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 封装Key
     *
     * @param lockKey
     * @return
     */
    private String getLockKey(String lockKey) {
        return String.format("lock:%s", lockKey);
    }
}
