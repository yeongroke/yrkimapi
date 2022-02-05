package com.yrkim.yrkimapi.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
public class ServiceAspect {

    @Around("execution( * com.yrkim.yrkimapi.controller.*.*(..)) || execution( * com.yrkim.yrkimapi.service.*.*(..))")
    public Object doAroundRequest(ProceedingJoinPoint point) throws Throwable {
        long start = System.currentTimeMillis();
        log.debug("This : '" + point.getThis() + "'");
        log.debug("Kind : '" + point.getKind() + "'");
        log.debug("Args : '" + point.getArgs() + "'");
        log.debug("Target: '" + point.getTarget() + "'");

        long finish = System.currentTimeMillis();
        long timeMs = finish - start;
        log.debug("[ " + point.getSignature().getDeclaringTypeName() + " ] :" + point.getSignature().getName() + "()'");
        log.debug("Doing Time - " + timeMs/1000 + " Second");
        return point.proceed();
    }
}
