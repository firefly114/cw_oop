package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MenuModel {
    Connection c;

    public MenuModel() {
        c = SqLiteConnection.Connector();
        if(c== null) System.exit(1);
    }

    public void updateUser(User u) throws SQLException {
        String query = "UPDATE Users SET money = ?, moneyOP = ? WHERE username = ?";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setDouble(1,u.money);
        ps.setDouble(2,u.moneyOnPush);
        ps.setString(3,u.getUsername());
        try {
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void newCar(Car car, String username) throws SQLException {
        String query = "INSERT INTO Cars(username , title, Acceleration, MaxSpeed, Brakes) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1,username);
        ps.setString(2,car.title);
        ps.setDouble(3,car.acceleration);
        ps.setDouble(4,car.maxSpeed);
        ps.setDouble(5,car.brakes);
        try {
            ps.execute();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateCar(Car car, String username) throws SQLException {
        PreparedStatement ps = null;
        String query = "UPDATE Cars SET title = ?, Acceleration = ?, MaxSpeed = ?, Brakes = ?, Tires= ?, Spoiler = ?, Nitro = ?, isUpgraded = ? WHERE username = ?";
        ps = c.prepareStatement(query);
        ps.setString(1,car.title);
        ps.setDouble(2,car.acceleration);
        ps.setDouble(3,car.maxSpeed);
        ps.setDouble(4,car.brakes);

        if(car.upgrades.containsKey("Tires"))
            ps.setInt(5,1);
        else
            ps.setInt(5,0);

        if(car.upgrades.containsKey("Spoiler"))
            ps.setInt(6,1);
        else
            ps.setInt(6,0);

        if(car.upgrades.containsKey("Nitro"))
            ps.setInt(7,1);
        else
            ps.setInt(7,0);

        ps.setInt(8,car.isUpgraded);
        ps.setString(9,username);
        try {
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
