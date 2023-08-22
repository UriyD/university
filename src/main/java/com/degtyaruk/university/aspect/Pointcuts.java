package com.degtyaruk.university.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* add(..))")
    public void allValidateMethods() {
    }

    @Pointcut("execution(* com.degtyaruk.university.dao.impl.*.*(..))")
    public void allDaoMethods(){
    }

    @Pointcut("execution(* com.degtyaruk.university.service.impl.*.*(..))")
    public void allServiceMethods(){
    }

    @Pointcut("allDaoMethods() || allServiceMethods()")
    public void allDaoAndServiceMethods() {
    }

    @Pointcut("execution(* com.degtyaruk.university.dao.impl.*.add(..))")
    public void allAddDaoMethods() {
    }

    @Pointcut("execution(* com.degtyaruk.university.dao.impl.*.update(..))")
    public void allUpdateDaoMethods() {
    }

    @Pointcut("allAddDaoMethods() || allUpdateDaoMethods()")
    public void allAddAndUpdateDaoMethods() {
    }
}

