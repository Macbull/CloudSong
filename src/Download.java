/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Divya
 */
public class Download {

    /**
     * @param args the command line arguments
     */
    public Download(Connection co,String s,int sid) throws ClassNotFoundException, SQLException, FileNotFoundException {
        // TODO code application logic here
        
        Statement st=null;
        PreparedStatement pstmt;
        ResultSet rs;
            
         
         byte[] fileBytes;
         String query;
         try
         {
              query = "select addr from sch.track where id="+sid;
              st = co.createStatement();
              rs = st.executeQuery(query);
              if (rs.next())
              {
                   fileBytes = rs.getBytes(1);
                  
                  OutputStream targetFile=new FileOutputStream(s);

                  targetFile.write(fileBytes);
                  targetFile.close();
               }        
                        
           }
                
         catch (Exception e)
          {
            e.printStackTrace();
          }
    }
    
}