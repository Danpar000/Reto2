import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lector {
    public static void main(String[] args) {
        FileReader fr = null;
        FileInputStream fich = null;
        DataInputStream entrada = null;


        try {
            fich = new FileInputStream("Open A-R_Ini.csv"); //Open A-R_Ini.csv Open B-R_Ini.csv
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
            int contador = 0;
            boolean buc = true;
            while (cadena != null) {
                if (contador>=5&&buc) {
                    if (cadena.equals(":::::::::")) {
                        buc = false;
                    }else {
                        club = "";
                        inf="";
                        rankini = Integer.parseInt(cadena.split(":")[0]);
                        titulo = cadena.split(":")[1];
                        nomjug = cadena.split(":")[2];
                        nacionalidad = cadena.split(":")[3];
                        fide = Integer.parseInt(cadena.split(":")[4]);
                        nac = Integer.parseInt(cadena.split(":")[5]);
                        fide_id = Integer.parseInt(cadena.split(":")[6]);
                        id_nac = Integer.parseInt(cadena.split(":")[7]);
                        try{
                            club = cadena.split(":")[8];
                            inf = cadena.split(":")[9];
                        }catch (IndexOutOfBoundsException e){
                        }
                        System.out.println("Jugador: rank= " + rankini + " titulo: "+ titulo + " nom: "+ nomjug + " nacionalidad: "+ nacionalidad + " fide: " + fide + " nac: " + nac + " fide_id: "+ fide_id + " id_nac: " + id_nac + " club: " + club + " inf: " + inf);
                    }

                }

                if (contador<6){
                    contador++;
                }
                cadena = entrada.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
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
    }
}
