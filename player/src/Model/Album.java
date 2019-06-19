package Model;

import IO.FileIO;
import utils.TagReader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;

public class Album extends Playlist{
    private String artist;
    public Album(int id, String title, String artist) {
        super(id, title);
        this.artist = artist;
    }
    public Album(int id, String title,String artist, URI artWork){
        super(id, title, artWork);
        this.artist = artist;
    }

//    public ArrayList<Album> createAlbumFromFolder(File folder){
//        ArrayList<URI> files = FileIO.findMP3Files(FileIO.findFilesRecursive(folder));
//        ArrayList<Album> results = new ArrayList<>();
//        for (URI i :files){
//            TagReader musicFileReader = new TagReader();
//            try {
//                musicFileReader.getAdvancedTags(i.toURL());
//                if (musicFileReader.getAlbum())
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
