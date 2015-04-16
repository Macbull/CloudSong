
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Album {
    String name,type;
    int Aid,year;
    Artist albumArtist;
    double avgRating;
    static HashMap<Integer,Song> songs=new HashMap<>();
    
    Album(int id,String name,String type,int year,Artist artist,double rating){
        this.Aid=id;
        this.name=name;
        this.type=type;
        this.year=year;
        this.albumArtist=artist;
        this.avgRating=rating;
    }
    Album(String name,String type,int year,Artist artist){
        this.name=name;
        this.type=type;
        this.year=year;
        this.albumArtist=artist;
        
    }
    HashMap<Integer,Song> getsongs()
    {
        songs=Populate.searchSongs(this);
        return songs;
    }
    
    Process play(){
       
        
        return null;
        
    }
}
