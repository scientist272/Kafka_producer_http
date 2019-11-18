package com.cuhk.ksl.kafkasender.util;

import com.cuhk.ksl.kafkasender.vo.heartMsg.HeartMsg;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;

//用于存储消息的缓存队列
public class Cache {
    public static Map<String, BlockingDeque<HeartMsg>> cache;
    static {
        cache = new ConcurrentHashMap<>();
    }
}
