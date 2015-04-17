
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Artist {
    int Arid;
    String name;
    String origin;
    String description;
    static HashMap<Integer,Song> songs=new HashMap<>();
    static HashMap<Integer,Album> albums=new HashMap<>();

    Artist(int id,String name,String origin,String description){
        this.Arid=id;
        this.name=name;
        this.origin= origin;
        this.description=description;
    }
    Artist(String name,String origin,String description){
        
        this.name=name;
        this.origin= origin;
        this.description=description;
    }
    
    HashMap<Integer,Album> getalbums()
    {
        albums=Populate.searchAlbums(this);
        return albums;
    }
    
    HashMap<Integer,Song> getsongs()
    {
        songs=Populate.searchSongs(this);
        return null;
    }
    
    Process play(){
        
        return null;
        
    }
}
