
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class updateData {
    static Connection co;
    static String query;
    static Statement st;
    static HashMap<Integer,Artist> allArtists;
    static HashMap<Integer,Album> allAlbums;
    static HashMap<Integer,Song> allSongs;                  
    static HashMap<Integer,Artist> ArtistSearch;
    static HashMap<Integer,Album> AlbumSearch;
    static HashMap<Integer,Song> SongSearch;
    static User us;
    static void setConnection(Connection con){
         co=con;
        try {
            st=co.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void setUser(User us){
        updateData.us=us;
    }
    updateData(Connection con,User us) 
    {
        setConnection(con);
        setUser(us);
    }
    static void addSong(Song song) throws FileNotFoundException{
     
        if(song != null){
        try {
        
            
             query = "Insert into sch.Track(name,year,length,language,FA_id,FG_name,FAr_id,FUp_uid,addr) values('"+song.name+"',"+song.year+","+song.length+",'"+song.language+"',"+song.album.Aid+",'"+song.genre.name+"',"+song.artist.Arid+","+us.uid+",?)";
           
           
            File file=new File(song.file);
            if(file!=null){
            InputStream fis = new FileInputStream(file);
            int len = (int)file.length();
            
            PreparedStatement pstmt=co.prepareStatement(query);
            pstmt.setBinaryStream(1, fis, len);
            pstmt.executeUpdate();
            pstmt.close();
            }
            else{
                JOptionPane.showMessageDialog(null, "Error","File Not Found",1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error", ex.getMessage(),1);
        }
        }
        
    }
    static void addAlbum(Album album){
         if(album != null){
        try {
         
            query = "Insert into sch.Album(name,year,type,FAr_id) values('"+album.name+"',"+album.year+",'"+album.type+"',"+album.albumArtist.Arid+")";
            st.executeUpdate(query);
        
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error", ex.getMessage(),1);
        }
        }
    }
    static void addGenre(Genre genre){
       if(genre != null){
        try {
         
            query = "Insert into sch.Genre values('"+genre.name+"','"+genre.details+"')";
            st.executeUpdate(query);
        
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error", ex.getMessage(),1);
        }
        } 
    }  
    static void addArtist(Artist artist){
        if(artist != null){
        try {
            query  = "Insert into sch.Artist(name,origin,description) values('"+artist.name+"','"+artist.origin+"','"+artist.description+"')";
            st.executeUpdate(query);
        
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error", ex.getMessage(),1);
        }
        }
        
    }
    static void updateHistory(Song s){
        if(s != null && us!=null){
        try {
           
            query  = "Insert into sch.history(uid,tid) values("+us.uid+","+s.id+")";
            st.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "You are credited by : "+s.value);
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error", ex.getMessage(),1);
        }
        }
        
    }
    static void download(Song s){
        if(s != null){
        try {
            
            query  = "Insert into sch.downloads(uid,tid) values("+us.uid+","+s.id+")";
            st.executeUpdate(query);
         JOptionPane.showMessageDialog(null, "You are decredited by : "+s.cost);
            
        } catch (SQLException ex) {
          Logger.getLogger(updateData.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "You have already Downloaded this song ! So you will not be decredited !");
        }
        }
    }
    static void addPlaylist(Playlist pl){
        
    }
    static void updateuserRating(Song s){
        try {
            query="Select * from sch.rated where tid="+s.id+" and uid="+us.uid;
            ResultSet rs;
            rs=st.executeQuery(query);
            if(rs.next()){
                rs.close();
                query="update sch.rated set rating="+s.userRating+" where tid="+s.id+" and uid="+us.uid;
            }
            else{
                query="insert into sch.rated(tid,uid,rating) values("+s.id+","+us.uid+","+s.userRating+")";
                
            }
          
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(updateData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
