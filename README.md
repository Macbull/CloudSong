# CloudSong
This project was developed for the course of OOP concepts with Java and DBMS 

The beauty of this project lies in the Data Model Design, which was my primary focus while developing this project. It is highly intutive. 

Some other highlights are : 
- Intutive data model
- a single common connection to database for complete instance of application
- all data retrieval from database is done in single class, populate.java, (another beauty in itself, uses static functions, variables, and again beautiful use of polymorphism)
- all data modifiation and creation is handled by updateData.java 
- All data once retrieved is stored on hashmaps
- Spontaneous search is handled in a beautiful way, and search results are stored in seperate sets of hashmaps. One global search, searches for all matching album, song, artist, genre.
- User can rate a song.
- Average rating of that song is shown.
- Higly intutive ER Diagram
- Songs are stored in Blobs
- Songs are played via VLC, ( # needs improvement)

- A fun credit system is implemented as :
  - You will get credits on listening a song
  - You will get credits on adding a song
  - You will be decredited on downloading a song
