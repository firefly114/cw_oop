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

    public double rewardForRace;
    public double upgradeCost;
    LinkedList<String> upgrades = new LinkedList<>();

    public Car(String title, double acceleration, double maxSpeed, double brakes) {
        this.title = title;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.brakes = brakes;
        this.rewardForRace = rewardForRace();
        this.upgradeCost = upgradeCost();
//        upgrades.put("Tires", 0);
//        upgrades.put("Spoiler", 0);
//        upgrades.put("Nitro", 0);
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
        acceleration += 10;
        maxSpeed += 5;
        brakes += 10;
    }

    public double price() {
        return CalculateCarPoints() *2;
    }
}
