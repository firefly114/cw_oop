package sample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Car {
    public String title;
    //public String pic;
    public double acceleration;
    public double maxSpeed;
    public double brakes;

    public int isUpgraded;
    HashMap<String, Upgrades> upgrades;

    public Car(String title, double acceleration, double maxSpeed, double brakes, int isUpgraded, HashMap<String, Upgrades> upg) {
        this.title = title;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.brakes = brakes;
        this.isUpgraded = isUpgraded;
        this.upgrades = upg;
    }

    public double rewardForRace() {
        return CalculateCarPoints()/3;
    }
    public double upgradeCost() {
        return CalculateCarPoints()/1.7;
    }
    public double CalculateCarPoints() {
        return (acceleration+maxSpeed+brakes)/3;
    }

    public void upgrade() {
        isUpgraded = 1;
        acceleration += 10;
        maxSpeed += 5;
        brakes += 10;
    }

    public double price() {
        return CalculateCarPoints() *2;
    }

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
