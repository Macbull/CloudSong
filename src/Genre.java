
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL
 */
public class Genre {
  
  String name;
  String details;
  static HashMap<Integer,Song> songs=new HashMap<>();
  Genre(String name,String details){
      this.name = name;
      this.details = details;
  }
  HashMap<Integer,Song> getsongs()
    {
        songs=Populate.searchSongs(this);
        
        return songs;
    }
}
