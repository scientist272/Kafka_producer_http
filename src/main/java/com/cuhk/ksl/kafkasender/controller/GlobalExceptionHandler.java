package com.cuhk.ksl.kafkasender.controller;

import com.cuhk.ksl.kafkasender.exception.DataFormatException;
import com.cuhk.ksl.kafkasender.exception.UnAutnException;
import com.cuhk.ksl.kafkasender.vo.Constant;
import com.cuhk.ksl.kafkasender.vo.ResultMsg;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResultMsg handleHttpMediaTypeNotSupportedException(){
        ResultMsg result = new ResultMsg();
        result.setCode(Constant.FAILURE_CODE);
        result.setMessage("Content Type is not supported, must be JSON format");
        return result;
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResultMsg handleHttpRequestMethodNotSupportedException(){
        ResultMsg result = new ResultMsg();
        result.setCode(Constant.FAILURE_CODE);
        result.setMessage("Request method must be POST");
        return result;
    }

    @ExceptionHandler(value = DataFormatException.class)
    public ResultMsg handleDataFormatException(DataFormatException e) {
        ResultMsg result = new ResultMsg();
        result.setCode(Constant.FAILURE_CODE);
        result.setMessage(e.getMessage());
        return result;
    }

    @ExceptionHandler(value = UnAutnException.class)
    public ResultMsg handleUnAuthException(UnAutnException e) {
        ResultMsg result = new ResultMsg();
        result.setCode(Constant.FAILURE_CODE);
        result.setMessage(e.getMessage());
        return result;
    }
}
