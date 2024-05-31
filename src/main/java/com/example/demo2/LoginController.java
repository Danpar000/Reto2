package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField idUsername;

    @FXML
    private PasswordField idPassword;

    @FXML
    private Button loginButton;

    @FXML
    private void aplicarInvertirColor (Button button) {
        button.setStyle(
                "-fx-background-color: #4aa5b2;" +
                        "-fx-border-color: #000000;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-border-width: 3;");
    }

    @FXML
    private void eventos(Button button) {
        button.setOnMouseEntered(event -> aplicarInvertirColor(button));
        button.setOnMouseExited(event -> restaurarColor(button));
    }

    @FXML
    private void restaurarColor(Button button) {
        button.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #4aa5b2;" +
                        "-fx-text-fill: #4aa5b2;" +
                        "-fx-border-width: 3;");
    }


    @FXML
    protected void login(){DBUtils.logIn(idUsername.getText(), idPassword.getText());}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventos(loginButton);
    }
}
