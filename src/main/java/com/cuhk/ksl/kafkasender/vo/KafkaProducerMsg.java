package com.cuhk.ksl.kafkasender.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KafkaProducerMsg {
    private String user;
    private String timestamp;
    private String device;
    private String data;

    public boolean judgeCorrect(){
        if(this.user==null || this.timestamp==null || this.device==null || this.data==null ){
            return false;
        }
        return true;
    }
}
