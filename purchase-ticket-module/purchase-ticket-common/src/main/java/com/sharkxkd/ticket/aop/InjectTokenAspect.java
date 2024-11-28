package com.sharkxkd.ticket.aop;


import com.sharkxkd.ticket.annotation.InjectToken;
import com.sharkxkd.ticket.entity.TokenMsg;
import com.sharkxkd.ticket.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zc
 * @date 2024/11/28 21:08
 */
@Aspect
@Component
public class InjectTokenAspect {
    @Around("@annotation(injectToken)")
    public Object injectToken(ProceedingJoinPoint joinPoint, InjectToken injectToken) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Class[] classes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        for (int i=0; i<classes.length; i++){
            if (classes[i].equals(TokenMsg.class)){
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                TokenMsg tokenMsg = JWTUtils.parseTokenMsg(request);
                args[i] = tokenMsg;
                break;
            }
        }
        return joinPoint.proceed(args);
    }
}


























