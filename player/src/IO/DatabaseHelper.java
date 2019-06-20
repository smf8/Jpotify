package IO;

import Model.Album;
import Model.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * helper class for database queries <br>
 * <b>use methods in another thread</b>
 */
public class DatabaseHelper {
    private Connection connection;

    public DatabaseHelper(Connection connection) {
        this.connection = connection;
    }

    /**
     * code is pretty self explanatory, uses preparedStatement to execute query
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
                LocalDateTime localDateTime = song.getPlayDate();
                String playDate = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
                statement.setString(7, playDate);
                statement.setInt(8, song.getReleasedDate());
                statement.setString(9, song.getLocation().toString());
                String artwork = song.getArtWork()==null?"":song.getArtWork().toString();
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

    /**
     * insertion of album
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
            if (album.getImageURI() != null){
                image = album.getImageURI().toString();
            }
            statement.setString(3, image);
            statement.setInt(4, album.isPublic() ? 1 : 0);
            StringBuilder builder = new StringBuilder();
            for (Song s : album.getSongs()) {
                builder.append(s.getHash()).append("|");
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
