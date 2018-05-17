package sample;
import java.sql.*;
public class SqLiteConnection {
    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:/home/firefly/java/cw_oop/db");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
