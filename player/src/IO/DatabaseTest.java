package IO;

import java.io.File;

public class DatabaseTest {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection("test");
        connection.initSqlTables();
    }
}
