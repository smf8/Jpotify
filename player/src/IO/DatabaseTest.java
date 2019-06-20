package IO;

import Model.Album;
import Model.Song;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class DatabaseTest {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        DatabaseConnection connection = new DatabaseConnection("test");
        connection.initSqlTables();
//        TagReader reader = new TagReader();
//        URL fileUrl = Path.of("/home/smf8/IdeaProjects/Jpotify/player/src/resources/test/Lamb of God - Laid to Rest.mp3").toUri().toURL();
//        reader.getAdvancedTags(fileUrl);
//        LocalDateTime time = LocalDateTime.now();
//        Song song = new Song(reader.getTitle(),reader.getArtist(),reader.getAlbum(),reader.getDurationInSeconds(),0,time, fileUrl.toURI(), false,false , time);
//        connection.insertSong(song);


        // initialize song table with songs inside a folder
        File dir = new File("player/src/resources");
        Song.saveSongs(Song.findSongsInFolder(dir), connection.getConnection());

        // initialize albums table with songs inside a folder
        Album.saveAlbums(Album.createAlbumFromFolder(dir), connection.getConnection());
        // don't forget to close database after usage
//        connection.close();
    }
}
