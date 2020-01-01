package com.example.eventbus.event;

public class MessageEvent {

    //3创建发送消息事件类
    public String name;

    public MessageEvent(String name) {
        this.name = name;
    }
}
