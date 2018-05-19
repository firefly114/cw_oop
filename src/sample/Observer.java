package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.LinkedList;
import java.util.List;


public abstract class Observer {
    public abstract void update(String s);
}

class Notifier {
    private List<Observer> observers = new LinkedList<>();
    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(String s){
        for (Observer observer : observers) {
            observer.update(s);
        }
    }
}

class ObsButtons extends Observer {
    @FXML
    private Button b1;
    @FXML
    private Button b2;

    public ObsButtons(Button b1, Button b2) {
        this.b1 = b1;
        this.b2 = b2;
    }

    @Override
    public void update(String s) {
        if(b1 != null)
        b1.setDisable(!b1.isDisabled());
        if(b2 != null)
        b2.setDisable(!b2.isDisabled());
    }
}

class ObsLabels extends Observer{
    @FXML
    private Label l1;
    @FXML
    private Label l2;

    public ObsLabels(Label l1, Label l2) {
        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public void update(String s) {
        l1.setText(s);
        l2.setText(s);
    }
}
