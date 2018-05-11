package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController implements Initializable{
    @FXML
    private Label user;
    @FXML
    private Label money;
    @FXML
    private Label moneyRace;
    @FXML
    private Label moneyOP;
    @FXML
    private ListView<String> listView;
    @FXML
    private Label selCar_title;
    @FXML
    private Label selCar_Reward;
    @FXML
    private Label selCar_Money;
    @FXML
    private Label selCar_Price;

    @FXML
    private ProgressBar selCar_acceleration;
    @FXML
    private ProgressBar selCar_maxSpeed;
    @FXML
    private ProgressBar selCar_brakes;

    @FXML
    private Label curCar_title;
    @FXML
    private Label curCar_Reward;
    @FXML
    private Label curCar_Money;
    @FXML
    private Label curCar_UpgradeCost;

    @FXML
    private Label tires;
    @FXML
    private Label spoiler;
    @FXML
    private Label nitro;

    @FXML
    private ProgressBar curCar_acceleration;
    @FXML
    private ProgressBar curCar_maxSpeed;
    @FXML
    private ProgressBar curCar_brakes;
    @FXML
    private Button upgrade;

    public MenuModel menuModel = new MenuModel();
    public User u;
    public Car car;

    Car[] cars = new Car[]{new Car("Renault", 20, 40, 30),
            new Car("Ferrari", 30, 50, 15),
            new Car("Mercedes", 20, 40, 30),
            new Car("RedBull", 30, 35, 30)};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll("Renault", "Ferrari", "Mercedes","RedBull");

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                //ObservableList<String> list = FXCollections.observableArrayList(c1.name, c2.name, c3.name, c4.name);
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                for(int i = 0; i < cars.length; i++) {
                    if(cars[i].title.equals(listView.getSelectionModel().getSelectedItem())) {
                        selCar_title.setText(cars[i].title);
                        selCar_Reward.setText(Double.toString(cars[i].rewardForRace));
                        selCar_Money.setText("+" + Double.toString(cars[i].rewardForRace / (cars[i].upgradeCost * 3)));
                        selCar_acceleration.setProgress(cars[i].acceleration / 100);
                        selCar_maxSpeed.setProgress(cars[i].maxSpeed / 100);
                        selCar_brakes.setProgress(cars[i].brakes / 100);
                        selCar_Price.setText(Double.toString(cars[i].price()));
                        break;
                    }
                }
            }
        });
    }

    public void buyCar(ActionEvent e) {
        for(int i = 0; i < cars.length; i++) {
            if(cars[i].title.equals(selCar_title.getText())) {
                if(u.enough(cars[i].price())) {
                    u.money -= cars[i].price();
                    money.setText(Double.toString(u.money));
                    u.moneyOnPush += cars[i].rewardForRace / (cars[i].upgradeCost*3);
                    try {
                        if(car == null) {
                            menuModel.newCar(cars[i], u.getUsername());
                        }
                        else
                            menuModel.updateCar(cars[i],u.getUsername());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    setCar(cars[i]);
                    u.moneyOnPush+=cars[i].rewardForRace / cars[i].upgradeCost ;
                    this.moneyOP.setText(Double.toString(u.moneyOnPush));
                    break;
                } else {
                    // message
                    System.out.println("not enough money");
                }
            }
        }
    }

    public void upgradeCar(ActionEvent e) {
        if(u.enough(car.upgradeCost)) {
            u.money -= car.upgradeCost;
            money.setText(Double.toString(u.money));
            car.upgrade();
            try {
                menuModel.updateCar(car, u.getUsername());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            rewriteCarData(car);
            upgrade.setDisable(true);
        } else {
            // message
            System.out.println("not enough money");
        }
    }

    public void setUser(User user) {
        this.user.setText(user.getUsername());
        this.money.setText(Double.toString(user.money));
        this.moneyOP.setText(Double.toString(user.moneyOnPush));
        this.u = user;
    }

    public void setCar(Car car) {
        rewriteCarData(car);
        nitro.setText("Nitro ("+ Double.toString(car.price()*2) + ")");
        spoiler.setText("Spoiler ("+ Double.toString(car.price()*1.5) + ")");
        tires.setText("Tires ("+ Double.toString(car.price()*1.7) + ")");
        this.car = car;
    }

    public void rewriteCarData(Car car) {
        curCar_title.setText(car.title);
        curCar_Reward.setText(Double.toString(car.rewardForRace));
        curCar_Money.setText("+" + Double.toString(car.rewardForRace / (car.upgradeCost*3)));
        curCar_acceleration.setProgress(car.acceleration / 100);
        curCar_maxSpeed.setProgress(car.maxSpeed / 100);
        curCar_brakes.setProgress(car.brakes / 100);
        curCar_UpgradeCost.setText(Double.toString(car.upgradeCost));
    }

    public void incPush(ActionEvent e) {
        u.money += u.moneyOnPush;
        this.money.setText(Double.toString(u.money));
    }

    public void toRace(ActionEvent e) {
        // loop with freeze
        u.money += u.moneyOnPush;
        this.money.setText(Double.toString(u.money));
    }

    public void tires(ActionEvent e) {
        if(u.enough(car.price()*1.7) && !car.upgrades.contains("Tires")) {
            u.money -= car.price()*1.7;
            money.setText(Double.toString(u.money));
            car.upgrades.add("Tires");
            car.brakes += 15;
            car.maxSpeed += 3;
            rewriteCarData(car);
        }
    }

    public void spoiler(ActionEvent e) {
        if(u.enough(car.price()*1.5) && !car.upgrades.contains("Spoiler")) {
            u.money -= car.price()*1.5;
            money.setText(Double.toString(u.money));
            car.upgrades.add("Spoiler");
            car.brakes += 20;
            car.acceleration -= 5;
            rewriteCarData(car);
        }
    }

    public void nitro(ActionEvent e) {
        if(u.enough(car.price()*2) && !car.upgrades.contains("Nitro")) {
            u.money -= car.price()*2;
            money.setText(Double.toString(u.money));
            car.upgrades.add("Nitro");
            car.maxSpeed += 10;
            car.acceleration += 15;
            rewriteCarData(car);
        }
    }

    public void Logout(ActionEvent e) {
        try {
            ((Node)e.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("sample.fxml").openStream());

            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
