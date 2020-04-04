package com.cuhk.ksl.kafkasender.vo.heartMsg;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeartMsg {
    private String receiver;
    private String sender;
    private String pictureUrl;
    private String title;
    private String content;
    private String recordBase;
}
