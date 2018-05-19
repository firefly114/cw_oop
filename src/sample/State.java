package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public abstract class State {
    abstract void resetView(Button b, Label l, AnchorPane t);
}

class NoCarState extends State {
    @Override
    void resetView(Button b, Label l, AnchorPane t) {
        b.setDisable(true);
        t.setDisable(true);
        l.setText("you havent bought a car yet");
    }
}

class CarState extends State {
    @Override
    void resetView(Button b, Label l, AnchorPane t) {
        b.setDisable(false);
        t.setDisable(false);
    }
}
