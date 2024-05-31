package com.example.demo2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.*;
import java.sql.*;
import java.util.Objects;


public class DBUtils {
    private static Stage stage = new Stage();
    public static Connection connection;
    public static String typeTorneo = "";

    public static void logIn(String username, String password) {

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Reto2", username, password);

            if (connection != null) {
                changeScene("tournament.fxml", "Benidorm Open Chess 2024 - Selecciona un Torneo", null, 1080, 640);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales incorrectas, vuelve a intentarlo");
                alert.showAndWait();
            }
        } catch (SQLException | NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales incorrectas, vuelve a intentarlo");
            e.getMessage();
            alert.showAndWait();
        }
    }

    public static void changeScene(String fxmlfile, String title, String varTorneo, int w, int h) throws IOException {
        typeTorneo = varTorneo;
        Parent root;
        FXMLLoader fxmlLoader;
        Image icon = new Image(Objects.requireNonNull(DBUtils.class.getResourceAsStream("/img/logo.png")));
        fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlfile));
        root = fxmlLoader.load();
        Scene scene = new Scene(root, w, h);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.show();
    }
}
