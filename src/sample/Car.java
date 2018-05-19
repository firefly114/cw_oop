package sample;

import java.util.HashMap;

public abstract class Car {
    public String title;
    public double acceleration;
    public double maxSpeed;
    public double brakes;

    public int isUpgraded;
    HashMap<String, Upgrades> upgrades;

    public PointsStrategy st;

    public Car(String title, double acceleration, double maxSpeed, double brakes, int isUpgraded, HashMap<String, Upgrades> upg, PointsStrategy st) {
        this.title = title;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.brakes = brakes;
        this.isUpgraded = isUpgraded;
        this.upgrades = upg;
        this.st = st;
    }

    abstract public double rewardForRace();
    abstract public double upgradeCost();

    public double CalculateCarPoints() {
        return st.CalculatePoints(acceleration,maxSpeed,brakes);
    }

    public double getMoneyIncOnClick() {
        return rewardForRace() / (upgradeCost() * 3);
    }

    abstract public void upgrade();
    abstract public double price();

    public Upgrades GetUpgrades(String key, double money) {
        Upgrades u = null;
        if(upgrades.containsKey(key)) {
            return null;
        } else {
            switch (key) {
                case "Tires": u = new Tires(); break;
                case "Spoiler": u = new Spoiler(); break;
                case "Nitro": u = new Nitro(); break;
            }
            if(u.cost * price() <= money)
            upgrades.put(key,u);
            else return null;
        }
        return u;
        
    }
}
