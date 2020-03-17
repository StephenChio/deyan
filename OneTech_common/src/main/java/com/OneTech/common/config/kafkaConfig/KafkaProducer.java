package com.OneTech.common.config.kafkaConfig;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //发送消息方法
    public void send(JSONObject jsonObject,String topic) {
            logger.info("发送消息到"+topic+"队列 ----->>>>>  message = {}",jsonObject);
            kafkaTemplate.send(topic, jsonObject.toString());
        }
}


