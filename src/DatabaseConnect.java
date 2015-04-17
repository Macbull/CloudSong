/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Divya
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseConnect {
	
	Connection co=null;
        Statement st=null;
        PreparedStatement pstmt;
        ResultSet rs;
            
        int status;
        
        void makeConnection() throws ClassNotFoundException, SQLException
        {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            co=DriverManager.getConnection("jdbc:db2://192.168.110.1:50000/songlib","Dell","kalilinux");
            //st=co.createStatement();
        }
        Connection getConnection()
        {
            return co;
        }
        void endConnection() throws SQLException
        {
            co.close();
        }
        
        public static void main(String args[]) throws ClassNotFoundException, SQLException
        {
            
            DatabaseConnect db=new DatabaseConnect();
            db.makeConnection();
            
            
            //new MaintainHash().createHashMap(db.getConnection());
            //MaintainHash map=new MaintainHash();
           // map.createHashMap(db.getConnection());
            //if else to check if already logged in
            
            new Login(db.getConnection()).setVisible(true);
            
        }
        
}
