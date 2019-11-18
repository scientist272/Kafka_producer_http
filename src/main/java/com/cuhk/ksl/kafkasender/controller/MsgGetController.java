package com.cuhk.ksl.kafkasender.controller;

import com.alibaba.fastjson.JSON;
import com.cuhk.ksl.kafkasender.exception.EmptyMsgException;
import com.cuhk.ksl.kafkasender.util.Cache;
import com.cuhk.ksl.kafkasender.vo.Constant;
import com.cuhk.ksl.kafkasender.vo.ResultMsg;
import com.cuhk.ksl.kafkasender.vo.heartMsg.HeartMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

@Slf4j
@RestController
@RequestMapping("/")
public class MsgGetController {
    //从缓存中根据用户名取消息
    @GetMapping("/get/{patient}")
    public ResultMsg getMsg(@PathVariable String patient) throws EmptyMsgException {

        BlockingDeque<HeartMsg> queue = Cache.cache.get(patient);
        if(queue==null){
            throw new EmptyMsgException("该用户消息为空");
        }
        List<HeartMsg> list = new ArrayList<>();
        ResultMsg resultMsg = new ResultMsg();
        if(!queue.isEmpty()){
            while(!queue.isEmpty()) {
                try {
                    list.add(queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            resultMsg.setCode(Constant.SUCCESS_CODE);
            resultMsg.setMessage(list);
            return resultMsg;
        }else{
            throw new EmptyMsgException("该用户消息为空");
        }
    }
}
