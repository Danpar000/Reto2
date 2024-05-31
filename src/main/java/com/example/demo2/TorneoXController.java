package com.example.demo2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class TorneoXController implements Initializable {
    Connection connection = DBUtils.connection;
    String typeTorneo = DBUtils.typeTorneo;

    @FXML
    private Button idBtnAjustes;

    @FXML
    private Button idBtnCalcularPremios;

    @FXML
    private Button idBtnDescalificar;

    @FXML
    private Button idBtnEditar;

    @FXML
    private Button idBtnExportar;

    @FXML
    private Button idBtnFiltros;

    @FXML
    private Button idBtnImportar;

    @FXML
    private Button idBtnImportarPosicion;

    @FXML
    private Button idBtnAyuda;

    @FXML
    private Button idBtnVolver;

    @FXML
    private Label idLabelBenidorm;

    @FXML
    private Label idTorneoLabel;

    @FXML
    private ImageView imgAjustes;

    @FXML
    private ImageView imgAyuda;

    @FXML
    private ImageView imgDescalificar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private ImageView imgFiltros;

    @FXML
    private ImageView imgImportar;

    @FXML
    private ImageView imgPosiciones;

    @FXML
    private TableColumn<Jugador, ?>  tableCV;

    @FXML
    private TableColumn<Jugador, ?>  tableClub;

    @FXML
    private TableColumn<Jugador, ?>  tableELO;

    @FXML
    private TableColumn<Jugador, ?>  tableFED;

    @FXML
    private TableColumn<Jugador, ?>  tableFIDE_ID;

    @FXML
    private TableColumn<Jugador, ?>  tableHotel;

    @FXML
    private TableColumn<Jugador, ?> tableID_Nac;

    @FXML
    private TableColumn<Jugador, ?>  tableNacional;

    @FXML
    private TableColumn<Jugador, ?>  tableNombre;

    @FXML
    private TableColumn<Jugador, ?>  tableRangoF;

    @FXML
    private TableColumn<Jugador, ?>  tableRangoI;

    @FXML
    private TableColumn<Jugador, ?>  tableTipoTorneo;

    @FXML
    private TableColumn<Jugador, ?>  tableTitulo;

    @FXML
    private TableColumn<Jugador, ?> tableDescalificado;

    @FXML
    private TableView<Jugador> tblJugador;

    private ObservableList<Jugador> jugadores;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventos(idBtnExportar);
        eventos(idBtnVolver);
        eventos(idBtnCalcularPremios);
        eventosAlt(idBtnEditar, imgEditar);
        eventosAlt(idBtnDescalificar, imgDescalificar);
        eventosAlt(idBtnImportar, imgImportar);
        eventosAlt(idBtnImportarPosicion, imgPosiciones);
        eventosAlt(idBtnFiltros, imgFiltros);
        eventosAlt(idBtnAyuda, imgAyuda);
        eventosAlt(idBtnAjustes, imgAjustes);

        try {
            idTorneoLabel.setText("Torneo Open " + typeTorneo + " - Jugadores");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Jugador WHERE TipoTorneo=?");
            ps.setString(1,typeTorneo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Jugador j = new Jugador(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getBoolean(10),
                        rs.getBoolean(11),
                        rs.getInt(12),
                        rs.getString(13),
                        rs.getBoolean(14));
                if (jugadores==null) {
                    this.jugadores= FXCollections.observableArrayList(j);
                } else {
                    this.jugadores.add(j);
                }
            }
        } catch (Exception e) {System.out.println(e.getMessage());}

        //Asocio el campo de la columna con el atributo de la clase Jugador
        this.tableRangoI.setCellValueFactory(new PropertyValueFactory("rangoI"));
        this.tableTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        this.tableNombre.setCellValueFactory(new PropertyValueFactory("nomjug"));
        this.tableFED.setCellValueFactory(new PropertyValueFactory("federacion"));
        this.tableELO.setCellValueFactory(new PropertyValueFactory("elo"));
        this.tableNacional.setCellValueFactory(new PropertyValueFactory("nac"));
        this.tableFIDE_ID.setCellValueFactory(new PropertyValueFactory("fide_id"));
        this.tableID_Nac.setCellValueFactory(new PropertyValueFactory("id_nac"));
        this.tableClub.setCellValueFactory(new PropertyValueFactory("club"));
        this.tableHotel.setCellValueFactory(new PropertyValueFactory("hotel"));
        this.tableCV.setCellValueFactory(new PropertyValueFactory("cv"));
        this.tableRangoF.setCellValueFactory(new PropertyValueFactory("rangoF"));
        this.tableTipoTorneo.setCellValueFactory(new PropertyValueFactory("tipotorneo"));
        this.tableDescalificado.setCellValueFactory(new PropertyValueFactory("descalificado"));

        // Setea los datos en la tabla
        this.tblJugador.setItems(jugadores);

        // Refresco
        tblJugador.refresh();

    }

    @FXML
    protected void mostrarAyuda(MouseEvent event) throws IOException {
        Parent root;
        Stage stage = new Stage();
        FXMLLoader fxmlLoader;
        Image icon = new Image(Objects.requireNonNull(DBUtils.class.getResourceAsStream("/img/logo.png")));
        fxmlLoader = new FXMLLoader(DBUtils.class.getResource("ayuda.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ayuda");
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    protected void mostrarFiltros(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Filtrado");
        alert.setHeaderText(null);
        if (!tblJugador.getColumns().get(1).isVisible()) {
            tblJugador.getColumns().get(1).setVisible(true);
            tblJugador.getColumns().get(3).setVisible(true);
            tblJugador.getColumns().get(5).setVisible(true);
            tblJugador.getColumns().get(7).setVisible(true);
            tblJugador.getColumns().get(8).setVisible(true);
            tblJugador.getColumns().get(11).setVisible(true);
            alert.setContentText("Columnas reveladas.");
        } else {
            tblJugador.getColumns().get(1).setVisible(false);
            tblJugador.getColumns().get(3).setVisible(false);
            tblJugador.getColumns().get(5).setVisible(false);
            tblJugador.getColumns().get(7).setVisible(false);
            tblJugador.getColumns().get(8).setVisible(false);
            tblJugador.getColumns().get(11).setVisible(false);
            alert.setContentText("Columnas ocultadas.");
        }
        alert.showAndWait();
    }

    @FXML
    protected void descalificarJugador(MouseEvent event) throws IOException, SQLException {
        boolean descalificado;

        if (tblJugador.getSelectionModel().getSelectedItem()!=null) {
            if (tblJugador.getSelectionModel().getSelectedItem().isDescalificado()==true) {descalificado = false;}
            else {descalificado = true;}

            String query = String.format("UPDATE Jugador SET descalificado = ? WHERE RangoI = ? AND TipoTorneo = ?");

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, descalificado);
            pstmt.setInt(2, obtenerJugador().getRangoI());
            pstmt.setString(3, obtenerJugador().getTipotorneo());
            pstmt.executeUpdate();

            if (descalificado==false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Calificado");
                alert.setHeaderText(null);
                alert.setContentText("Se ha vuelto a calificar al jugador.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Descalificado");
                alert.setHeaderText(null);
                alert.setContentText("Se ha descalificado al jugador.");
                alert.showAndWait();
            }

            // Refresco y recargo
            this.tblJugador.setItems(jugadores);
            this.tblJugador.refresh();
            DBUtils.changeScene("verjugadores.fxml", "Benidorm Chess Open 2024 - Jugadores Open " + typeTorneo, typeTorneo, 1080, 640);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Tienes que seleccionar a un jugador para usar esta función.");
            alert.showAndWait();
        }
    }

    @FXML
    protected void calcularPremios(MouseEvent event) throws IOException, SQLException {
        Connection connection = DBUtils.connection;
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Jugador WHERE TipoTorneo=?");
        pstmt.setString(1, typeTorneo);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.first()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("No puedes calcular premios sin los jugadores.");
            alert.showAndWait();
        } else {
            pstmt = connection.prepareStatement("SELECT * FROM Jugador WHERE RangoF IS null AND TipoTorneo=? OR RangoF=0 AND TipoTorneo=?");
            pstmt.setString(1, typeTorneo);
            pstmt.setString(2, typeTorneo);
            rs = pstmt.executeQuery();
            if (!rs.next()) {
                DBUtils.changeScene("premiosdef.fxml", "Benidorm Chess 2024 - Listado Ganadores Torneo " + typeTorneo, typeTorneo, 1280, 720);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.setContentText("Por favor importa la posición final para todos los jugadores.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    protected void mostrarAjustes(MouseEvent event) {
        if (idLabelBenidorm.isVisible()) {
            imgAjustes.setRotate(32);
            idLabelBenidorm.setVisible(false);
            idBtnEditar.setVisible(true);
            idBtnDescalificar.setVisible(true);
            idBtnImportar.setVisible(true);
            idBtnFiltros.setVisible(true);
            idBtnAyuda.setVisible(true);
            idBtnImportarPosicion.setVisible(true);
        } else {
            imgAjustes.setRotate(0);
            idLabelBenidorm.setVisible(true);
            idBtnEditar.setVisible(false);
            idBtnDescalificar.setVisible(false);
            idBtnImportar.setVisible(false);
            idBtnFiltros.setVisible(false);
            idBtnAyuda.setVisible(false);
            idBtnImportarPosicion.setVisible(false);
        }
    }

    @FXML
    private void eventos(Button button) {
        button.setOnMouseEntered(event -> aplicarInvertirColor(button));
        button.setOnMouseExited(event -> restaurarColor(button));
    }

    @FXML
    private void aplicarInvertirColor (Button button) {
        button.setStyle(
                "-fx-background-color: #4aa5b2;" +
                        "-fx-border-color: #000000;" +
                        "-fx-text-fill: #000000;" +
                        "-fx-border-width: 5");
    }

    @FXML
    private void restaurarColor(Button button) {
        button.setStyle(
                "-fx-background-color: #000000;" +
                        "-fx-border-color: #000000;" +
                        "-fx-text-fill: #4aa5b2;" +
                        "-fx-border-width: 5");
    }

    @FXML
    private void eventosAlt(Button button, ImageView imageView) {
        button.setOnMouseEntered(event -> aplicarInvertirColorAlt(button, imageView));
        button.setOnMouseExited(event -> restaurarColorAlt(button, imageView));
    }

    @FXML
    private void aplicarInvertirColorAlt (Button button, ImageView imageView) {
        if (Objects.equals(button.getId(), "idBtnAjustes")) {
            button.setStyle(
                    "-fx-background-color: #4aa5b2;" +
                            "-fx-border-color: #000000;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 4");
            imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/ajustesAlt.png"))));
        } else {
            button.setStyle(
                    "-fx-background-color: #000000;" +
                            "-fx-border-color: #4aa5b2;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 4");

            // Comprobar botón para cambiar imagen
            if (Objects.equals(button.getId(), "idBtnEditar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/editarAlt.png"))));
            } else if (Objects.equals(button.getId(), "idBtnDescalificar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/descalificarAlt.png"))));
            } else if (Objects.equals(button.getId(), "idBtnImportar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/importarAlt.png"))));
            } else if (Objects.equals(button.getId(), "idBtnImportarPosicion")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/puestosAlt.png"))));
            } else if (Objects.equals(button.getId(), "idBtnFiltros")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/filtrosAlt.png"))));
            } else if (Objects.equals(button.getId(), "idBtnAyuda")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/ayudaAlt.png"))));
            }
        }
    }

    @FXML
    private void restaurarColorAlt (Button button, ImageView imageView) {
        if (Objects.equals(button.getId(), "idBtnAjustes")) {
            button.setStyle("-fx-background-color: #000000;");
            imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/ajustes.png"))));
        } else {
            button.setStyle("-fx-background-color: #4aa5b2;");

            // Comprobar botón para cambiar imagen
            if (Objects.equals(button.getId(), "idBtnEditar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/editar.png"))));
            } else if (Objects.equals(button.getId(), "idBtnDescalificar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/descalificar.png"))));
            } else if (Objects.equals(button.getId(), "idBtnImportar")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/importar.png"))));
            } else if (Objects.equals(button.getId(), "idBtnImportarPosicion")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/puestos.png"))));
            } else if (Objects.equals(button.getId(), "idBtnFiltros")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/filtros.png"))));
            } else if (Objects.equals(button.getId(), "idBtnAyuda")) {
                imageView.setImage(new Image(String.valueOf(getClass().getResource("/img/ayuda.png"))));
            }
        }
    }

    @FXML
    protected void importarJugadores() throws SQLException {
        Statement statement = connection.createStatement();
        FileReader fr = null;
        FileInputStream fich = null;
        DataInputStream entrada = null;
        try {
            if (typeTorneo.equals("A")) {
                fich = new FileInputStream("Open A-R_Ini.csv"); // | Open A-R_Ini.csv | Open B-R_Ini.csv | Open A-R_Ini - Copy.csv |
            } else {
                fich = new FileInputStream("Open B-R_Ini.csv"); // | Open A-R_Ini.csv | Open B-R_Ini.csv | Open A-R_Ini - Copy.csv |
            }
            entrada = new DataInputStream(fich);
            String cadena = entrada.readLine();
            int rankini = 0;
            String titulo = "";
            String nomjug = "";
            String nacionalidad = "";
            int elo = 0;
            int nac = 0;
            int fide_id = 0;
            int id_nac = 0;
            String club = null;
            String inf = "";
            StringBuilder exists = new StringBuilder();
            char tipotorneo = typeTorneo.charAt(0);
            int contador = 0;
            boolean buc = true;
            while (cadena != null) {
                if (contador>=5&&buc) {
                    if (cadena.equals(":::::::::") || cadena.equals("::::::::::::::::")) {
                        buc = false;
                    }else {
                        boolean cv = false;
                        boolean h = false;
                        boolean descalificado = false;
                        club = "";
                        inf = "";
                        rankini = Integer.parseInt(cadena.split(":")[0]);
                        titulo = cadena.split(":")[1];
                        nomjug = cadena.split(":")[2];
                        nacionalidad = cadena.split(":")[3];
                        elo = Integer.parseInt(cadena.split(":")[4]);
                        nac = Integer.parseInt(cadena.split(":")[5]);
                        fide_id = Integer.parseInt(cadena.split(":")[6]);
                        id_nac = Integer.parseInt(cadena.split(":")[7]);
                        try {
                            club = cadena.split(":")[8];
                            inf = cadena.split(":")[9];


                            if (inf.contains("CVH")) {
                                cv = true;
                                h = true;
                            } else if (inf.contains("CV")) {
                                cv = true;
                            } else if (inf.contains("H")) {
                                h = true;
                            }

                        } catch (IndexOutOfBoundsException ignored){}

                        String query = String.format("SELECT * FROM Jugador WHERE rangoI = '%s' AND fide_id = '%s'", rankini, fide_id);
                        ResultSet resultSet = statement.executeQuery(query);
                        if (resultSet.next()) {
                            exists.append(" ").append(fide_id);
                        } else {
                            query = "INSERT INTO Jugador (RangoI, Titulo, Nombre, Federacion, ELO, Nacional, FIDE_ID, ID_Nacional, OrigenClub, Hotel, Comunidad_Valenciana, RangoF, TipoTorneo, Descalificado)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                                pstmt.setString(1, String.valueOf(rankini));
                                pstmt.setString(2, titulo);
                                pstmt.setString(3, nomjug);
                                pstmt.setString(4, nacionalidad);
                                pstmt.setString(5, String.valueOf(elo));
                                pstmt.setString(6, String.valueOf(nac));
                                pstmt.setString(7, String.valueOf(fide_id));
                                pstmt.setString(8, String.valueOf(id_nac));
                                pstmt.setString(9, club);
                                pstmt.setBoolean(10, h);
                                pstmt.setBoolean(11, cv);
                                pstmt.setString(12, null);
                                pstmt.setString(13, String.valueOf(tipotorneo));
                                pstmt.setBoolean(14, descalificado);
                                pstmt.executeUpdate();


                                Jugador j = new Jugador(rankini, titulo, nomjug, nacionalidad, elo, nac, fide_id, id_nac, club, h, cv, null, String.valueOf(tipotorneo), descalificado);
                                if (jugadores==null) {
                                    this.jugadores = FXCollections.observableArrayList(j);
                                } else {
                                    this.jugadores.add(j);
                                }
                                this.tblJugador.setItems(jugadores);
                                tblJugador.refresh();

                            } catch (SQLIntegrityConstraintViolationException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Error, hay entradas duplicadas: " + exists);
                                alert.showAndWait();
                            }
                        }
                    }

                }
                if (contador<6){
                    contador++;
                }
                cadena = entrada.readLine();
            }

            if (!exists.isEmpty()) {
                tblJugador.refresh();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso (1/2)");
                alert.setHeaderText(null);
                alert.setContentText("Hay entradas duplicadas que no se han importado. FIDE_ID: " + exists);
                alert.showAndWait();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Importación exitosa (2/2)");
                alert.setHeaderText(null);
                alert.setContentText("El resto de jugadores han sido importados correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Importación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Se han importado los jugadores correctamente.");
                alert.showAndWait();
            }

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error, no se ha encontrado el archivo especificado.");
            alert.showAndWait();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
        statement.close();
    }

    @FXML
    protected void importarPosicion() throws SQLException {

        Statement statement = DBUtils.connection.createStatement();
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Jugador WHERE TipoTorneo=?");
        pstmt.setString(1, typeTorneo);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.first()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("No puedes importar posiciones sin los jugadores.");
            alert.showAndWait();
        } else {
            FileReader fr = null;
            FileInputStream fich = null;
            DataInputStream entrada = null;
            try {
                if (typeTorneo.equals("A")) {
                    fich = new FileInputStream("Open A-Resultados.csv"); // | Open A-Resultados.csv | Open B-Resultados.csv
                } else {
                    fich = new FileInputStream("Open B-Resultados.csv"); // | Open A-Resultados.csv | Open B-Resultados.csv
                }
                entrada = new DataInputStream(fich);
                String cadena = entrada.readLine();
                int rankini = 0;
                int rankinF = 0;
                StringBuilder exists = new StringBuilder();
                char tipotorneo = typeTorneo.charAt(0);
                int contador = 0;
                boolean buc = true;
                while (cadena != null) {
                    if (contador>=5&&buc) {
                        if (cadena.equals("::::::::::::::::") || cadena.equals(":::::::::") || cadena.equals(":::::::::::::::::")) {
                            buc = false;
                        }else {
                            try {
                                rankini = Integer.parseInt(cadena.split(":")[1]);
                                rankinF = Integer.parseInt(cadena.split(":")[0]);
                                String query = "UPDATE Jugador SET RangoF = ? WHERE RangoI = ? AND TipoTorneo = ?";
                                pstmt = connection.prepareStatement(query);
                                pstmt.setString(1, String.valueOf(rankinF));
                                pstmt.setString(2, String.valueOf(rankini));
                                pstmt.setString(3, String.valueOf(tipotorneo));
                                pstmt.executeUpdate();
                            } catch (ArrayIndexOutOfBoundsException | SQLIntegrityConstraintViolationException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    }
                    if (contador<6){
                        contador++;
                    }
                    cadena = entrada.readLine();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Importación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("Se han importado los resultados correctamente.");
                alert.showAndWait();

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error, no se ha encontrado el archivo especificado. Coloca el fichero en la ruta.");
                alert.showAndWait();
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (fr != null) {
                        fr.close();
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            }
            statement.close();
        }

    }

    @FXML
    protected void cambiarTorneo() throws IOException {
        DBUtils.changeScene("tournament.fxml", "Benidorm Chess Open 2024 - Selecciona un Torneo", null, 1080, 640);
    }

    @FXML
    protected void Exportar() throws SQLException {
        Connection connection = DBUtils.connection;
        PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Jugador WHERE TipoTorneo=?");
        pstmt.setString(1, typeTorneo);
        ResultSet rs = pstmt.executeQuery();
        if (!rs.first()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("No puedes exportar sin los jugadores.");
            alert.showAndWait();
        } else {
            PrintWriter salida = null;
            int rangIni = 1;
            int num;
            pstmt = connection.prepareStatement("call jug_premios()");
            pstmt.execute();
            pstmt = connection.prepareStatement("SELECT COUNT(*) FROM Jugador WHERE TipoTorneo=?");
            pstmt.setString(1, typeTorneo);
            ResultSet output = pstmt.executeQuery();
            output.next();
            num = output.getInt(1);
            try {
                if (typeTorneo=="A") {salida = new PrintWriter("PremiosOptaJugA.txt");}
                else {salida = new PrintWriter("PremiosOptaJugB.txt");}
                salida.println("---------------------------------------------------------------------------------");
                salida.println("                  TORNEO " + typeTorneo + " - PREMIOS QUE OPTA CADA JUGADOR     ");
                while (rangIni<=num) {
                    String Jugador = "";
                    pstmt = connection.prepareStatement("SELECT jo.RangoI, Nombre, jo.TipoCategoria FROM Jug_Opta_Categ jo JOIN Jugador j ON jo.RangoI=j.RangoI AND jo.TipoTorneo=j.TipoTorneo WHERE j.RangoI=? AND j.TipoTorneo=?");
                    pstmt.setInt(1,rangIni);
                    pstmt.setString(2,typeTorneo);
                    rs = pstmt.executeQuery();
                    rs.next();
                    salida.println("---------------------------------------------------------------------------------");
                    Jugador = "| " + rs.getString("RangoI") + " | " + rs.getString("Nombre");
                    Jugador = Jugador + " | " + rs.getString("TipoCategoria");
                    while (rs.next()) {
                        Jugador = Jugador + ", " + rs.getString("TipoCategoria");
                    }
                    rangIni++;
                    salida.println(Jugador);
                }
                salida.println("---------------------------------------------------------------------------------");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operación exitosa");
                alert.setHeaderText(null);
                alert.setContentText("El fichero se ha generado correctamente.");
                alert.showAndWait();
                salida.close();
            } catch (FileNotFoundException e) {
                e.getMessage();
            }
        }

    }

    @FXML
    public Jugador obtenerJugador() throws IOException {
        if (tblJugador.getSelectionModel().getSelectedItem()!=null) {
            try {
                int rangoI = tblJugador.getSelectionModel().getSelectedItem().getRangoI();
                String titulo = tblJugador.getSelectionModel().getSelectedItem().getTitulo();
                String nomjug = tblJugador.getSelectionModel().getSelectedItem().getNomjug();
                String federacion = tblJugador.getSelectionModel().getSelectedItem().getFederacion();
                int elo = tblJugador.getSelectionModel().getSelectedItem().getElo();
                int nac = tblJugador.getSelectionModel().getSelectedItem().getNac();
                int fide_id = tblJugador.getSelectionModel().getSelectedItem().getFide_id();
                int id_nac = tblJugador.getSelectionModel().getSelectedItem().getId_nac();
                String club = tblJugador.getSelectionModel().getSelectedItem().getClub();
                boolean hotel = tblJugador.getSelectionModel().getSelectedItem().isHotel();
                boolean cv = tblJugador.getSelectionModel().getSelectedItem().isCv();
                Integer rangoF = tblJugador.getSelectionModel().getSelectedItem().getRangoF();
                String tipotorneo = tblJugador.getSelectionModel().getSelectedItem().getTipotorneo();
                boolean descalificar = tblJugador.getSelectionModel().getSelectedItem().isDescalificado();

                Jugador j = new Jugador(rangoI, titulo, nomjug, federacion, elo, nac, fide_id, id_nac, club, hotel, cv, rangoF, tipotorneo, descalificar);
                return j;
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return null;
    }

    @FXML
    private void llamarJugadorController() throws IOException {
        JugadorController jugadorController = new JugadorController();
        if (obtenerJugador()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Tienes que seleccionar a un jugador para usar esta función.");
            alert.showAndWait();
        } else {
            jugadorController.ventanaEditar(obtenerJugador());
        }
    }

    @FXML
    public void recibirJugadorEditado(Jugador jugadorEditado) throws IOException, SQLException {
        String query = "UPDATE Jugador SET RangoI=?, Titulo=?, Nombre=?, Federacion=?, ELO=?, Nacional=?, FIDE_ID=?, ID_Nacional=?, OrigenClub=?, Hotel=?, Comunidad_Valenciana=?, RangoF=?, TipoTorneo=?, Descalificado=? WHERE RangoI=? AND TipoTorneo=?;";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, jugadorEditado.getRangoI());
            pstmt.setString(2, jugadorEditado.getTitulo());
            pstmt.setString(3, jugadorEditado.getNomjug());
            pstmt.setString(4, jugadorEditado.getFederacion());
            pstmt.setInt(5, jugadorEditado.getElo());
            pstmt.setInt(6, jugadorEditado.getNac());
            pstmt.setInt(7, jugadorEditado.getFide_id());
            pstmt.setInt(8, jugadorEditado.getId_nac());
            pstmt.setString(9, jugadorEditado.getClub());
            pstmt.setBoolean(10, jugadorEditado.isHotel());
            pstmt.setBoolean(11, jugadorEditado.isCv());

            if (jugadorEditado.getRangoF()==null) {
                pstmt.setInt(12, 0);
            } else {
                pstmt.setInt(12, jugadorEditado.getRangoF());
            }

            pstmt.setString(13, jugadorEditado.getTipotorneo());
            pstmt.setBoolean(14, jugadorEditado.isDescalificado());
            pstmt.setInt(15, jugadorEditado.getRangoI());
            pstmt.setString(16, jugadorEditado.getTipotorneo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.getMessage();
        }

        DBUtils.changeScene("verjugadores.fxml", "Benidorm Chess Open 2024 - Jugadores Open " + typeTorneo, typeTorneo, 1080, 640);
    }
}
