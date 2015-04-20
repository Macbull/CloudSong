
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Divya
 */
public class Play {
    
    Play(String s) throws IOException
    {
       try{ ProcessBuilder pb = new ProcessBuilder("/Applications/VLC.app/Contents/MacOS/VLC ", s);
        Process start = pb.start();
       }
       catch(Exception ex){
           JOptionPane.showMessageDialog(null,"VLC Media Player not found");
       }
    }
    
    
}
