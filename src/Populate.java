/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public  class Populate {
   
    static Connection co;
    static String query;
    static Statement st;
    static HashMap<Integer,Artist> allArtists;
    static HashMap<Integer,Album> allAlbums;
    static HashMap<Integer,Song> allSongs;   
    static HashMap<String,Genre> allGenres;
    static HashMap<String,Genre> GenreSearch;
    static HashMap<Integer,Artist> ArtistSearch;
    static HashMap<Integer,Album> AlbumSearch;
    static HashMap<Integer,Song> SongSearch;
    static User us;
    Populate(Connection con,User us) 
    {
        setConnection(con);
        setUser(us);
        setUpdateddata();
       
        
    }
    void setUser(User us){
        this.us=us;
    }
    void setConnection(Connection con){
         co=con;
        try {
            st=co.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void setUpdateddata(){
        allArtists = getArtists();
        allAlbums = getAlbums();
         allGenres = getGenres();
        allSongs = getSongs();
       updateRating();
       updateCredits();
    
    }
    static void updateRating(){
        getuserRating();
       getavgRating();
       getalbumRating();
    }
    
    ////////// Getting Data from db2
    
    
    
    private static HashMap<Integer,Song> getSongs()
    {   
//        ArrayList<Song> songs = new ArrayList<Song>();
        HashMap<Integer,Song> songs = new HashMap<>();
        query="select * from sch.Track";
        try {
            ResultSet rs=st.executeQuery(query);
            while(rs.next())
            { 
                Song s = createSong(rs);
                 
                songs.put(s.id, s);
               

            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return songs;
    }
    
   
    
    private static HashMap<Integer,Album> getAlbums()
    {
        HashMap<Integer,Album> albums = new HashMap<>();
        query="select * from sch.Album";
        try {
            ResultSet rs=st.executeQuery(query);
             while(rs.next())
            {  
                

               Album a = createAlbum(rs);
               albums.put(a.Aid,a);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    return albums;
    }
    
    
    
    Playlist[] getplaylist(int uid)
    {
     //to be moved in users//  
        
    return null;
    }
    
    private static HashMap<Integer,Artist> getArtists()
    {
        HashMap<Integer,Artist> artists = new HashMap<>();
        query="select * from sch.Artist";
        try {
            ResultSet rs=st.executeQuery(query);
      
            while(rs.next())
            {  
              
             
                   int id = rs.getInt("id");
                   String name = rs.getString("name");
                   String origin = rs.getString("origin");
                   String description = rs.getString("description");
                   Artist ar = new Artist(id,name,origin,description);
               
               artists.put(ar.Arid,ar);
               
            }
          
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return artists;
    }        
    
    private static HashMap<String,Genre> getGenres()
    {
        HashMap<String,Genre> genres = new HashMap<>();
        query="select * from sch.Genre";
        try {
            ResultSet rs=st.executeQuery(query);
           
             while(rs.next())
            {  
                

               Genre a = new Genre(rs.getString("name"),rs.getString("details"));
               genres.put(a.name,a);
             
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    return genres;
    }
    static void getavgRating(){
         query = "Select avg(rating) as avgrating,tid from sch.rated group by tid";
         try {
            ResultSet rs=st.executeQuery(query);
           
             while(rs.next())
            {  
                
                int Sid=rs.getInt("tid");
                double avr=rs.getDouble("avgrating");
                allSongs.get(Sid).avgRating=avr;
              
             
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    static void getalbumRating(){
        query = "Select id,avgrating from sch.album";
         try {
            ResultSet rs=st.executeQuery(query);
           
             while(rs.next())
            {  
                
                int Aid=rs.getInt("id");
                int avr=rs.getInt("avgrating");
                allAlbums.get(Aid).avgRating=avr;
              
             
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void getuserRating(){
        if(us!=null){
         query = "Select tid,rating from sch.rated where uid="+us.uid;
         try {
            ResultSet rs=st.executeQuery(query);
           
             while(rs.next())
            {  
                
                int Sid=rs.getInt("tid");
                int avr=rs.getInt("rating");
                allSongs.get(Sid).userRating=avr;
              
             
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    static void updateCredits(){
        if(us!=null){
         query = "Select credit from sch.user where id="+us.uid;
         try {
            ResultSet rs=st.executeQuery(query);
           
             while(rs.next())
            {  
                
                us.setCredit(rs.getInt("credit"));
                
                
              
             
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    ///////////// Creating objects from ResultSet
    
    private static Song createSong(ResultSet rs){
        try {
            //retrieve variables from rs
            // search for album object in HashMap
            // search for artist object in hashMap
            
            Album album=Populate.allAlbums.get(rs.getInt("FA_id"));
            Artist artist=Populate.allArtists.get(rs.getInt("FAr_id"));
            Genre genre=Populate.allGenres.get(rs.getString("FG_name"));
            
            Song s = new Song(rs.getInt("id"),rs.getString("name"),rs.getInt("year"),rs.getDouble("length"),rs.getString("language"),rs.getInt("value"),rs.getInt("cost"),rs.getInt("plays"),artist,album,genre);
           
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
   
//    Artist createArtist(ResultSet rs){
//        //retrieve variables from rs
////        Artist artist = new Artist();
//       
////        return artist;
//    }
   private static  Album createAlbum(ResultSet rs){
        try {
            //retrieve variables from rs
          
            Artist artist=Populate.allArtists.get(rs.getInt("FAr_id"));
            int avgRating = -1;
            
            Album album = new Album(rs.getInt("id"),rs.getString("name"),rs.getString("type"),rs.getInt("year"),artist,rs.getInt("avgrating"));
   
            return album;
        } catch (SQLException ex) {
            Logger.getLogger(Populate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    /////// Searching by name
    
     
    static HashMap<Integer,Song> searchSongs(String name){
//        if(name.trim().equals("")){
//            return SongSearch=allSongs;
//        }
        SongSearch = new HashMap<>();
        int i =1;
        for (Song song : allSongs.values())
        {
            if(song.name.toLowerCase().contains(name.trim().toLowerCase())){
                SongSearch.put(i,song);
                i++;
            }
            
        }
        return SongSearch;
    }
    
   
    static HashMap<String,Genre> searchGenres(String name){
//        if(name.trim().equals("")){
//            return GenreSearch=allGenres;
//        }
        GenreSearch = new HashMap<>();
        
        for (Genre genre : allGenres.values())
        {
            if(genre.name.toLowerCase().contains(name.trim().toLowerCase())){
                GenreSearch.put(genre.name,genre);
                
            }
            
        }
        return GenreSearch;
    }
    static HashMap<Integer,Album> searchAlbums(String name){
//        if(name.trim().equals("")){
//            return AlbumSearch=allAlbums;
//        }
        AlbumSearch = new HashMap<>();
        int i =1;
        for (Album album : allAlbums.values())
        {
            if(album.name.toLowerCase().contains(name.trim().toLowerCase())){
                AlbumSearch.put(i,album);
                i++;
            }
            
        }
        return AlbumSearch;
    }
    static HashMap<Integer,Artist> searchArtists(String name){
//        if(name.trim().equals("")){
//            return ArtistSearch=allArtists;
//        }
        ArtistSearch = new HashMap<>();
        int i =1;
        for (Artist artist : allArtists.values())
        {
            if(artist.name.toLowerCase().contains(name.trim().toLowerCase())){
                ArtistSearch.put(i,artist);
                i++;
            }
            
        }
        return ArtistSearch;
    }
    
    
    ///////// Searching by Objects
    
    static HashMap<Integer,Song> searchSongs(Album album){
        HashMap<Integer,Song> searchResult = new HashMap<>();                
        int i =1;
  
        for (Song song : allSongs.values())
        {
            
            if(song.album.equals(album)){
                searchResult.put(i,song);
                i++;
            }
            
        }
       
        return searchResult;
    }
    static HashMap<Integer,Song> searchSongs(Genre genre){
        HashMap<Integer,Song> searchResult = new HashMap<>();                
        int i =1;
  
        for (Song song : allSongs.values())
        {
            
            System.out.println("song genre : "+song.genre);
            System.out.println("Searched genre : "+genre);
        
        
            if(song.genre.equals(genre)){
               System.out.println(song);
                searchResult.put(i,song);
                i++;
            }
            
        }
       
        return searchResult;
    }
    
    static HashMap<Integer,Song> searchSongs(Artist artist){
        HashMap<Integer,Song> searchResult = new HashMap<>();
        int i =1;
        for (Song song : allSongs.values())
        {
            if(song.artist.equals(artist)){
                searchResult.put(i,song);
                i++;
            }
            
        }
        return searchResult;
    }
    
    static HashMap<Integer,Album> searchAlbums(Artist artist){
        HashMap<Integer,Album> searchResult = new HashMap<>();
        int i =1;
        for (Album album : allAlbums.values())
        {
            if(album.albumArtist.equals(artist)){
                searchResult.put(i,album);
                i++;
            }
            
        }
        return searchResult;
    }
    
  
    
}



