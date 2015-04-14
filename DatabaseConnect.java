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
            co=DriverManager.getConnection("jdbc:db2://localhost:50000/VK1","DIVYA","hansZIMMER");
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
            //if else to check if already logged in
            if(db.anyOneLoggedIn(db.getConnection())==0)
            new Login(db.getConnection()).setVisible(true);
            else
            new Main(db.getConnection()).setVisible(true);
        }
        public int anyOneLoggedIn(Connection co)
        {
            int count=0;
            try {
                String query="Select count(*) AS counter from (Select * from USERS where status=1)";
                
                st=co.createStatement();
                rs=st.executeQuery(query);
                while(rs.next())
                {
                    count=rs.getInt("counter");
                }
              
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
            return count;
        }

}
