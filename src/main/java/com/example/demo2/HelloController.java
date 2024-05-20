package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {

        welcomeText.setText("Jugadores añadidos correctamente!");
    }

    @FXML
    private TextField idUsername;

    @FXML
    private TextField idPassword;


    @FXML
    protected Connection login (MouseEvent mouseEvent) throws SQLException, IOException {
        Connection connection = getConnection();
        if (connection != null) {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            return connection;
        }
        return null;
    }

    @FXML
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/Reto2";
        try {
            String user = this.idUsername.getText();
            String password = this.idPassword.getText();
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Usuario o contraseña incorrectos.");
            alert.showAndWait();
        }
        return null;
    }









    @FXML
    protected void importarJugadores() throws SQLException {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        FileReader fr = null;
        FileInputStream fich = null;
        DataInputStream entrada = null;
        try {
            fich = new FileInputStream("Open A-R_Ini.csv"); // | Open A-R_Ini.csv | Open B-R_Ini.csv | Open A-R_Ini - Copy.csv |
            entrada = new DataInputStream(fich);
            String cadena = entrada.readLine();
            int rankini = 0;
            String titulo = "";
            String nomjug = "";
            String nacionalidad = "";
            int fide = 0;
            int nac = 0;
            int fide_id = 0;
            int id_nac = 0;
            String club = null;
            String inf = "";
            StringBuilder exists = new StringBuilder();
            char tipotorneo = 'A';
            int contador = 0;
            boolean buc = true;
            while (cadena != null) {
                if (contador>=5&&buc) {
                    if (cadena.equals(":::::::::")) {
                        buc = false;
                    }else {
                        boolean cv = false;
                        boolean h = false;
                        club = "";
                        inf = "";
                        rankini = Integer.parseInt(cadena.split(":")[0]);
                        titulo = cadena.split(":")[1];
                        nomjug = cadena.split(":")[2];
                        nacionalidad = cadena.split(":")[3];
                        fide = Integer.parseInt(cadena.split(":")[4]);
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
                            query = String.format("INSERT INTO Jugador (RangoI, Titulo, Nombre, Federacion, FIDE, Nacional, FIDE_ID, ID_Nacional, OrigenClub, Hotel, Comunidad_Valenciana, RangoF, TipoTorneo)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                                pstmt.setString(1, String.valueOf(rankini));
                                pstmt.setString(2, titulo);
                                pstmt.setString(3, nomjug);
                                pstmt.setString(4, nacionalidad);
                                pstmt.setString(5, String.valueOf(fide));
                                pstmt.setString(6, String.valueOf(nac));
                                pstmt.setString(7, String.valueOf(fide_id));
                                pstmt.setString(8, String.valueOf(id_nac));
                                pstmt.setString(9, club);
                                pstmt.setBoolean(10, h);
                                pstmt.setBoolean(11, cv);
                                pstmt.setString(12, null);
                                pstmt.setString(13, String.valueOf(tipotorneo));
                                pstmt.executeUpdate();

                            } catch (SQLIntegrityConstraintViolationException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Error, hay entradas duplicadas: " + exists);
                                alert.showAndWait();
                            }
                        }
                        //System.out.println("Jugador: rank= " + rankini + " titulo: "+ titulo + " nom: "+ nomjug + " nacionalidad: "+ nacionalidad + " fide: " + fide + " nac: " + nac + " fide_id: "+ fide_id + " id_nac: " + id_nac + " club: " + club + " inf: " + inf + " torneo: " + tipotorneo);

                    }

                }

                if (contador<6){
                    contador++;
                }
                cadena = entrada.readLine();
            }

            if (exists.length() > 0) {
                System.out.println(exists);
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
        connection.close();
    }

}