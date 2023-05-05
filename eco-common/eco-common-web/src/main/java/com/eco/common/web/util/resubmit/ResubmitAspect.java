package com.eco.common.web.util.resubmit;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.eco.common.constant.GlobalConstants;
import com.eco.common.result.Result;
import com.eco.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author chenz
 * @title: ResubmitAspect
 * @description: 重复提交切面
 * @date 2021/9/24 16:33
 */
@Aspect
@Component
@Slf4j
public class ResubmitAspect {

    @Autowired
    private RedissonClient redissonClient;

    @Pointcut("@annotation(com.eco.common.web.util.resubmit.ResubmitCheck)")
    public void resubmitCheckAspect(){}

    /**
     * @description  在连接点执行之前执行的通知
     */
    @Around("resubmitCheckAspect()")
    public Object doAround( ProceedingJoinPoint proceedingJoinPoint) {

        Object resultVO = null;
        String key = getLockKey(getKey(proceedingJoinPoint));
        // 获取注解的超时时间
        ResubmitCheck resubmitCheck = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(ResubmitCheck.class);
        int expireTime = 2;
        if (resubmitCheck != null){
            expireTime = resubmitCheck.timeout();
        }

        RLock lock = redissonClient.getLock(key);
        try {
            if (lock.tryLock(0 , expireTime, TimeUnit.SECONDS)){
                try {
                    log.info("重复提交方法拦截器加锁,key:{}",key);
                    resultVO = proceedingJoinPoint.proceed();
                }catch (Throwable e) {
                    log.error("调用【{}-{}】方法，错误信息信息:{}",
                            getClassName(proceedingJoinPoint),
                            getClassFunctionName(proceedingJoinPoint),
                            e.getMessage());
                    lock.unlock();
                    resultVO = Result.failed(ResultCode.SYSTEM_EXECUTION_ERROR);
                }
            }else{
                resultVO = Result.failed(ResultCode.RESUBMIT_ERROR);
                log.error("重复提交");
            }
        }catch (Exception e) {
            log.error("lock error, {}", e.getMessage());
            lock.unlock();
            resultVO = Result.failed(ResultCode.SYSTEM_EXECUTION_ERROR);
        }
        return resultVO;
    }


    private String getClassName(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass().getName();
    }

    private String getClassFunctionName( JoinPoint joinPoint){
        return joinPoint.getSignature().getName();
    }

    private String getKey(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = getClassName(proceedingJoinPoint)
                + GlobalConstants.LINE + getClassFunctionName(proceedingJoinPoint);

        Object[] args = proceedingJoinPoint.getArgs();
        log.info("调用[{}]方法,参数:{}",methodName,args);
        Long userId = 0L;
        StringBuilder keyBuilder = new StringBuilder(String.valueOf(userId)).append(GlobalConstants.LINE).append(methodName);
        if (args.length > 0){
            for (int i = 0; i < args.length ; i++) {
                if(args[i] instanceof Errors){
                    continue;
                }
                if (!(args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse)){
                    keyBuilder.append(GlobalConstants.LINE).append(JSONUtil.toJsonStr(args[i])) ;
                }
            }
        }
        String sign = keyBuilder.toString();
        log.info("key:{}",sign);
        return SecureUtil.md5(sign);
    }


    private String getLockKey (String key) {
        return "lock:resubmit:" + key;
    }


}
