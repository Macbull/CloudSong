
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Narwal
 */
public class MainWindow extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    Connection co;
    User user;
    Populate one;
    Song selectedSong;
    Album selectedAlbum;
    Artist selectedArtist;
    Genre selectedGenre;
    
    public MainWindow(Connection connection,User u) {
        initComponents();
        co=connection;
        user=u;
        if(user!=null){
        
        jLabel3.setText(user.getName());
       setCredit();
        }
        else{
            jLabel3.setText("Guest");
            jButton9.setEnabled(false);
          
            jButton6.setEnabled(false);
            jButton10.setEnabled(false);
            jLabel2.setText("0");
        }
        one = new Populate(co,user);
        resetSearch();
        
    }
    void setCredit(){
        if(user!=null){
        Populate.updateCredits();
        jLabel17.setText("Credits : "+user.getCredit());
        }
        
    }
    void setSelectedGenre(JTable sel){
         int index = sel.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) sel.getModel();
        selectedGenre = Populate.GenreSearch.get((model.getValueAt(index, 1).toString()));
        
    }
    void setSelectedArtist(JTable sel){
         int index = sel.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) sel.getModel();
        selectedArtist = Populate.ArtistSearch.get(Integer.parseInt(model.getValueAt(index, 0).toString()));
        
    }
    void setSelectedAlbum(JTable sel,Boolean fromSearch){
         int index = sel.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) sel.getModel();
        if(fromSearch)
        selectedAlbum = Populate.AlbumSearch.get(Integer.parseInt(model.getValueAt(index, 0).toString()));
        else
        {  
           selectedAlbum = selectedArtist.getalbums().get(Integer.parseInt(model.getValueAt(index, 0).toString())); }
    }
    
    void setSelectedSong(JTable sel,Boolean fromSearch,Boolean fromArtist,Boolean fromAlbum){
        int index = sel.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) sel.getModel();
        if(fromSearch)
        selectedSong = Populate.SongSearch.get(Integer.parseInt(model.getValueAt(index, 0).toString()));
        else if(fromArtist)
        selectedSong = selectedArtist.getsongs().get(Integer.parseInt(model.getValueAt(index, 0).toString()));
        else if(fromAlbum)
        selectedSong = selectedAlbum.getsongs().get(Integer.parseInt(model.getValueAt(index, 0).toString()));    
        else
            selectedSong = selectedGenre.getsongs().get(Integer.parseInt(model.getValueAt(index, 0).toString()));    
       
//        System.out.println("selected : "+selectedGenre);
//        System.out.println(selectedGenre.getsongs().keySet());
//        System.out.println(model.getValueAt(index, 0));
        
        
         jLabel14.setText(selectedSong.name);
    }
    
    void rate(int i){
        if(selectedSong!=null)
        selectedSong.updateuserRating(i);
        else
            JOptionPane.showMessageDialog(null,"Select a song to rate");
    }
    private void populateSongTable(HashMap<Integer,Song> songs)
    {
        
        DefaultTableModel model=(DefaultTableModel) songTable.getModel();
        model.setNumRows(0);
        Song song;
        for (Integer i : songs.keySet()) {
           
            song=songs.get(i);
            model.addRow(new Object[]{i, song.name,song.language,song.length,song.artist.name,song.album.name,song.genre.name,song.avgRating,song.plays}); 
            
        }                
        songTable.setModel(model);
    }
     private void populateSongTable4(HashMap<Integer,Song> songs)
    {
        
        DefaultTableModel model=(DefaultTableModel) songTable4.getModel();
        model.setNumRows(0);
        Song song;
        for (Integer i : songs.keySet()) {
           
            song=songs.get(i);
            
              model.addRow(new Object[]{i, song.name,song.language,song.length,song.artist.name,song.album.name,song.genre.name,song.avgRating,song.plays});  
        }                
        
    }
    private void populateSongTable2(HashMap<Integer,Song> songs)
    {
        DefaultTableModel model= (DefaultTableModel) songTable2.getModel();
        model.setNumRows(0);
        Song song;
        for (Integer i : songs.keySet()) {
            song=songs.get(i);
            model.addRow(new Object[]{i, song.name, song.language,song.avgRating,song.plays});  
        }                
    }
    private void populateSongTable3(HashMap<Integer,Song> songs)
    {
        DefaultTableModel model= (DefaultTableModel) songTable3.getModel();
        model.setNumRows(0);
        Song song;
        for (Integer i : songs.keySet()) {
            song=songs.get(i);
            model.addRow(new Object[]{i, song.name, song.language,song.avgRating,song.plays});  
        }                
    }
    private void populateAlbumTable(HashMap<Integer,Album> albums)
    {
        DefaultTableModel model= (DefaultTableModel) albumTable.getModel();
        model.setNumRows(0);
        Album album;
        for (Integer i : albums.keySet()) {
            album=albums.get(i);
            model.addRow(new Object[]{i,album.name});  
        }
        
        
    }
    private void populateGenreTable(HashMap<String,Genre> genres)
    {
        DefaultTableModel model= (DefaultTableModel) genreTable.getModel();
        model.setNumRows(0);
        Album album;
        int i=1;
        for (String name : genres.keySet() ) {
            
            model.addRow(new Object[]{i,name});
            i++;
        }
        
        
    }
     private void populateAlbumTable2(HashMap<Integer,Album> albums)
    {
        DefaultTableModel model= (DefaultTableModel) albumTable2.getModel();
        model.setNumRows(0);
        Album album;
        for (Integer i : albums.keySet()) {
            album=albums.get(i);
            model.addRow(new Object[]{i,album.name,album.albumArtist.name});  
        }
        
        
    }
     private void populateArtistTable(HashMap<Integer,Artist> artists)
    {
        DefaultTableModel model= (DefaultTableModel) artistTable.getModel();
        model.setNumRows(0);
        Artist artist;
        for (Integer i : artists.keySet()) {
            artist = artists.get(i);
            model.addRow(new Object[]{i,artist.name});  
        }
        
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        all = new javax.swing.JPanel();
        user_logout = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        Library_Playlist = new javax.swing.JTabbedPane();
        Library = new javax.swing.JPanel();
        song_Album_Artist = new javax.swing.JTabbedPane();
        Song = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        songTable = new javax.swing.JTable();
        Album = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        albumTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        songTable2 = new javax.swing.JTable();
        Album_details = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Artist = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        artistTable = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        albumTable2 = new javax.swing.JTable();
        Artist_details = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        song = new javax.swing.JPanel();
        AlbumDetails_2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        songTable3 = new javax.swing.JTable();
        Genre = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        genreTable = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        songTable4 = new javax.swing.JTable();
        Album_details1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Play_search = new javax.swing.JPanel();
        search_filters = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CloudSong");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        user_logout.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        user_logout.setOpaque(false);

        jLabel3.setText("Name");

        jLabel2.setFont(new java.awt.Font("Marker Felt", 3, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("300");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel1.setText("Log Out");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel17.setText("Credit");

        javax.swing.GroupLayout user_logoutLayout = new javax.swing.GroupLayout(user_logout);
        user_logout.setLayout(user_logoutLayout);
        user_logoutLayout.setHorizontalGroup(
            user_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_logoutLayout.createSequentialGroup()
                .addGroup(user_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(user_logoutLayout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(user_logoutLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addComponent(jLabel1)
                .addContainerGap())
        );
        user_logoutLayout.setVerticalGroup(
            user_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(user_logoutLayout.createSequentialGroup()
                .addGroup(user_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(user_logoutLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(user_logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel17)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        songTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><center>S.No</center></html>", "Name", "Language", "Time", "Artist", "Album", "Genre", "Rating", "Plays"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        songTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songTableMouseClicked(evt);
            }
        });
        songTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                songTablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(songTable);
        if (songTable.getColumnModel().getColumnCount() > 0) {
            songTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        }

        javax.swing.GroupLayout SongLayout = new javax.swing.GroupLayout(Song);
        Song.setLayout(SongLayout);
        SongLayout.setHorizontalGroup(
            SongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE)
        );
        SongLayout.setVerticalGroup(
            SongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
        );

        song_Album_Artist.addTab("Songs", Song);

        Album.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        albumTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Album"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        albumTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                albumTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(albumTable);
        if (albumTable.getColumnModel().getColumnCount() > 0) {
            albumTable.getColumnModel().getColumn(0).setResizable(false);
            albumTable.getColumnModel().getColumn(1).setResizable(false);
        }

        songTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Language", "Plays", "Rating"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        songTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songTable2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                songTable2MouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(songTable2);
        if (songTable2.getColumnModel().getColumnCount() > 0) {
            songTable2.getColumnModel().getColumn(0).setResizable(false);
            songTable2.getColumnModel().getColumn(1).setResizable(false);
            songTable2.getColumnModel().getColumn(2).setResizable(false);
            songTable2.getColumnModel().getColumn(3).setResizable(false);
            songTable2.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setText("Album Title");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(558, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        jLabel6.setText("Album Details");

        javax.swing.GroupLayout Album_detailsLayout = new javax.swing.GroupLayout(Album_details);
        Album_details.setLayout(Album_detailsLayout);
        Album_detailsLayout.setHorizontalGroup(
            Album_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Album_detailsLayout.setVerticalGroup(
            Album_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Album_detailsLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout AlbumLayout = new javax.swing.GroupLayout(Album);
        Album.setLayout(AlbumLayout);
        AlbumLayout.setHorizontalGroup(
            AlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlbumLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
                    .addComponent(Album_details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        AlbumLayout.setVerticalGroup(
            AlbumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(AlbumLayout.createSequentialGroup()
                .addComponent(Album_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 921, Short.MAX_VALUE))
        );

        song_Album_Artist.addTab("Albums", Album);

        Artist.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        artistTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Artist"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        artistTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                artistTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(artistTable);

        albumTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Album", "Album Artist"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        albumTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                albumTable2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(albumTable2);
        if (albumTable2.getColumnModel().getColumnCount() > 0) {
            albumTable2.getColumnModel().getColumn(0).setResizable(false);
            albumTable2.getColumnModel().getColumn(1).setResizable(false);
            albumTable2.getColumnModel().getColumn(2).setResizable(false);
        }

        Artist_details.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("Artist Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(782, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel8.setText("Artist detail");

        javax.swing.GroupLayout Artist_detailsLayout = new javax.swing.GroupLayout(Artist_details);
        Artist_details.setLayout(Artist_detailsLayout);
        Artist_detailsLayout.setHorizontalGroup(
            Artist_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Artist_detailsLayout.setVerticalGroup(
            Artist_detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Artist_detailsLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        AlbumDetails_2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Album title");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(437, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel10.setText("Album Details");

        javax.swing.GroupLayout AlbumDetails_2Layout = new javax.swing.GroupLayout(AlbumDetails_2);
        AlbumDetails_2.setLayout(AlbumDetails_2Layout);
        AlbumDetails_2Layout.setHorizontalGroup(
            AlbumDetails_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
            .addGroup(AlbumDetails_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AlbumDetails_2Layout.setVerticalGroup(
            AlbumDetails_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AlbumDetails_2Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(AlbumDetails_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(AlbumDetails_2Layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 73, Short.MAX_VALUE)))
        );

        songTable3.setBackground(new java.awt.Color(204, 204, 204));
        songTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Language", "avg. Rating", "Plays"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        songTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songTable3MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(songTable3);
        if (songTable3.getColumnModel().getColumnCount() > 0) {
            songTable3.getColumnModel().getColumn(0).setResizable(false);
            songTable3.getColumnModel().getColumn(1).setResizable(false);
            songTable3.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout songLayout = new javax.swing.GroupLayout(song);
        song.setLayout(songLayout);
        songLayout.setHorizontalGroup(
            songLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AlbumDetails_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
        songLayout.setVerticalGroup(
            songLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(songLayout.createSequentialGroup()
                .addComponent(AlbumDetails_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 799, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ArtistLayout = new javax.swing.GroupLayout(Artist);
        Artist.setLayout(ArtistLayout);
        ArtistLayout.setHorizontalGroup(
            ArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ArtistLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ArtistLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(song, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Artist_details, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ArtistLayout.setVerticalGroup(
            ArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(ArtistLayout.createSequentialGroup()
                .addComponent(Artist_details, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(ArtistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(song, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5)))
        );

        song_Album_Artist.addTab("Artists", Artist);

        Genre.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        genreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Genre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        genreTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                genreTableMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(genreTable);
        if (genreTable.getColumnModel().getColumnCount() > 0) {
            genreTable.getColumnModel().getColumn(0).setResizable(false);
            genreTable.getColumnModel().getColumn(1).setResizable(false);
        }

        songTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name", "Language", "Time", "Artist", "Album", "Genre", "Rating", "Plays"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        songTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                songTable4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                songTable4MouseEntered(evt);
            }
        });
        jScrollPane9.setViewportView(songTable4);
        if (songTable4.getColumnModel().getColumnCount() > 0) {
            songTable4.getColumnModel().getColumn(0).setResizable(false);
            songTable4.getColumnModel().getColumn(1).setResizable(false);
            songTable4.getColumnModel().getColumn(2).setResizable(false);
            songTable4.getColumnModel().getColumn(3).setResizable(false);
            songTable4.getColumnModel().getColumn(4).setResizable(false);
            songTable4.getColumnModel().getColumn(5).setResizable(false);
            songTable4.getColumnModel().getColumn(6).setResizable(false);
            songTable4.getColumnModel().getColumn(7).setResizable(false);
            songTable4.getColumnModel().getColumn(8).setResizable(false);
        }

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel15.setText("Genre Name");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(541, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
        );

        jLabel16.setText("Genre Detail");

        javax.swing.GroupLayout Album_details1Layout = new javax.swing.GroupLayout(Album_details1);
        Album_details1.setLayout(Album_details1Layout);
        Album_details1Layout.setHorizontalGroup(
            Album_details1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Album_details1Layout.setVerticalGroup(
            Album_details1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Album_details1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout GenreLayout = new javax.swing.GroupLayout(Genre);
        Genre.setLayout(GenreLayout);
        GenreLayout.setHorizontalGroup(
            GenreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GenreLayout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GenreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
                    .addComponent(Album_details1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        GenreLayout.setVerticalGroup(
            GenreLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
            .addGroup(GenreLayout.createSequentialGroup()
                .addComponent(Album_details1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE))
        );

        song_Album_Artist.addTab("Genre", Genre);

        javax.swing.GroupLayout LibraryLayout = new javax.swing.GroupLayout(Library);
        Library.setLayout(LibraryLayout);
        LibraryLayout.setHorizontalGroup(
            LibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(song_Album_Artist)
        );
        LibraryLayout.setVerticalGroup(
            LibraryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(song_Album_Artist, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        Library_Playlist.addTab("Library", Library);

        search_filters.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextField1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextField1CaretUpdate(evt);
            }
        });

        jButton11.setText("X");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout search_filtersLayout = new javax.swing.GroupLayout(search_filters);
        search_filters.setLayout(search_filtersLayout);
        search_filtersLayout.setHorizontalGroup(
            search_filtersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(search_filtersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        search_filtersLayout.setVerticalGroup(
            search_filtersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, search_filtersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(search_filtersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton10.setText("Buy Song");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton9.setText("Add Songs");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("3");
        jRadioButton3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton3StateChanged(evt);
            }
        });
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton3MouseClicked(evt);
            }
        });
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("1");
        jRadioButton1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton1StateChanged(evt);
            }
        });
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("4");
        jRadioButton4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton4StateChanged(evt);
            }
        });
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton4MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setText("5");
        jRadioButton5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton5StateChanged(evt);
            }
        });
        jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton5MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("2");
        jRadioButton2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jRadioButton2StateChanged(evt);
            }
        });
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton2MouseClicked(evt);
            }
        });

        jButton6.setText("Rate !");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5)
                    .addComponent(jButton6))
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Selected Song");
        jLabel14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jLabel14FocusGained(evt);
            }
        });

        jButton13.setText("Play");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jButton13)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13))
                .addContainerGap())
        );

        javax.swing.GroupLayout Play_searchLayout = new javax.swing.GroupLayout(Play_search);
        Play_search.setLayout(Play_searchLayout);
        Play_searchLayout.setHorizontalGroup(
            Play_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Play_searchLayout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_filters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Play_searchLayout.setVerticalGroup(
            Play_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(search_filters, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout allLayout = new javax.swing.GroupLayout(all);
        all.setLayout(allLayout);
        allLayout.setHorizontalGroup(
            allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(user_logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Play_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Library_Playlist)
        );
        allLayout.setVerticalGroup(
            allLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(allLayout.createSequentialGroup()
                .addComponent(user_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Play_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Library_Playlist))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(all, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(all, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
            
        
    }//GEN-LAST:event_formWindowOpened

    
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        
            dispose();
            new Login(co).setVisible(true);

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
     
    }//GEN-LAST:event_jLabel2MouseClicked

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
      
    }//GEN-LAST:event_formKeyTyped

    private void jTextField1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextField1CaretUpdate
        // TODO add your handling code here:
        search(jTextField1.getText());
        
    }//GEN-LAST:event_jTextField1CaretUpdate

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        
        resetSearch();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        resetSearch();
        new AddSong(co).setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        
        Populate.setUpdateddata();
        
        setCredit();
        resetSearch();
    }//GEN-LAST:event_formWindowGainedFocus

    private void jRadioButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton4MouseClicked

    private void jRadioButton4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton4StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButton4StateChanged

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton3MouseClicked

    private void jRadioButton3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton3StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButton3StateChanged

    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2MouseClicked

    private void jRadioButton2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton2StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButton2StateChanged

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void jRadioButton1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton1StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButton1StateChanged

    private void jRadioButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton5MouseClicked

    private void jRadioButton5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton5StateChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_jRadioButton5StateChanged

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        
        if(jRadioButton1.isSelected()){
            rate(1);
        }
        else if(jRadioButton2.isSelected()){
            rate(2);
        }
        else if(jRadioButton3.isSelected()){
            rate(3);
        }
        else if(jRadioButton4.isSelected()){
            rate(4);
        }
        else if(jRadioButton5.isSelected()){
            rate(5);
        }
        else{
            JOptionPane.showMessageDialog(rootPane,"Select ratng");
        }
        Populate.updateRating();
        search(jTextField1.getText());
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        if(selectedSong!=null){
        String file = JOptionPane.showInputDialog("Enter path to Download folder");
       if(file!=null){
        selectedSong.buy(co, file);
        Populate.updateCredits();}
        }
        else{
            JOptionPane.showMessageDialog(rootPane,"Select a song to buy");
        }
         setCredit();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jLabel14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jLabel14FocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel14FocusGained

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        if(selectedSong!=null){
        selectedSong.play(co);
        }
        else{
            JOptionPane.showMessageDialog(null,"No song Selected");
        }
        setCredit();
         
    }//GEN-LAST:event_jButton13ActionPerformed

    private void songTable4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTable4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_songTable4MouseEntered

    private void songTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTable4MouseClicked
        // TODO add your handling code here:
        setSelectedSong(songTable4,false,false,false);

    }//GEN-LAST:event_songTable4MouseClicked

    private void genreTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_genreTableMouseClicked
        // TODO add your handling code here:
        setSelectedGenre(genreTable);
        populateSongTable4(selectedGenre.getsongs());
        jLabel15.setText(selectedGenre.name);
        jLabel16.setText(selectedGenre.details);
        //        int row = genreTable.rowAtPoint(evt.getPoint());
        //        int col = genreTable.columnAtPoint(evt.getPoint());
        //        if (row >= 0 && col >= 0) {
            //            DefaultTableModel model = (DefaultTableModel) genreTable.getModel();
            //            Genre selected = Populate.GenreSearch.get((model.getValueAt(row, 1).toString()));
            //            System.out.println(selected);
            //            populateSongTable4(selected.getsongs());
            //
            //        }

    }//GEN-LAST:event_genreTableMouseClicked

    private void songTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTable3MouseClicked
        // TODO add your handling code here:
        setSelectedSong(songTable3,false,false,true);
        
    }//GEN-LAST:event_songTable3MouseClicked

    private void albumTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_albumTable2MouseClicked
        // TODO add your handling code here:
        setSelectedAlbum(albumTable2,false);
 
        populateSongTable3(selectedAlbum.getsongs());
        jLabel9.setText(selectedAlbum.name);
        jLabel10.setText(selectedAlbum.type);
        
        //        int row = albumTable2.rowAtPoint(evt.getPoint());
        //        int col = albumTable2.columnAtPoint(evt.getPoint());
        //        if (row >= 0 && col >= 0) {
            //            DefaultTableModel model = (DefaultTableModel) albumTable2.getModel();
            //            Album selected = Populate.AlbumSearch.get(Integer.parseInt(model.getValueAt(row, 0).toString()));
            //            populateSongTable3(selected.getsongs());
            //
            //        }
    }//GEN-LAST:event_albumTable2MouseClicked

    private void artistTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_artistTableMouseClicked
        // TODO add your handling code here:
        setSelectedArtist(artistTable);
        populateAlbumTable2(selectedArtist.getalbums());
        jLabel7.setText(selectedArtist.name);
        jLabel8.setText(selectedArtist.origin+ "        "+selectedArtist.description );
        //        int row = artistTable.rowAtPoint(evt.getPoint());
        //        int col = artistTable.columnAtPoint(evt.getPoint());
        //        if (row >= 0 && col >= 0) {
            //            DefaultTableModel model = (DefaultTableModel) artistTable.getModel();
            //            Artist selected = Populate.ArtistSearch.get(Integer.parseInt(model.getValueAt(row, 0).toString()));
            //            populateAlbumTable2(selected.getalbums());
            //
            //        }
    }//GEN-LAST:event_artistTableMouseClicked

    private void songTable2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTable2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_songTable2MouseEntered

    private void songTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTable2MouseClicked
        // TODO add your handling code here:
        setSelectedSong(songTable2,false,false,true);

    }//GEN-LAST:event_songTable2MouseClicked

    private void albumTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_albumTableMouseClicked
        // TODO add your handling code here:
        setSelectedAlbum(albumTable,true);
        jLabel5.setText(selectedAlbum.name);
        jLabel6.setText(selectedAlbum.type);
        populateSongTable2(selectedAlbum.getsongs());
        //        int row = albumTable.rowAtPoint(evt.getPoint());
        //        int col = albumTable.columnAtPoint(evt.getPoint());
        //        if (row >= 0 && col >= 0) {
            //            DefaultTableModel model = (DefaultTableModel) albumTable.getModel();
            //            Album selected = Populate.AlbumSearch.get(Integer.parseInt(model.getValueAt(row, 0).toString()));
            //            System.out.println(selected);
            //            populateSongTable2(selected.getsongs());
            //
            //        }
    }//GEN-LAST:event_albumTableMouseClicked

    private void songTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_songTablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_songTablePropertyChange

    private void songTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_songTableMouseClicked
        // TODO add your handling code here:
        setSelectedSong(songTable,true,false,false);

    }//GEN-LAST:event_songTableMouseClicked

    void resetSearch(){
        jTextField1.setText("");
        search("");
    }
    void search(String qu){
        
        populateSongTable(one.searchSongs(qu));
        populateAlbumTable(one.searchAlbums(qu));
        populateArtistTable(one.searchArtists(qu));
        populateGenreTable(one.searchGenres(qu));
        
        
    }
    
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Album;
    private javax.swing.JPanel AlbumDetails_2;
    private javax.swing.JPanel Album_details;
    private javax.swing.JPanel Album_details1;
    private javax.swing.JPanel Artist;
    private javax.swing.JPanel Artist_details;
    private javax.swing.JPanel Genre;
    private javax.swing.JPanel Library;
    private javax.swing.JTabbedPane Library_Playlist;
    private javax.swing.JPanel Play_search;
    private javax.swing.JPanel Song;
    private javax.swing.JTable albumTable;
    private javax.swing.JTable albumTable2;
    private javax.swing.JPanel all;
    private javax.swing.JTable artistTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable genreTable;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel search_filters;
    private javax.swing.JPanel song;
    private javax.swing.JTable songTable;
    private javax.swing.JTable songTable2;
    private javax.swing.JTable songTable3;
    private javax.swing.JTable songTable4;
    private javax.swing.JTabbedPane song_Album_Artist;
    private javax.swing.JPanel user_logout;
    // End of variables declaration//GEN-END:variables
}
