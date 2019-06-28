package utils.IO;

import Model.Album;
import Model.Playlist;
import Model.Song;
import Model.User;

import java.net.URI;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * helper class for database queries <br>
 * <b>use methods in another thread</b><br>
 * do not forget to call close method after database queries have finished
 */
public class DatabaseHelper implements DatabaseHandler {
    private Connection connection;

    public DatabaseHelper(Connection connection) {
        this.connection = connection;
    }

    public void deleteAlbum() {

    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * this method removes song from database and alters album and playlist's tables
     *
     * @param song the song to be removed
     */
    public void removeSong(Song song) {
        //removing song from Songs table
        String query = "DELETE FROM Songs WHERE hash=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, song.getHash());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // removing this song from all playlists and albums
        PreparedStatement playlistStatement = null;
        try {
            playlistStatement = connection.prepareStatement("SELECT id,songs FROM Playlists WHERE songs LIKE ?");
            playlistStatement.setString(1, "%" + song.getHash() + "%");
            ResultSet resultSet = playlistStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("songs").contains(song.getHash())) {
                    String newHash = resultSet.getString("songs").replace(song.getHash() + Song.HASH_SEPERATOR, "");
                    playlistStatement = connection.prepareStatement("UPDATE Playlists SET songs=? WHERE id=?");
                    playlistStatement.setString(1, newHash);
                    playlistStatement.setInt(2, resultSet.getInt("id"));
                    playlistStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                playlistStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //removing the song from all the album that contains this song
        PreparedStatement albumStatement = null;
        try {
            albumStatement = connection.prepareStatement("SELECT id,songs FROM Albums WHERE songs LIKE ?");
            albumStatement.setString(1, "%" + song.getHash() + "%");
            ResultSet resultSet = albumStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("songs").contains(song.getHash())) {
                    String newHash = resultSet.getString("songs").replace(song.getHash() + Song.HASH_SEPERATOR, "");
                    albumStatement = connection.prepareStatement("UPDATE Albums SET songs=? WHERE id=?");
                    albumStatement.setString(1, newHash);
                    albumStatement.setInt(2, resultSet.getInt("id"));
                    albumStatement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                albumStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeSongFromAlbum(Song song, Album album) {

        String query = "UPDATE Albums SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            ArrayList<Song> newSongs = album.getSongs();
            newSongs.remove(song);
            for (Song s : album.getSongs()) {
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(1, builder.toString());
            statement.setInt(2, album.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void removeSongFromPlaylist(Song song, Playlist playlist) {

        String query = "UPDATE Playlists SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            ArrayList<Song> newSongs = playlist.getSongs();
            newSongs.remove(song);
            for (Song s : playlist.getSongs()) {
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            System.out.println(builder.toString() + "----");
            statement.setString(1, builder.toString());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        String query = "UPDATE Playlists SET songs=? WHERE id=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            StringBuilder builder = new StringBuilder();
            for (Song s : playlist.getSongs()) {
                builder.append(s.getHash()).append(Song.HASH_SEPERATOR);
            }
            builder.append(song.getHash()).append(Song.HASH_SEPERATOR);
            statement.setString(1, builder.toString());
            statement.setInt(2, playlist.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    public void deepInsertSong(ArrayList<Song> songs){
        String query = "INSERT OR IGNORE INTO Songs(hash,title,artist,album,length,playCount,playDate,releaseDate,location,artwork) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        boolean succ = true;
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
                PreparedStatement albumStatement = null;
                try {
                    albumStatement = connection.prepareStatement("SELECT id,songs FROM Albums WHERE title=?");
                    albumStatement.setString(1,song.getAlbum());
                    ResultSet resultSet = albumStatement.executeQuery();
                    boolean flag = false;
                    while (resultSet.next()) {
                        flag = true;
                        if (!resultSet.getString("songs").contains(song.getHash())) {
                            String newHash = resultSet.getString("songs") + song.getHash() + Song.HASH_SEPERATOR;
                            albumStatement = connection.prepareStatement("UPDATE Albums SET songs=? WHERE id=?");
                            albumStatement.setString(1, newHash);
                            albumStatement.setInt(2, resultSet.getInt("id"));
                            albumStatement.execute();
                        }
                    }
                    if (!flag){
                        Album album = new Album(0,song.getAlbum(), song.getArtist(), song.getArtWork());
                        album.addSong(song);
                        insertAlbum(album);
                    }
                } catch (SQLException e) {
                    // couldn't update the album table so we have to create it
                    e.printStackTrace();
                } finally {
                    try {
                        albumStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (SQLException e) {
            succ = false;
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

    public Playlist getPlaylistByID(int ID) {
        String sql = "SELECT * FROM Playlists WHERE id = ?";
        Playlist playlist = null;

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
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

    public Album getAlbumByID(int ID) {
        String sql = "SELECT * FROM Albums WHERE id = ?";
        Album album = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, ID);
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

    public int insertPlaylist(Playlist playlist) {
        int id = 0;
        String query = "INSERT OR IGNORE INTO Playlists(title, creator, artwork,public,editable,songs) VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = (int) generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
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
        return id;
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

    /**
     * gets Usernames with username like the enterd username
     * if you enter the exact username get the user object by get(0)<br>
     * <b>the user does' not contain friend information</b>
     * <br>
     * <b>this method uses several other queries to finish so make sure to use it inside another thread</b>
     *
     * @param username the username of the user
     * @return an arraylist of user objects with similar username
     */
    public ArrayList<User> getUserByUsername(String username, DatabaseHandler handler) {
        String query = "SELECT * FROM Users WHERE username LIKE ?";
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + username + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("username"), resultSet.getString("password"));
                ArrayList<Song> recentSongs = new ArrayList<>();
                ArrayList<Song> likedSongs = new ArrayList<>();
                if (!resultSet.getString("albums").equals("")) {
                    for (String song : resultSet.getString("recentlyPlayed").split(Song.HASH_SEPERATOR)) {
                        Song foundSong = handler.getSongByHash(song);
                        if (foundSong != null) {
                            recentSongs.add(foundSong);
                        }
                    }
                }
                if (!resultSet.getString("likedSongs").equals("")) {
                    for (String song : resultSet.getString("likedSongs").split(Song.HASH_SEPERATOR)) {
                        Song foundSong = handler.getSongByHash(song);
                        if (foundSong != null) {
                            likedSongs.add(foundSong);
                        }
                    }
                }
                ArrayList<Album> albums = new ArrayList<>();
                ArrayList<Playlist> playlists = new ArrayList<>();
                if (!resultSet.getString("albums").equals("")) {
                    for (String album : resultSet.getString("albums").split(Song.HASH_SEPERATOR)) {
                        Album foundAlbum = handler.getAlbumByID(Integer.parseInt(album));
                        if (foundAlbum != null) {
                            albums.add(foundAlbum);
                        }
                    }
                }
                if (!resultSet.getString("playlists").equals("")) {
                    for (String playlist : resultSet.getString("playlists").split(Song.HASH_SEPERATOR)) {
                        Playlist foundPlaylist = handler.getPlaylistByID(Integer.parseInt(playlist));
                        if (foundPlaylist != null) {
                            playlists.add(foundPlaylist);
                        }
                    }
                }
                user.setAlbums(albums);
                user.setPlaylists(playlists);
                user.setLikedSongs(likedSongs);
                user.setOnline(resultSet.getInt("online")==1);
                user.setLastOnline(resultSet.getLong("lastOnline"));
                user.setSongs(recentSongs);
                user.setCurrentSong(null);
                user.setFriends(resultSet.getString("friends"));
                user.setProfileImage(URI.create(resultSet.getString("profileImage")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void removeUser(String username) {
        String query = "DELETE FROM Users WHERE username = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * add a user to database
     *
     * @param user the user object
     */
    public boolean addUser(User user) {
        boolean success = true;
        String query = "INSERT OR IGNORE INTO Users(username, likedSongs, recentlyPlayed, password,profileImage,albums, playlists, friends,online,lastOnline) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            StringBuilder builder = new StringBuilder();
            for (Song song : user.getLikedSongs()) {
                if (!song.getHash().equals("")) {
                    builder.append(song.getHash()).append(Song.HASH_SEPERATOR);
                }
            }
            statement.setString(2, builder.toString());
            builder = new StringBuilder();
            for (Song song : user.getSongs()) {
                if (!song.getHash().equals("")) {
                    builder.append(song.getHash()).append(Song.HASH_SEPERATOR);
                }
            }
            statement.setString(3, builder.toString());

            statement.setString(4, user.getPassword());

            statement.setString(5, user.getProfileImage().toString());
            builder = new StringBuilder();
            for (Album album : user.getAlbums()) {
                builder.append(album.getId()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(6, builder.toString());
            builder = new StringBuilder();
            for (Playlist playlist : user.getPlaylists()) {
                builder.append(playlist.getId()).append(Song.HASH_SEPERATOR);
            }
            statement.setString(7, builder.toString());
            builder = new StringBuilder();
            if (user.getFriendsList() != null) {
                for (User friends : user.getFriendsList()) {
                    builder.append(friends.getUsername()).append(Song.HASH_SEPERATOR);
                }
            }
            statement.setString(8, builder.toString());

            statement.setInt(9, user.isOnline()?1:0);
            statement.setLong(10, user.getLastOnline());
            statement.execute();
        } catch (SQLException e) {
            success = false;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                success = false;
            }
        }
        return success;
    }

    public void updateSong(Song song){
        String query = "UPDATE Songs set playDate = ? WHERE hash = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, LocalDate.now().toString());
            statement.setString(2, song.getHash());
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
