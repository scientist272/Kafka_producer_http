package com.cuhk.ksl.kafkasender.service;

import com.alibaba.fastjson.JSON;
import com.cuhk.ksl.kafkasender.util.Cache;
import com.cuhk.ksl.kafkasender.vo.heartMsg.HeartMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.LinkedBlockingDeque;

@Component
@Slf4j
public class KafkaReciver {

    @KafkaListener(topics = "ksl-msg")
    public void listen(@Payload String record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partitionId) {
        Optional<?> optional = Optional.ofNullable(record);
        if (optional.isPresent()) {
            log.info("----------success in getting message " +
                    "from kafka topic:{},with partitionId:{}-----------------", topic, partitionId);
            HeartMsg heartMsg = JSON.parseObject(record, HeartMsg.class);
            String receiver = heartMsg.getReceiver();
            //将消息放入缓存之中
            if(Cache.cache.get(receiver)==null){
                Cache.cache.put(receiver,new LinkedBlockingDeque<>());
                try {
                    Cache.cache.get(receiver).put(heartMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    Cache.cache.get(receiver).put(heartMsg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            log.info("---------Failure in getting message from kafka-------------");
        }
    }
}
