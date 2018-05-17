package sample;

import java.util.HashMap;

public class SuperCar extends Car {
    public SuperCar(String title, double acceleration, double maxSpeed, double brakes, int isUpgraded, HashMap<String, Upgrades> upg) {
        super(title, acceleration, maxSpeed, brakes, isUpgraded, upg, new SuperPoints());
    }

    public double rewardForRace() {
        return CalculateCarPoints()/3;
    }
    public double upgradeCost() {
        return (acceleration+maxSpeed+brakes)/3/1.7;
    }

    public void upgrade() {
        isUpgraded = 1;
        acceleration += 12;
        maxSpeed += 5;
        brakes += 7;
    }

    public double price() {
        return CalculateCarPoints() *2;
    }
}
