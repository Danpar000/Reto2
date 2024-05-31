package com.example.demo2;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.*;
import java.sql.SQLException;

public class MainGestor extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        try {
            DBUtils.changeScene("logindef.fxml", "Benidorm Chess Open 2024 - Iniciar sesión", null, 1080, 640);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}