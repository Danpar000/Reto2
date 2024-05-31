package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class TournamentController {
    @FXML
    private Button idTournamentA;
    @FXML
    private Button idTournamentB;


    @FXML
    public void selectTournament(MouseEvent mouseEvent) throws IOException {
        String torneo = "";
        if (mouseEvent.getSource()==idTournamentA){torneo = "A";}
        else if (mouseEvent.getSource()==idTournamentB) {torneo = "B";}
        DBUtils.changeScene("verjugadores.fxml", "Benidorm Chess Open 2024 - Jugadores Open " + torneo, torneo, 1080, 640);
    }

    @FXML
    public void salir(MouseEvent mouseEvent) throws IOException, SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmacion");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres salir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            DBUtils.connection.close();
            DBUtils.changeScene("logindef.fxml", "Benidorm Chess Open 2024 - Iniciar sesión", null, 1080, 640);
        }
    }
}
