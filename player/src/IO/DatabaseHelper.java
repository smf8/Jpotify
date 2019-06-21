package IO;

import Model.Album;
import Model.Playlist;
import Model.Song;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * helper class for database queries <br>
 * <b>use methods in another thread</b><br>
 * do not forget to call close method after database queries have finished
 */
public class DatabaseHelper {
    private Connection connection;

    public DatabaseHelper(Connection connection) {
        this.connection = connection;
    }
    public void deleteAlbum(){

    }
    public void removeSong(Song song){

        String query = "DELETE FROM Songs WHERE hash=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, song.getHash());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // removing this song from all playlists and albums
        ArrayList<Playlist> playlists = searchPlaylist("%");
        PreparedStatement playlistStatement = null;
        try {
            playlistStatement = connection.prepareStatement("SELECT id,songs FROM Playlists");
            ResultSet resultSet = playlistStatement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getString("songs").contains(song.getHash())){
                    String newHash = resultSet.getString("songs").replace(song.getHash() + Song.HASH_SEPERATOR, "");
                    playlistStatement = connection.prepareStatement("UPDATE Playlists SET songs=? WHERE id=?");
                    playlistStatement.setString(1, newHash);
                    playlistStatement.setInt(2,resultSet.getInt("id"));
                    playlistStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                playlistStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PreparedStatement albumStatement = null;
        try {
            albumStatement = connection.prepareStatement("SELECT id,songs FROM Albums");
            ResultSet resultSet = albumStatement.executeQuery();
            while(resultSet.next()){
                if (resultSet.getString("songs").contains(song.getHash())) {
                    String newHash = resultSet.getString("songs").replace(song.getHash() + Song.HASH_SEPERATOR, "");
                    albumStatement = connection.prepareStatement("UPDATE Albums SET songs=? WHERE id=?");
                    albumStatement.setString(1,newHash);
                    albumStatement.setInt(2,resultSet.getInt("id"));
                    albumStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                albumStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        ArrayList<Album> albums = searchAlbum("%");
//        for (Playlist playlist : playlists){
//            System.out.println(playlist.getSongs().size());
//            System.out.println(playlist.getSongs().get(5).getTitle());
//            for (Song s : playlist.getSongs()){
//                System.out.println(s.getTitle());
//                if (s.equals(song)) {
//                    removeSongFromPlaylist(song, playlist);
//                }
//            }
//        }
//        for (Album album : albums){
//            for(Song s : album.getSongs()){
//                if (s.equals(song)) {
//                    removeSongFromAlbum(song, album);
//                }
//            }
//        }
    }

    public void removeSongFromAlbum(Song song,Album album){

        String query = "UPDATE Albums SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            ArrayList<Song> newSongs = album.getSongs();
            newSongs.remove(song);
            for (Song s : album.getSongs()){
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(1, builder.toString());
            statement.setInt(2,album.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void removeSongFromPlaylist(Song song, Playlist playlist){

        String query = "UPDATE Playlists SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            ArrayList<Song> newSongs = playlist.getSongs();
            newSongs.remove(song);
            for (Song s : playlist.getSongs()){
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            System.out.println(builder.toString() + "----");
            statement.setString(1, builder.toString());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void addSongToPlaylist(Song song, Playlist playlist){
            String query = "UPDATE Playlists SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            for (Song s : playlist.getSongs()){
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            builder.append(song.getHash()).append(Song.HASH_SEPERATOR);
            statement.setString(1, builder.toString());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * code is pretty self explanatory, uses preparedStatement to execute query
     * <b>for large scale insertion use the method inside another thread to avoid UI lock </b>
     *
     * @param songs An arraylist of songs to be added
     */
    public void insertSongs(ArrayList<Song> songs) {
        String query = "INSERT OR IGNORE INTO Songs(hash,title,artist,album,length,playCount,playDate,releaseDate,location,artwork) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            for (Song song : songs) {

                statement = connection.prepareStatement(query);
                statement.setString(1, song.getHash());
                statement.setString(2, song.getTitle());
                statement.setString(3, song.getArtist());
                statement.setString(4, song.getAlbum());
                statement.setInt(5, song.getLength());
                statement.setInt(6, song.getPlayCount());
                LocalDate LocalDate = song.getPlayDate();
                String playDate = LocalDate.format(DateTimeFormatter.ofPattern(Song.DATE_FORMAT));
                statement.setString(7, playDate);
                statement.setInt(8, song.getReleasedDate());
                statement.setString(9, song.getLocation().toString());
                String artwork = song.getArtWork() == null ? "" : song.getArtWork().toString();
                statement.setString(10, artwork);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Playlist getPlaylistByName(String title) {
        String sql = "SELECT * FROM Playlists WHERE title = ?";
        Playlist playlist = null;

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            playlist = new Playlist(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("creator"), URI.create(resultSet.getString("artwork")), resultSet.getInt("public") == 1, resultSet.getInt("editable") == 1);
            String[] songHash = resultSet.getString("songs").split(Song.HASH_SEPERATOR);
            for (String hash : songHash) {
//                System.out.println(hash);
                playlist.addSong(getSongByHash(hash));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return playlist;
    }

    /**
     * get an album by it's name ftom database<br>
     * this method internally uses another sqlite query for retrieving album's songs so make sure to <b>use it inside another thread</b>
     *
     * @param title title of the album
     * @return returns an Album object with it's songs inside
     */
    public Album getAlbumByTitle(String title) {
        String sql = "SELECT * FROM Albums WHERE title = ?";
        Album album = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            album = new Album(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("artist"), URI.create(resultSet.getString("artwork")));
            String[] songHash = resultSet.getString("songs").split(Song.HASH_SEPERATOR);
            for (String hash : songHash) {
//                System.out.println(hash);
                album.addSong(getSongByHash(hash));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return album;
    }

    /**
     * get song by it's title, if 2 songs where found by the same title it will return the one with lower ID<br>
     * <b>for large scale insertion use the method inside another thread to avoid UI lock </b>
     *
     * @param title title of the song
     * @return
     */
    public Song getSongByTitle(String title) {
        String sql = "SELECT * FROM Songs WHERE title = ?";
        Song song = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Song.DATE_FORMAT);
            LocalDate dateTime = LocalDate.parse(resultSet.getString("playDate"), formatter);
            song = new Song(resultSet.getString("title"), resultSet.getString("artist"), resultSet.getString("album"), resultSet.getInt("length"), resultSet.getInt("playCount"), dateTime, URI.create(resultSet.getString("location")), false, false, resultSet.getInt("releaseDate"), URI.create(resultSet.getString("artwork")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return song;
    }

    /**
     * gets song by it's hash <br>
     * <b>for large scale insertion use the method inside another thread to avoid UI lock </b>
     *
     * @param hash hash string of the song.
     * @return
     */

    public Song getSongByHash(String hash) {
        String sql = "SELECT * FROM Songs WHERE hash = ?";
        Song song = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, hash);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next())
                return null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Song.DATE_FORMAT);
            LocalDate dateTime = LocalDate.parse(resultSet.getString("playDate"), formatter);
            song = new Song(resultSet.getString("title"), resultSet.getString("artist"), resultSet.getString("album"), resultSet.getInt("length"), resultSet.getInt("playCount"), dateTime, URI.create(resultSet.getString("location")), false, false, resultSet.getInt("releaseDate"), URI.create(resultSet.getString("artwork")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return song;
    }


    /**
     * insertion of album
     *
     * @param album
     */
    public void insertAlbum(Album album) {
        String query = "INSERT OR IGNORE INTO Albums(title, artist, artwork,public,songs) VALUES(?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, album.getTitle());
            statement.setString(2, album.getArtist());
            String image = "";
            if (album.getImageURI() != null) {
                image = album.getImageURI().toString();
            }
            statement.setString(3, image);
            statement.setInt(4, album.isPublic() ? 1 : 0);
            StringBuilder builder = new StringBuilder();
            for (Song s : album.getSongs()) {
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(5, builder.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertPlaylist(Playlist playlist) {
        String query = "INSERT OR IGNORE INTO Playlists(title, creator, artwork,public,editable,songs) VALUES(?,?,?,?,?,?)";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, playlist.getTitle());
            statement.setString(2, playlist.getCreator());
            String image = "";
            if (playlist.getImageURI() != null) {
                image = playlist.getImageURI().toString();
            }
            statement.setString(3, image);
            statement.setInt(4, playlist.isPublic() ? 1 : 0);
            statement.setInt(5, playlist.isEditable() ? 1 : 0);
            StringBuilder builder = new StringBuilder();
            for (Song s : playlist.getSongs()) {
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(6, builder.toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<Album> searchAlbum(String searchQuery) {
        String sql = "SELECT * FROM Albums WHERE title LIKE ? OR artist LIKE ?";
        ArrayList<Album> albums = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(2, "%" + searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Album al = new Album(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("artist"), URI.create(resultSet.getString("artwork")));
                String[] songHash = resultSet.getString("songs").split(Song.HASH_SEPERATOR);
                for (String hash : songHash) {
                    al.addSong(getSongByHash(hash));
                }
                albums.add(al);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return albums;
    }

    public ArrayList<Playlist> searchPlaylist(String searchItem) {
        String sql = "SELECT * FROM Playlists WHERE title LIKE ? OR creator LIKE ?";
        ArrayList<Playlist> playlists = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchItem + "%");
            statement.setString(2, "%" + searchItem + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Playlist pl = new Playlist(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("creator"), URI.create(resultSet.getString("artwork")), resultSet.getInt("public") == 1, resultSet.getInt("editable") == 1);
                String[] songHash = resultSet.getString("songs").split(Song.HASH_SEPERATOR);
                for (String hash : songHash) {
                    pl.addSong(getSongByHash(hash));
                }
                playlists.add(pl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return playlists;
    }

    public ArrayList<Song> searchSong(String searchItem) {

        String sql = "SELECT * FROM Songs WHERE title LIKE ? OR album LIKE ? OR artist LIKE ?";
        ArrayList<Song> songs = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchItem + "%");
            statement.setString(2, "%" + searchItem + "%");
            statement.setString(3, "%" + searchItem + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Song.DATE_FORMAT);
                LocalDate dateTime = LocalDate.parse(resultSet.getString("playDate"), formatter);
                songs.add(new Song(resultSet.getString("title"), resultSet.getString("artist"), resultSet.getString("album"), resultSet.getInt("length"), resultSet.getInt("playCount"), dateTime, URI.create(resultSet.getString("location")), false, false, resultSet.getInt("releaseDate"), URI.create(resultSet.getString("artwork"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return songs;
    }


    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
