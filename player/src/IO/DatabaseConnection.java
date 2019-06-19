package IO;

import Model.Song;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseConnection {
    private Connection connection = null;

    public DatabaseConnection(String databaseName) {
        try {
            Path path = Paths.get("player" + File.separator + "src" + File.separator + "resources");
            String url = "jdbc:sqlite:" + path.toAbsolutePath() + File.separator + databaseName + ".db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(String sql) {
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initSqlTables(){
        // create Song table
        String songQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS Songs(").append("hash TEXT PRIMARY KEY,\n").append("title TEXT,\n").append("artist TEXT,\n").append("album TEXT,\n")
                .append("length integer,\n").append("playCount integer,\n").append("playDate TEXT,\n").append("releaseDate Text,\n").append("location TEXT\n);").toString();
        createTable(songQuery);
    }

    public void insertSong(Song song){
        String query = "INSERT INTO Songs(hash,title,artist,album,length,playCount,playDate,releaseDate,location) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, song.getHash());
            statement.setString(2, song.getTitle());
            statement.setString(3,song.getArtist());
            statement.setString(4, song.getAlbum());
            statement.setInt(5, song.getLength());
            statement.setInt(6, song.getPlayCount());
            LocalDateTime localDateTime = song.getPlayDate();
            String playDate = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
            String releasedDate = song.getReleasedDate().format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
            statement.setString(7, playDate);
            statement.setString(8, releasedDate);
            statement.setString(9, song.getLocation().toString());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void close(){
        if (connection !=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
