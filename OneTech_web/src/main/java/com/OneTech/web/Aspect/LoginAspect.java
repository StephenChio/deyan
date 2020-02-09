package com.OneTech.web.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Pointcut("execution( * com.OneTech.web.controller.mainController.login(*))")
    public void login() {

    }
    @Pointcut("execution( * com.OneTech.web.controller..*.*(*))")
    public void action() {

    }

    @Before("login()")
    public void beforeLogin(JoinPoint joinPoint) {
        System.out.println("before");
    }

    //在本类的login执行之后执行
    @After("login()")
    public void afterLogin(JoinPoint joinPoint) {
        System.out.println("after");
    }

    @Before("action()")
    public void beforeAction(JoinPoint joinPoint) {
        System.out.println("before");
    }

    //在本类的login执行之后执行
    @After("action()")
    public void afterAction(JoinPoint joinPoint) {
        System.out.println("after");
    }

}
