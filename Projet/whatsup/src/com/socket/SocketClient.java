package com.socket;

import com.ui.ChatFrame;
import com.ui.inc;
import com.ui.login;
import com.ui.message1;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SocketClient implements Runnable{
    
    public int port;
    public String serverAddr;
    public Socket socket;
    public ChatFrame ui;
    public ObjectInputStream In;
    public static ObjectInputStream In1;
    public static ObjectOutputStream Out;
     public static ObjectOutputStream Out1;
    public History hist;
    //////////////////////////
     public login uii;
    public static String user;
 public static String pass;
 public static SocketClient client;
 public static Message msag ;
 ///////////////////////////////////////
  public message1 umi;
  public inc uiii;
   public static inc c = new inc();
  
    
    public SocketClient(ChatFrame frame) throws IOException{
        
        
        ui = frame; this.serverAddr = ui.serverAddr; this.port = ui.port;
        socket = new Socket(InetAddress.getByName(serverAddr), port);
            
        Out = new ObjectOutputStream(socket.getOutputStream());
        Out.flush();
        In = new ObjectInputStream(socket.getInputStream());
        
        hist = ui.hist;
      
    }
    
 public static message1 m = new message1();

    private SocketClient(inc m) {
        try {
            uiii = m;
            socket = new Socket(InetAddress.getByName("localhost"), 13000);
            
            Out = new ObjectOutputStream(socket.getOutputStream());
            Out.flush();
            In = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        boolean keepRunning = true;
       
        while(keepRunning){
            
            try {
                
                Message msg = (Message) In.readObject();
                 String target3 = msg.sender;
                System.out.println("Incoming : "+msg.toString());
                   if(msg.type.equals("signup")){
                    if(msg.content.equals("TRUE")){
                    
                         JOptionPane.showMessageDialog(uii,"insc succeful ! ","insc succeful",JOptionPane.PLAIN_MESSAGE); 
                         
                    }
                    else{
                      JOptionPane.showMessageDialog(uii,"err ! ","erreur d'insc",JOptionPane.PLAIN_MESSAGE); 
                    }
                }
                if(msg.type.equals("message")){
                   
                         if(!msg.sender.equals(login.user) && !m.isShowing()){
                           m.setTitle(target3);
                           m.setVisible(true);}
                            if(msg.sender.equals(login.user)){
                        m.msgchatarea.append("[moi]: " + msg.content + "\n");
                        
                    }
                             else{
                         
                        m.msgchatarea.append("                      ["+msg.sender +" ---> moi] : " + msg.content + "\n");
                       
                    }
                         
            
                                            
                    if(!msg.content.equals(".bye") && !msg.sender.equals(ui.username)){
                        String msgTime = (new Date()).toString();
                        
                        try{
                            hist.addMessage(msg, msgTime);
                            DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                            table.addRow(new Object[]{msg.sender, msg.content, "Me", msgTime});
                        }
                        catch(Exception ex){}  
                    }
                }
                else if(msg.type.equals("login")){
                    if(msg.content.equals("TRUE")){
                        
                        ui.setVisible(true);   
                            }
                    else{
                       JOptionPane.showMessageDialog(uii,"erreur d'auth ! ","ver donnes",JOptionPane.PLAIN_MESSAGE);  
                    } 
                }
                else if(msg.type.equals("get")){
             //   System.out.println("reseved");
        ChatFrame.login1=(String) msg.info[0];
        ChatFrame.prenom1=(String) msg.info[4];
        ChatFrame.age1=(String) msg.info[1];
        ChatFrame.email1=(String) msg.info[2];
        ChatFrame.ville1=(String) msg.info[3];
        ChatFrame.pays1=(String) msg.info[5];
        ChatFrame.statut1=(String) msg.info[6];
                }
                
                
                else if(msg.type.equals("test")){   
//                    ui.jButton7.setEnabled(true);
                }
                else if(msg.type.equals("newuser")){
                    if(!msg.content.equals(ui.username)){
                        boolean exists = false;
                        for(int i = 0; i < ui.model.getSize(); i++){
                            if(ui.model.getElementAt(i).equals(msg.content)){
                                exists = true;
                                break;
                            }
                        }
                        if(!exists){ ui.model.addElement(msg.content); }
                    }
                }
              
                else if(msg.type.equals("signout")){
                    if(msg.content.equals(ui.username)){
   
                        for(int i = 1; i < ui.model.size(); i++){
                            ui.model.removeElementAt(i);
                        }
                        
                        ui.clientThread.stop();
                    }
                    else{
                        ui.model.removeElement(msg.content);
 
                    }
                }
                else if(msg.type.equals("upload_req")){
                    
                    if(JOptionPane.showConfirmDialog(ui, ("Accept '"+msg.content+"' from "+msg.sender+" ?")) == 0){
                        
                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(msg.content));
                        int returnVal = jf.showSaveDialog(ui);
                       
                        String saveTo = jf.getSelectedFile().getPath();
                        if(saveTo != null && returnVal == JFileChooser.APPROVE_OPTION){
                            Download dwn = new Download(saveTo, ui);
                            Thread t = new Thread(dwn);
                            t.start();
                            //send(new Message("upload_res", (""+InetAddress.getLocalHost().getHostAddress()), (""+dwn.port), msg.sender));
                            send(new Message("upload_res", ui.username, (""+dwn.port), msg.sender,null));
                        }
                        else{
                            send(new Message("upload_res", ui.username, "NO", msg.sender,null));
                        }
                    }
                    else{
                        send(new Message("upload_res", ui.username, "NO", msg.sender,null));
                    } 
                }
                else if(msg.type.equals("upload_res")){
                    if(!msg.content.equals("NO")){
                        int port  = Integer.parseInt(msg.content);
                        String addr = msg.sender;
                        
  
                        Upload upl = new Upload(addr, port, ui.file, ui);
                        Thread t = new Thread(upl);
                        t.start();
                    }
                                  }
                
            }
            catch(Exception ex) {
                keepRunning = false;
                System.out.println("Exception SocketClient run()");
                ex.printStackTrace();
            }
        }
       
    }
    
   
     public static void con (boolean b){
     if(b=true){
        
                    ChatFrame m = new ChatFrame();
                    
                    
        m.serverAddr = login._host;
        m.port = Integer.parseInt(login._port);
        
        if(!m.serverAddr.isEmpty() && !login._port.isEmpty()){
            try{
                
                m.client = new SocketClient(m);
                m.clientThread = new Thread(m.client);
                m.clientThread.start();
                
                m.client.send(new Message("test", "testUser", "testContent", "SERVER",null));
            }
            catch(Exception ex){
                //message affiche a l'utilisateur indique que le serveur pas trouve, adresse ip ou port incorrect 
            }
        }   
        }
     }
    
     
      public static void getinfo (String target){
            try{          
                
                 ChatFrame.client.send(new Message("get",login.user, target, "SERVER",null));
            }
            catch(Exception ex){
                //message affiche a l'utilisateur indique que le serveur pas trouve, adresse ip ou port incorrect 
            }
            
    }
     
    public static void insc (){

                    inc k = new inc();
                    
                    Object[] t = {inc.login,inc.mdp,inc.nom,inc.prenom,inc.age,inc.email,inc.pays,inc.ville} ;
            try{
               
                k.client = new SocketClient(k);
                k.clientThread = new Thread(k.client);
                k.clientThread.start();
                
                k.client.send(new Message("test", "testUser", "testContent", "SERVER",null));
                
                
                
                 k.client.send(new Message("signup", inc.login, inc.mdp, "SERVER",t));
            }
            catch(Exception ex){
                //message affiche a l'utilisateur indique que le serveur pas trouve, adresse ip ou port incorrect 
            }
            
    }
    public static void log (boolean b){
    if(b=true){
         ChatFrame m = new ChatFrame();
          m.serverAddr = login._host;
          m.port = Integer.parseInt(login._port);
        
        if(!m.serverAddr.isEmpty() && !login._port.isEmpty()){
            try{
               
                m.client = new SocketClient(m);
                m.clientThread = new Thread(m.client);
                m.clientThread.start();
                
                m.client.send(new Message("test", "testUser", "testContent", "SERVER",null));
            }
            catch(Exception ex){
                //message affiche a l'utilisateur indique que le serveur pas trouve, adresse ip ou port incorrect 
            }
            
        }
        
                   m.username=login.user;
                   m.password=login.pass;
                   
          try{         
             if(!m.username.isEmpty() && !m.password.isEmpty()){
            m.client.send(new Message("login", m.username, m.password, "SERVER",null)); 
             }
            
            
        }
        catch(Exception ex){
               /// message message affiche a l'utilisateur indique que utilisateur pas inscrit
            }
          
    
    }
   
    }
    
    
public static void msg() {
    ChatFrame k = new ChatFrame(); 
    
 String target = k.jList1.getSelectedValue().toString();
     m.setTitle(target);
     m.setVisible(true);    
}   
public  void msg1(String s) {
 message1 e =new message1();
 m.setTitle(s);
 m.setVisible(true);
}
    public void send(Message msg){
        try {
            Out.writeObject(msg);
            Out.flush();
            System.out.println("Outgoing : "+msg.toString());
            
            if(msg.type.equals("message") && !msg.content.equals(".bye")){
                String msgTime = (new Date()).toString();
                try{
                    hist.addMessage(msg, msgTime);               
                    DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                    table.addRow(new Object[]{"Me", msg.content, msg.recipient, msgTime});
                }
                catch(Exception ex){}
            }
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
        }
    }
    
    public void closeThread(Thread t){
        t = null;
    }
}
