package sample;

public abstract class Upgrades {
    public String title;
    public double acceleration;
    public double maxSpeed;
    public double brakes;
    public double cost;
}

class Tires extends Upgrades {
    public Tires() {
        title = "Tires";
        acceleration = 0;
        maxSpeed = 3;
        brakes = 15;
        cost = 1.7;
    }
}

class Spoiler extends Upgrades {
    public Spoiler() {
        title = "Spoiler";
        acceleration = -5;
        maxSpeed = 0;
        brakes = 20;
        cost = 1.5;
    }
}

class Nitro extends Upgrades {
    public Nitro() {
        title = "Nitro";
        acceleration = 15;
        maxSpeed = 10;
        brakes = 0;
        cost = 2;
    }
}
