//package com.OneTech.common.config.mqconfig;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @author Gjing
// **/
//@Component
//public class Producer {
//
//    @Resource
//    private AmqpTemplate rabbitTemplate;
//
//    public void send() {
//        String message = "hello";
//        System.out.println("推送到消息队列");
//        this.rabbitTemplate.convertAndSend("simple", message);
//    }
//}