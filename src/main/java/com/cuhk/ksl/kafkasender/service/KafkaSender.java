package com.cuhk.ksl.kafkasender.service;


import com.alibaba.fastjson.JSON;
import com.cuhk.ksl.kafkasender.vo.KafkaProducerMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class KafkaSender {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    public KafkaSender(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(KafkaProducerMsg msg,String topic){
        log.info("----------send message to topic :{}-------------",topic);
        String message = JSON.toJSONString(msg);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic,message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable){
                log.error("--------------Fail in sending user:{}'s message,ex:{},topic:{}",msg.getUser(),throwable,topic);
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info("-----------------success in sending user:{}'s message, at time :{},topic:{},partition:{} -----------------------"
                        ,msg.getUser(),msg.getTimestamp(),stringStringSendResult.getRecordMetadata().topic(),stringStringSendResult.getRecordMetadata().partition());
            }
        });

    }
}
