package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController implements Initializable{
    @FXML
    private Label user;
    @FXML
    private Label money;
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
    public Button upgrade;
    @FXML
    public Button getMoney;
    @FXML
    public Button toRace;
    @FXML
    private Button tiresBtn;
    @FXML
    private Button spoilerBtn;
    @FXML
    private Button nitroBtn;

    @FXML
    private Label timeLabel;

    public MenuModel menuModel = new MenuModel();
    public User u;
    public Car car;

    HashMap<String, Upgrades> empty = new HashMap<>();
    Car[] cars = new Car[]{new Car("Renault", 20, 40, 30,0, empty),
            new Car("Ferrari", 30, 50, 15,0, empty),
            new Car("Mercedes", 20, 40, 30,0, empty),
            new Car("RedBull", 30, 35, 30,0, empty)};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getItems().addAll("Renault", "Ferrari", "Mercedes","RedBull");
        if(car.isUpgraded == 1) {
            upgrade.setDisable(true);
        }
        tiresBtn.setUserData("Tires");
        spoilerBtn.setUserData("Spoiler");
        nitroBtn.setUserData("Nitro");

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                //ObservableList<String> list = FXCollections.observableArrayList(c1.name, c2.name, c3.name, c4.name);
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                for(int i = 0; i < cars.length; i++) {
                    if(cars[i].title.equals(listView.getSelectionModel().getSelectedItem())) {
                        selCar_title.setText(cars[i].title);
                        selCar_Reward.setText(Double.toString(round(cars[i].rewardForRace(),2)));
                        selCar_Money.setText("+" + Double.toString(round(cars[i].rewardForRace() / (cars[i].upgradeCost() * 3),2)));
                        selCar_acceleration.setProgress(cars[i].acceleration / 100);
                        selCar_maxSpeed.setProgress(cars[i].maxSpeed / 100);
                        selCar_brakes.setProgress(cars[i].brakes / 100);
                        selCar_Price.setText(Double.toString(round(cars[i].price(),2)));
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
                    money.setText(Double.toString(round(u.money,2)));
                    u.moneyOnPush += cars[i].rewardForRace() / (cars[i].upgradeCost()*3);
                    try {
                        if(car == null)
                            menuModel.newCar(cars[i], u.getUsername());
                        else
                            menuModel.updateCar(cars[i],u.getUsername());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    setCar(cars[i]);
                    this.moneyOP.setText(Double.toString(round(u.moneyOnPush,2)));
                    upgrade.setDisable(false);
                    break;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("You dont have enough money to buy this car");

                    alert.showAndWait();
                }
            }
        }
    }

    public void upgradeCar(ActionEvent e) {
        if(u.enough(car.upgradeCost())) {
            u.money -= car.upgradeCost();
            money.setText(Double.toString(round(u.money,2)));
            car.upgrade();

            rewriteCarData(car);
            upgrade.setDisable(true);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("You dont have enough money upgrade your car");

            alert.showAndWait();
        }
    }

    public void setUser(User user) {
        this.user.setText(user.getUsername());
        this.money.setText(Double.toString(round(user.money,2)));
        this.moneyOP.setText(Double.toString(round(user.moneyOnPush,2)));
        this.u = user;
    }

    public void setCar(Car car) {
        rewriteCarData(car);
        this.car = car;
    }

    public void rewriteCarData(Car car) {
        curCar_title.setText(car.title);
        curCar_Reward.setText(Double.toString(round(car.rewardForRace(),2)));
        curCar_Money.setText("+" + Double.toString(round(car.rewardForRace() / (car.upgradeCost()*3),2)));
        curCar_acceleration.setProgress(car.acceleration / 100);
        curCar_maxSpeed.setProgress(car.maxSpeed / 100);
        curCar_brakes.setProgress(car.brakes / 100);
        curCar_UpgradeCost.setText(Double.toString(round(car.upgradeCost(),2)));
        nitro.setText("Nitro ("+ Double.toString(round(car.price()*2,2)) + ")");
        spoiler.setText("Spoiler ("+ Double.toString(round(car.price()*1.5,2)) + ")");
        tires.setText("Tires ("+ Double.toString(round(car.price()*1.7,2)) + ")");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void incPush(ActionEvent e) {
        u.money += u.moneyOnPush;
        this.money.setText(Double.toString(round(u.money,2)));
    }

    private int seconds = 10;

    public void toRace(ActionEvent e) {
        // loop with freeze
        toRace.setDisable(true);
        getMoney.setDisable(true);
        Timeline time= new Timeline();
        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                seconds--;
                timeLabel.setText("Countdown: "+Integer.toString(seconds));
                if(seconds<=0){
                    toRace.setDisable(false);
                    getMoney.setDisable(false);
                    u.money += car.rewardForRace();
                    money.setText(Double.toString(round(u.money,2)));
                    timeLabel.setText("");
                    seconds = 10;
                    time.stop();
                }
            }
        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!=null){
            time.stop();
        }
        time.play();
    }

    public void addUpgrades(ActionEvent e) {
        Button btn = (Button) e.getSource();
        String key = (String)btn.getUserData();
        Upgrades u = car.GetUpgrades(key, this.u.money);
        if(u == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("You've just buy this improvement or you dont have enough money to buy it");

            alert.showAndWait();
            return;
        }
        this.u.money -= car.price()*u.cost;
        money.setText(Double.toString(round(this.u.money,2)));
        car.acceleration += u.acceleration;
        car.maxSpeed += u.maxSpeed;
        car.brakes += u.brakes;
        rewriteCarData(car);
    }

    public void Logout(ActionEvent e) {
        try {
            try {
                if(car != null)
                    menuModel.updateCar(car, u.getUsername());
                menuModel.updateUser(u);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            ((Node)e.getSource()).getScene().getWindow().hide();
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("sample.fxml").openStream());

            primaryStage.setTitle("Formula1 Simulator");
            primaryStage.setScene(new Scene(root, 300, 275));
            primaryStage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}