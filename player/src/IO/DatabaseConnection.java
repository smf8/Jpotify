package IO;

import Model.Song;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Base class for database Reading / Writing
 * use init and insert and query methods inside a new thread to avoid main thread lock
 *
 * <b>don't forget to involve close method after completing queries</b>
 */
public class DatabaseConnection {
    private Connection connection = null;

    /**
     * constructor to create database file in resources folder
     * method uses sqlite jdbc driver
     * @param databaseName database file name
     */
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

    public synchronized void initSqlTables(){
        // create Song table
        String songQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS Songs(").append("hash TEXT PRIMARY KEY,\n").append("title TEXT,\n").append("artist TEXT,\n").append("album TEXT,\n")
                .append("length integer,\n").append("playCount integer,\n").append("playDate TEXT,\n").append("releaseDate integer,\n").append("location TEXT,\n artwork TEXT\n);").toString();
        createTable(songQuery);
        // creating albums table
        String albumQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS Albums(").append("id integer PRIMARY KEY AUTOINCREMENT,\n").append("title TEXT,\n").append("artist TEXT,\n").append("artwork TEXT,\n").append("public integer,\n").append("songs TEXT,\n unique(title,artist));").toString();
        createTable(albumQuery);
        String playlistQuery = new StringBuilder("CREATE TABLE IF NOT EXISTS Playlists(").append("id integer PRIMARY KEY AUTOINCREMENT,\n").append("title TEXT,\n").append("creator TEXT,\n").append("artwork TEXT,\n").append("public integer,\n").append("editable integer,\n").append("songs TEXT,\n unique(title));").toString();
        createTable(playlistQuery);
    }


    public synchronized void close(){
        if (connection !=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
