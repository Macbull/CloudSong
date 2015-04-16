
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Song {
    int id,cost,value,plays,year,userRating;
    double length,avgRating;
    String name;
    String language;
    Album album;
    Artist artist;
    Genre genre;
    String file;

Song(int id,String name,int year,double length,String language,int value,int cost,int plays,Artist artist,Album album,Genre genre){
    this.id=id;
    this.name=name;
    this.year=year;
    this.length=length;
    this.language=language;
    this.value=value;
    this.cost=cost;
    
    this.plays=plays;
    this.album=album;
    this.artist=artist;
    this.genre=genre;
}

Song(String name,int year,double length,String language,int value,int cost,Artist artist,Album album,Genre genre,String file){
    this.name=name;
    this.year=year;
    this.length=length;
    this.language=language;
    this.value=value;
    this.cost=cost;
    this.album=album;
    this.artist=artist;
    this.genre=genre;
    this.file=file;

}
void play(Connection co){
        try {
            updateData.updateHistory(this);
            try {
            new Download(co,"/Users/Narwal/Desktop/"+this.id+".mp3",this.id);
           
            Play p = new Play("/Users/Narwal/Desktop/"+this.id+".mp3");
             this.plays++;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        } catch (IOException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}

void updateuserRating(int i){
    this.userRating=i;
    updateData.updateuserRating(this);
    
}
void buy(Connection co,String file){
        try {
            new Download(co,file,this.id);
            updateData.download(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Song.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}
 

 
    
    
}
