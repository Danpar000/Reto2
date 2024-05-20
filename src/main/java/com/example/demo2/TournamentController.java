package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TournamentController implements Initializable {

    @FXML
    private Button idTournamentA;

    @FXML
    private Button idTournamentB;

    @FXML
    private ImageView idTournamentAimage;


    @FXML
    private ImageView idTournamentBimage;

    @FXML
    public void selectTournament(MouseEvent mouseEvent) throws IOException {
        System.out.println(mouseEvent);
        if (mouseEvent.getSource()==idTournamentA || mouseEvent.getSource()==idTournamentAimage){
            DBUtils.changeScene("hello-view.fxml", "Lista A");
        } else if (mouseEvent.getSource()==idTournamentB || mouseEvent.getSource()==idTournamentBimage) {
            DBUtils.changeScene("hello-view.fxml", "Lista B");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
