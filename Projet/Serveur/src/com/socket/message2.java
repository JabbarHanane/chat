
package com.socket;
import java.io.Serializable;

public class message2 implements Serializable{
     private static final long serialVersionUID = 1L;
    public String type, sender, recipient;
    public String content[];
    
    public message2(String type, String sender, String content[], String recipient){
    }
    
    @Override
    public String toString(){
        return "{type='"+type+"', sender='"+sender+"', content='"+content+"', recipient='"+recipient+"'}";
    }
}
