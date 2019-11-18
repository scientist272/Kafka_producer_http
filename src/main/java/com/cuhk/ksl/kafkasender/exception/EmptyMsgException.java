package com.cuhk.ksl.kafkasender.exception;

//用户消息为空的时候抛出的异常
public class EmptyMsgException extends Exception{
    public EmptyMsgException(String msg){
        super(msg);
    }
}
