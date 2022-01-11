package com.example.natour2.model;

import java.util.Date;

public class ChatMessage {
    public String senderId;
    public String receiverId;
    public String message;
    public String dataTime;
    public Date dateObject;
    public String conversionId;
    public String conversionName;
    public String conversionImage;


    public ChatMessage(){

    }
    public ChatMessage(String senderId, String receiverId, String message, String conversionName){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.conversionName = conversionName;
    }


}
