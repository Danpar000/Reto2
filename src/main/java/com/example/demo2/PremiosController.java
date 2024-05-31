package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PremiosController implements Initializable {
    Connection connection = DBUtils.connection;
    String typeTorneo = DBUtils.typeTorneo;
    ObservableList<JugadorGana> jugadoresGanan;

    @FXML
    private Button idBtnVolver;

    @FXML
    private Button idBtnExportar;

    @FXML
    private TableColumn<JugadorGana, ?> idCategoria;

    @FXML
    private TableColumn<JugadorGana, ?> idImporte;

    @FXML
    private Label idLabelTipoTorneo;

    @FXML
    private TableColumn<JugadorGana, ?> idNombre;

    @FXML
    private TableColumn<JugadorGana, ?> idPuesto;

    @FXML
    private TableColumn<JugadorGana, ?> idRangoF;

    @FXML
    private TableColumn<JugadorGana, ?> idRangoI;

    @FXML
    private TableView<JugadorGana> tblJugador;

    @FXML
    void exportar(MouseEvent event) throws SQLException {
        PrintWriter salida = null;
        int rangIni = 0;
        int num = tblJugador.getSelectionModel().getTableView().getItems().size();
        JugadorGana jug;
        try {
            if (typeTorneo=="A") {salida = new PrintWriter("GanadoresOpenA.txt");}
            else {salida = new PrintWriter("GanadoresOpenB.txt");}
            salida.println("-----------------------------------------------------------------------------------------------------------------------------------");
            salida.println("                                                TORNEO OPEN " + typeTorneo + " - LISTADO GANADORES     ");
            while (rangIni<num) {
                jug = tblJugador.getSelectionModel().getTableView().getItems().get(rangIni);
                salida.println("-----------------------------------------------------------------------------------------------------------------------------------");
                salida.println("| " + jug.getIdCategoria() + " | " + "Puesto: " + jug.getIdPuesto() + " | " + jug.getIdNombre() + " | " + "Importe: " + jug.getIdImporte() + " | " + "Rango Final: " + jug.getIdRangoF() + " | " + "Rango Inicial: " + jug.getIdRangoI() + " |");
                rangIni++;
            }
            salida.println("-----------------------------------------------------------------------------------------------------------------------------------");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Operación exitosa");
            alert.setHeaderText(null);
            alert.setContentText("El fichero se ha generado correctamente.");
            alert.showAndWait();
            salida.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void volver(MouseEvent event) throws IOException {
        DBUtils.changeScene("verjugadores.fxml", "Benidorm Chess Open 2024 - Jugadores Open " + typeTorneo, typeTorneo, 1080, 640);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            idLabelTipoTorneo.setText("TORNEO " + typeTorneo + " - LISTADO GANADORES");
            PreparedStatement ps = connection.prepareStatement("CALL jug_premios()");
            ps.execute();
            ps = connection.prepareStatement("CALL bucle(?)");
            ps.setString(1, typeTorneo);
            ps.execute();
            ps = connection.prepareStatement("select TipoCategoria, Puesto, Importe, Nombre, t.RangoF, t.RangoI from TempPremios t JOIN Jugador j ON j.RangoI=t.RangoI AND j.TipoTorneo=t.TipoTorneo ORDER BY Prioridad, Puesto, RangoF ASC;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JugadorGana jg = new JugadorGana(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6)
                );
                if (jugadoresGanan==null) {
                    this.jugadoresGanan= FXCollections.observableArrayList(jg);
                } else {
                    this.jugadoresGanan.add(jg);
                }
            }

        } catch (Exception e) {
            e.getCause();
        }

        this.idCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        this.idRangoI.setCellValueFactory(new PropertyValueFactory<>("idRangoI"));
        this.idImporte.setCellValueFactory(new PropertyValueFactory<>("idImporte"));
        this.idNombre.setCellValueFactory(new PropertyValueFactory<>("idNombre"));
        this.idRangoF.setCellValueFactory(new PropertyValueFactory<>("idRangoF"));
        this.idPuesto.setCellValueFactory(new PropertyValueFactory<>("idPuesto"));

        // Seteo los datos en la tabla
        this.tblJugador.setItems(jugadoresGanan);

        // Refresco
        tblJugador.refresh();


    }
}
