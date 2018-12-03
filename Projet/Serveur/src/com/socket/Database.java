package com.socket;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.*;

public class Database {
    
 
    
    public Database(String user){
   
    }
   
    public boolean userExists(String username){
      
        try {
             Connection connect = null ;
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String db_con = "jdbc:mysql://localhost:3306/test";
            connect = DriverManager.getConnection(db_con,"root","");
            Statement st = connect.createStatement();
            String sql = "select * from client ";
            ResultSet res = st.executeQuery(sql);
            String a =username;
            while(res.next()){
                if(res.getString(2).equals(a)){
                        System.out.println("login");
                          
                        return true;
                   
                }
                else{
                    System.out.println("incorrecte");
                   
                }
            }     
        } catch (SQLException ex) {
            System.out.println("pas de connection");
        }
        
    
       return false;
        
      
        
        
       
    }
    
    public void statut(String statut,String client){
     Connection connect = null ;
     
        try {
           
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String db_con = "jdbc:mysql://localhost:3306/test";
            connect = DriverManager.getConnection(db_con,"root","");
            Statement st = connect.createStatement();
       
            String sql = "UPDATE client SET statut = '"+statut+"' WHERE nom ='"+client+"'" ;
            st.executeUpdate(sql);
            
          
            
        } catch (SQLException ex) {
            System.out.println("erreur dans la base de donness"+ ex);
            
        }
    }
    
     public Object[] getinfo(String username){
        Object[] t = new Object[7];
                  Connection connect = null ;
        try {
            //chargement de driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //connection
            String db_con = "jdbc:mysql://localhost:3306/test";
            connect = DriverManager.getConnection(db_con,"root","");
            //statement
            Statement st = connect.createStatement();
           
            
            
            String sql = "SELECT * FROM `client` WHERE `nom` = '"+username+"'";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
         t[0]=res.getString(2);t[1]=res.getString(4);t[2]=res.getString(5);t[3]=res.getString(6);t[4]=res.getString(7);t[5]=res.getString(8);t[6]=res.getString(9);
                
                
            }
            
        } catch (SQLException ex) {
            System.out.println("erreur dans la base de donness"+ ex);
            
        }
     return t;
    }

    public boolean checkLogin(String username, String password){
        
       if(!userExists(username)){ return false; }
        
                  Connection connect = null ;
        try {
            //chargement de driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //connection
            String db_con = "jdbc:mysql://localhost:3306/test";
            connect = DriverManager.getConnection(db_con,"root","");
            //statement
            Statement st = connect.createStatement();
           
            String a = username;
            String b = password;
            String sql = "select * from client ";
            ResultSet res = st.executeQuery(sql);
            while(res.next()){
               
                if(res.getString(2).equals(a) && res.getString(3).equals(b)){
                        System.out.println("login");
                        return true;
                }
                else{System.out.println("incorrecte");
               }
            }
            
        } catch (SQLException ex) {
            System.out.println("erreur dans la base de donness");
            
        }
        finally{
           try {
               connect.close();
           } catch (SQLException ex) {
               Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
         return false;
        
        
   
    }
  
    public void addUser(String username, String password,Object prenom,Object age,Object email,Object pays,Object ville){
       // 
         Connection connect = null ;
     
        try {
           
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            String db_con = "jdbc:mysql://localhost:3306/test";
            connect = DriverManager.getConnection(db_con,"root","");
            Statement st = connect.createStatement();
       
            String sql = "INSERT INTO `client`(`nom`, `password`,`age`,`email`,`ville`,`prenom`,`pays`) VALUES ('"+username+"','"+password+"' ,'"+age+"','"+email+"','"+ville+"','"+prenom+"','"+pays+"')";
            st.executeUpdate(sql);
            
          
            
        } catch (SQLException ex) {
            System.out.println("erreur dans la base de donness"+ ex);
        }
        finally{
         try {
             connect.close();
         } catch (SQLException ex) {
             System.out.println("erreur close connectin");
         }
        }
       
	}
    
    public static String getTagValue(String sTag, Element eElement) {
	NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
	return nValue.getNodeValue();
  }
}
