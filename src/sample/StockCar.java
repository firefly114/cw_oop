package sample;

import java.util.HashMap;

public class StockCar extends Car {
    public StockCar(String title, double acceleration, double maxSpeed, double brakes, int isUpgraded, HashMap<String, Upgrades> upg) {
        super(title, acceleration, maxSpeed, brakes, isUpgraded, upg, new StockPoints());
    }

    public double rewardForRace() {
        return CalculateCarPoints()/3;
    }
    public double upgradeCost() {
        return CalculateCarPoints()/1.7;
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
}
