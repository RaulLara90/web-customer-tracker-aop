package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
    public void forControllerPackage() {
    }

    @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
    public void forServicePackage() {
    }

    @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
    public void forDaoPackage() {
    }

    @Pointcut("forControllerPackage() ||forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    private void before(JoinPoint theJoinPoint) {
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====> in @Before: calling method: " + theMethod);
        Arrays.stream(theJoinPoint.getArgs()).forEach(s -> myLogger.info("====> argument: " + s));
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult){
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====> in @AfterReturning: from method: " + theMethod);
        myLogger.info("=====> result: " + theResult);

    }
}

