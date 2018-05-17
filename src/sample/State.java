package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class State {
    abstract void resetView(Button b, Label l);
}

class NoCarState extends State {
    @Override
    void resetView(Button b, Label l) {
        b.setDisable(true);
        l.setText("you havent bought a car yet");
    }
}

class CarState extends State {
    @Override
    void resetView(Button b, Label l) {
        b.setDisable(false);
    }
}
