package com.OneTech.common.config.kafkaConfig;

import java.util.Optional;

import com.OneTech.common.util.massageUtils.MassageUitls;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMassageReceiver {
    @Autowired
    MassageUitls massageUitls;

    private static Logger logger = LoggerFactory.getLogger(KafkaMassageReceiver.class);

    @KafkaListener(topics = {"message"})
    public void listen(ConsumerRecord<?, ?> record) {
        JSONObject jsonObject = JSONObject.parseObject((String)record.value());
        logger.info("----------------- 监听到消息" + record.value());
        massageUitls.sendMassageToSingle(jsonObject);
        logger.info("----------------- 发送短信成功");
    }
}










