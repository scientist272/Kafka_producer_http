package com.cuhk.ksl.kafkasender.controller;

import com.cuhk.ksl.kafkasender.exception.DataFormatException;
import com.cuhk.ksl.kafkasender.exception.UnAutnException;
import com.cuhk.ksl.kafkasender.service.KafkaSender;
import com.cuhk.ksl.kafkasender.vo.Constant;
import com.cuhk.ksl.kafkasender.vo.KafkaProducerMsg;
import com.cuhk.ksl.kafkasender.vo.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/")
public class MsgSendController {

    private final KafkaSender kafkaSender;

    @Autowired
    public MsgSendController(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @PostMapping(value = "/send/{topic}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultMsg sendMsgToKafkaServer(
            @RequestBody KafkaProducerMsg msg, @PathVariable String topic,
            HttpServletRequest request) throws DataFormatException, UnAutnException {
        if (!msg.judgeCorrect()) {
            throw new DataFormatException("Incorrect Data format");
        }
        if (request.getHeader(Constant.SEND_KEY) == null || !request.getHeader(Constant.SEND_KEY).equals(Constant.SEND_KEY_VALUE)) {
            throw new UnAutnException("You have no right to send data");
        }
        kafkaSender.send(msg, topic);
        ResultMsg result = new ResultMsg();
        result.setCode(Constant.SUCCESS_CODE);
        result.setMessage("Success in sending data");
        log.info("---------------accept data from :{}, at time :{}----------------------------"
                , request.getRemoteAddr(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return result;
    }


}
