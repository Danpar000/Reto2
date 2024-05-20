/*package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.table.TableColumn;
import java.net.URL;
import java.util.ResourceBundle;

public class JugadorController implements Initializable {
    @FXML
    private TableColumn<Jugador, ?> tableRangoI;
    @FXML
    private TableColumn<Jugador, ?> tableTitulo;
    @FXML
    private TableColumn<Jugador, ?> tableNombre;
    @FXML
    private TableColumn<Jugador, ?> tableFED;
    @FXML
    private TableColumn<Jugador, ?> tableELO;
    @FXML
    private TableColumn<Jugador, ?> tableNacional;
    @FXML
    private TableColumn<Jugador, ?> tableFIDE_ID;
    @FXML
    private TableColumn<Jugador, ?> tableID_Nac;
    @FXML
    private TableColumn<Jugador, ?> tableClub;
    @FXML
    private TableColumn<Jugador, ?> tableHotel;
    @FXML
    private TableColumn<Jugador, ?> tableCV;
    @FXML
    private TableColumn<Jugador, ?> tableRangoF;
    @FXML
    private TableColumn<Jugador, ?> tableTipoTorneo;

    @FXML
    private TableView<Jugador> tblJugador;

    private ObservableList<Jugador> jugadores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // creo el observablelist e inserto personas en la lista
        jugadores= FXCollections.observableArrayList(
                new Jugador("Isabel", "Allende",45, 1234),
                new Jugador("William", "Hurt", 33, 1234),
                new Jugador("Robin", "Williams", 24, 1234),
                new Jugador("Emma", "Johnson", 44, 1234),
                new Jugador("Vicky", "Luengo", 65, 5555)
        );

        //Asocio el campo de la columna con el atributo de la clase Persona
        // Hay que tener en cuenta que la TableView es de Persona -- ver definición
        this.tableRangoI.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.tableTitulo.setCellValueFactory(new PropertyValueFactory("apellidos"));
        this.tableNombre.setCellValueFactory(new PropertyValueFactory("edad"));
        this.tableFED.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableELO.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableNacional.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableFIDE_ID.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableID_Nac.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableClub.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableHotel.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableCV.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableRangoF.setCellValueFactory(new PropertyValueFactory("numero"));
        this.tableTipoTorneo.setCellValueFactory(new PropertyValueFactory("numero"));


        // Setea los datos en la tabla
        this.tblPersonas.setItems(personas);
    }
}
*/