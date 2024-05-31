package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class JugadorController implements Initializable {

    @FXML
    private Button idBtnAceptar;

    @FXML
    private Button idBtnReiniciar;

    @FXML
    private Button idBtnCancelar;

    @FXML
    private CheckBox idCheckDescalificar;

    @FXML
    private CheckBox idCheckHotel;

    @FXML
    private CheckBox idCheckCV;

    @FXML
    private TextField idTFCLUB;

    @FXML
    private TextField idTFELO;

    @FXML
    private TextField idTFFIDE_ID;

    @FXML
    private TextField idTFFed;

    @FXML
    private TextField idTFID_NAC;

    @FXML
    private TextField idTFNac;

    @FXML
    private TextField idTFNombre;

    @FXML
    private TextField idTFTitulo;

    @FXML
    private Label idLabelNombre;

    private Jugador jugador;

    @FXML
    public void ventanaEditar(Jugador j) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editar.fxml"));
        Parent root = fxmlLoader.load();
        JugadorController nuevoControlador = fxmlLoader.getController();
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo64.png")));
        Scene scene = new Scene(root, 540, 640);
        Stage stage = new Stage();
        stage.setTitle("Benidorm Chess Open 2024 - Editar jugador");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(icon);

        // Seteo Jugador
        nuevoControlador.setInfo(j);

        // Muestro la ventana
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void setInfo(Jugador j) {
        this.jugador = j;
        idLabelNombre.setText("Editando a: " + j.getNomjug());
        idTFNombre.setText(j.getNomjug());
        idTFTitulo.setText(j.getTitulo());
        idTFFed.setText(j.getFederacion());
        idTFELO.setText(String.valueOf(j.getElo()));
        idTFNac.setText(String.valueOf(j.getNac()));
        idTFFIDE_ID.setText(String.valueOf(j.getFide_id()));
        idTFID_NAC.setText(String.valueOf(j.getId_nac()));
        idTFCLUB.setText(j.getClub());
        idCheckHotel.setSelected(j.isHotel());
        idCheckCV.setSelected(j.isCv());
        idCheckDescalificar.setSelected(j.isDescalificado());
    }

    @FXML
    private void aceptarCambios(ActionEvent event) {
        try {
            int elo = Integer.parseInt(idTFELO.getText());
            if (Objects.equals(this.jugador.getTipotorneo(), "A") && elo<=1800 || Objects.equals(this.jugador.getTipotorneo(), "B") && elo>=2000) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                if (Objects.equals(this.jugador.getTipotorneo(), "A")) {
                    alert.setContentText("El ELO del jugador del torneo A debe ser mayor que 1800");
                } else {
                    alert.setContentText("El ELO del jugador del torneo B debe ser menor que 2000");
                }
                alert.showAndWait();
            } else {

                // Los campos estan bien, procedo a editar
                String nombre = idTFNombre.getText();
                String titulo = idTFTitulo.getText();
                String federacion = idTFFed.getText();
                elo = Integer.parseInt(idTFELO.getText());
                int nac = Integer.parseInt(idTFNac.getText());
                int fideID = Integer.parseInt(idTFFIDE_ID.getText());
                int idNac = Integer.parseInt(idTFID_NAC.getText());
                String club = idTFCLUB.getText();
                boolean hotel = idCheckHotel.isSelected();
                boolean cv = idCheckCV.isSelected();
                boolean descalificar = idCheckDescalificar.isSelected();

                Jugador jugadorEditado = new Jugador(jugador.getRangoI(), titulo, nombre, federacion, elo, nac, fideID, idNac, club, hotel, cv, jugador.getRangoF(), jugador.getTipotorneo(), descalificar);

                // Indico que está todo ok
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cambio realizado");
                alert.setHeaderText(null);
                alert.setContentText("Se han realizado los cambios con éxito.");
                alert.showAndWait();

                // Cierro Ventana
                Stage stage = (Stage) idBtnAceptar.getScene().getWindow();
                stage.close();


                // Paso el Jugador editado a TorneoXController
                TorneoXController torneoXController = new TorneoXController();
                torneoXController.recibirJugadorEditado(jugadorEditado);
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Campos incorrectos, por favor verifícalos.");
            alert.showAndWait();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void cancelarCambios(ActionEvent event) {
        Stage stage = (Stage) idBtnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void reiniciarCampos(ActionEvent event) {
        setInfo(jugador);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}