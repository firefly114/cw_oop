package sample;

public class User {
    private String username;
    public int id;

    public double money;
    public double moneyOnPush;
    public boolean auth;

    public User(String username, int id, double money, double moneyOnPush, boolean auth) {
        this.username = username;
        this.id = id;
        this.money = money;
        this.moneyOnPush = moneyOnPush;
        this.auth = auth;
    }

    public String getUsername() {
        return username;
    }

    public void push() {
        this.money += moneyOnPush;
    }

    public boolean enough(double price) {
        return money >= price;
    }
}
