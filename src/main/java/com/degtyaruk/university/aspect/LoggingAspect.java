package com.degtyaruk.university.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("com.degtyaruk.university.aspect.Pointcuts.allDaoAndServiceMethods()")
    public void beforeAllDaoMethods(JoinPoint joinPoint) {
        log.trace("Invocation Dao method {} from: {}", joinPoint.getSignature(), joinPoint.getTarget().getClass());
    }

    @AfterThrowing(pointcut = "com.degtyaruk.university.aspect.Pointcuts.allValidateMethods()", throwing = "ex")
    public void afterThrowingValidateAdvice(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in {}.{}()", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), ex);
    }

    @Around("com.degtyaruk.university.aspect.Pointcuts.allAddAndUpdateDaoMethods()")
    public Object aroundAddAndUpdateDaoMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("Executing operation {}() with following entity {}",
                    proceedingJoinPoint.getSignature().getName(), Arrays.toString(proceedingJoinPoint.getArgs()));
        }
        Object targetMethodResult = proceedingJoinPoint.proceed();
        log.info("Success operation {}()", proceedingJoinPoint.getSignature().getName());
        return targetMethodResult;
    }
}

