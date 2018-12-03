package com.socket;

import java.io.Serializable;
import java.util.Arrays;

public class Message implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String type, sender, content, recipient;
    public Object[]  info;
    
    public Message(String type, String sender, String content, String recipient,Object[] info){
        this.type = type; this.sender = sender; this.content = content; this.recipient = recipient; this.info=info;  }
    
    @Override
    public String toString(){
        
        return "{type='"+type+"', sender='"+sender+"', content='"+content+"', recipient='"+recipient+"',info='"+Arrays.toString(info)+"'}";
    }
}
