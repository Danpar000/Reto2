package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField idUsername;

    @FXML
    private TextField idPassword;


    @FXML
    protected void login(){DBUtils.logIn(idUsername.getText(), idPassword.getText());}


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
