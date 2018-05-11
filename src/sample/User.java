package sample;

public class User {
    private String username;
    public int id;

    public double money = 0;
    public double moneyOnPush = 0.2;
    public Car currentCar;

    public User(String username, int id, double money, double moneyOnPush) {
        this.username = username;
        this.id = id;
        this.money = money;
        this.moneyOnPush = moneyOnPush;
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
