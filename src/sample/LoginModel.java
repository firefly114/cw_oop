package sample;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;

public class LoginModel {
    Connection c;

    public LoginModel() {
        c = SqLiteConnection.Connector();
        if(c== null) System.exit(1);
    }

    public boolean isDbConnected() {
        try {
            return !c.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String username, String password) throws SQLException {
        PreparedStatement ps = null;
        String query = "SELECT * FROM Users WHERE username = ? and passhash = ?";
        ps = c.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            if(rs.next()) return true;
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            ps.close();
            rs.close();
        }
    }

    public void new_User(String username, String password) throws SQLException {
        String query = "INSERT INTO Users(username , passhash) VALUES (?, ?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, password);
        try {
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUser(String username) throws SQLException{
        String query = "SELECT * FROM Users WHERE username = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            int id = rs.getInt("id");
            double money = rs.getDouble("money");
            double moneyOP = rs.getDouble("moneyOP");
            rs.close();
            ps.close();
            return new User(username, id, money, moneyOP);
        }
        return null;
    }

    public Car getCar(String username) throws SQLException{
        PreparedStatement ps = null;
        String query = "SELECT * FROM Cars WHERE username = ?";
        ps = c.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            String title = rs.getString("title");
            double a = rs.getDouble("Acceleration");
            double m = rs.getDouble("MaxSpeed");
            double b = rs.getDouble("Brakes");
            int u = rs.getInt("isUpgraded");
            HashMap<String, Upgrades> upg = new HashMap<>();
            if(rs.getInt("Nitro")==1) upg.put("Nitro",new Nitro());
            if(rs.getInt("Spoiler")==1) upg.put("Spoiler", new Spoiler());
            if(rs.getInt("Tires")==1) upg.put("Tires", new Tires());
            rs.close();
            ps.close();
            return new Car(title,a,m,b,u, upg);
        }
        return null;
    }
}
