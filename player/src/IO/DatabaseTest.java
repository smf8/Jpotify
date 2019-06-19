package IO;

import Model.Album;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class DatabaseTest {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
//        DatabaseConnection connection = new DatabaseConnection("test");
//        connection.initSqlTables();
//        TagReader reader = new TagReader();
//        URL fileUrl = Path.of("/home/smf8/IdeaProjects/Jpotify/player/src/resources/test/Lamb of God - Laid to Rest.mp3").toUri().toURL();
//        reader.getAdvancedTags(fileUrl);
//        LocalDateTime time = LocalDateTime.now();
//        Song song = new Song(reader.getTitle(),reader.getArtist(),reader.getAlbum(),reader.getDurationInSeconds(),0,time, fileUrl.toURI(), false,false , time);
//        connection.insertSong(song);
        File dir = new File("player/src/resources");
        ArrayList<Album> albums = Album.createAlbumFromFolder(dir);
        System.out.println(albums.size());
        for (Album a : albums){
            System.out.println(a.getTitle());
        }
    }
}
