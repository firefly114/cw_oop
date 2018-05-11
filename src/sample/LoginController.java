package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

    LoginModel lm = new LoginModel();

    @FXML
    private Label isConnected;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(lm.isDbConnected())
            isConnected.setText("Connected");
        else
            isConnected.setText("Not connected");
    }

    public void Login(ActionEvent e) {
        try {
            if(lm.isLogin(txt_username.getText(), txt_password.getText())) {
                isConnected.setText("correct");
                ((Node)e.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("menu.fxml").openStream());
                MenuController mc = (MenuController)loader.getController();

                User u = lm.getUser(txt_username.getText());
                mc.setUser(u);

                Car car = lm.getCar(txt_username.getText());
                if(car != null) {
                    mc.setCar(car);
                }
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("window is closing");
                        saveData(mc);
                    }
                });
            } else {
                isConnected.setText("error");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void saveData(MenuController mc) {
        try {
            if(mc.car != null)
                mc.menuModel.updateCar(mc.car, mc.u.getUsername());
            mc.menuModel.updateUser(mc.u);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void Signup(ActionEvent e) {
        try {
            if(!lm.isLogin(txt_username.getText(), txt_password.getText())) {
                lm.new_User(txt_username.getText(), txt_password.getText());
                ((Node)e.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("menu.fxml").openStream());
                MenuController mc = (MenuController)loader.getController();

                User u = lm.getUser(txt_username.getText());
                mc.setUser(u);

                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();

                primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        saveData(mc);
                    }
                });
            } else {
                isConnected.setText("error");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
