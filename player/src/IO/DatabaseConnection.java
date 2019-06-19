package IO;

import Model.Song;

import java.io.File;
import java.sql.*;

public class DatabaseConnection {
    private Connection connection = null;

    public DatabaseConnection(String databaseName) {
        try {
            connection = DriverManager.getConnection("player" + File.separator + "src" + File.separator + "resources" + File.separator + databaseName + ".db");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
                .append("length integer,\n").append("playCount integer,\n").append("playDate TEXT,\n").append("releaseDate Text\n);").toString();
        createTable(songQuery);
    }

    public void insertSong(Song song){
        String query = "INSERT INTO Songs(hash,title,artist,album,length,playCount,playDate,releaseDate) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, song.getHash());
            statement.setString(2, song.getTitle());
            statement.setString(3,song.getArtist());
            statement.setString(4, song.getAlbum());
            statement.setInt(5, song.getLength());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
