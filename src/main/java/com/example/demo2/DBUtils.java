package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.naming.PartialResultException;
import java.io.*;
import java.sql.*;
import java.util.Scanner;


public class DBUtils {
    private static String typeTorneo = "";
    private static Connection connection;
    private static Stage stage = new Stage();
    //private static ;

    public static void logIn(String username, String password) {

        try {
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Reto2", username, password);

            if (connection != null) {
                changeScene("tournament.fxml", "Selecciona un torneo", null);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales incorrectas, vuelve a intentarlo");
                System.out.println("arriba2");
                alert.showAndWait();
            }
        } catch (SQLException | NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales incorrectas, vuelve a intentarlo");
            System.out.println("abajo");
            System.out.println(e.getMessage());
            alert.showAndWait();
        }
    }

    public static void changeScene(String fxmlfile, String title, String varTorneo) throws IOException {
        typeTorneo = varTorneo;
        Parent root;
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlfile));
        root = fxmlLoader.load();
        Scene scene = new Scene(root, 1080, 640);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
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
            char tipotorneo = Character.valueOf(typeTorneo.charAt(0));
            int contador = 0;
            boolean buc = true;
            while (cadena != null) {
                if (contador>=5&&buc) {
                    if (cadena.equals(":::::::::") || cadena.equals("::::::::::::::::")) {
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
                            query = String.format("INSERT INTO Jugador (RangoI, Titulo, Nombre, Federacion, ELO, Nacional, FIDE_ID, ID_Nacional, OrigenClub, Hotel, Comunidad_Valenciana, RangoF, TipoTorneo)" + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
                                pstmt.executeUpdate();

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
        //connection.close();
    }

    @FXML
    protected void importarPosicion() throws SQLException {

        Statement statement = DBUtils.connection.createStatement();

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
            char tipotorneo = Character.valueOf(typeTorneo.charAt(0));
            int contador = 0;
            boolean buc = true;
            while (cadena != null) {
                if (contador>=5&&buc) {
                    if (cadena.equals("::::::::::::::::") || cadena.equals(":::::::::") || cadena.equals(":::::::::::::::::")) {
                        buc = false;
                    }else {
                        try {
                            rankini = Integer.parseInt(cadena.split(":")[0]);
                            rankinF = Integer.parseInt(cadena.split(":")[1]);
                            String query = String.format("UPDATE Jugador SET RangoF = ? WHERE RangoI = ? AND TipoTorneo = ?");
                            PreparedStatement pstmt = connection.prepareStatement(query);
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
        //connection.close();
    }

    @FXML
    protected void cambiarTorneo() {
        System.out.println("terminar");
    }


    @FXML
    protected void Exportar() throws SQLException {
        PrintWriter salida = null;
        int rangIni = 1;
//        String torneo = "A";

        int num = 0;
        PreparedStatement procedimiento = connection.prepareStatement("call jug_premios()");
        procedimiento.execute();
        PreparedStatement psnumJug = connection.prepareStatement("SELECT COUNT(*) FROM Jugador WHERE TipoTorneo=?");
        psnumJug.setString(1, typeTorneo);
        ResultSet output = psnumJug.executeQuery();
        output.next();
        num = output.getInt(1);
        try {
            if (typeTorneo=="A") {salida = new PrintWriter("PremiosOptaJugA.txt");}
            else {salida = new PrintWriter("PremiosOptaJugB.txt");}
            salida.println("---------------------------------------------------------------------------------");
            salida.println("                  TORNEO " + typeTorneo + " - PREMIOS QUE OPTA CADA JUGADOR     ");
            while (rangIni<=num) {
                String Jugador = "";
                PreparedStatement ps = connection.prepareStatement("SELECT jo.RangoI, Nombre, jo.TipoCategoria FROM Jug_Opta_Categ jo JOIN Jugador j ON jo.RangoI=j.RangoI WHERE j.RangoI=? AND j.TipoTorneo=?");
                ps.setInt(1,rangIni);
                ps.setString(2,typeTorneo);
                ResultSet rs = ps.executeQuery();
                rs.next();
                salida.println("---------------------------------------------------------------------------------");
                Jugador = "| " + rs.getString("RangoI") + " | " + rs.getString("Nombre");;
                Jugador = Jugador + " | " + rs.getString("TipoCategoria");
                while (rs.next()) {
                    Jugador = Jugador + ", " + rs.getString("TipoCategoria");
                    rs.next();
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
            System.out.println(e.getMessage());
        }

    }

}
