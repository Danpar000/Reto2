import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Scanner;

public class MainEmpleado {
    static Scanner sc;
    static Connection cnx;

    static {
        try {
            cnx = getConnexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        try {
            sc = new Scanner(System.in);
            int opcion = -1;
            while (opcion != 0) {
                menu();
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        listEmpleados();
                        break;
                    case 2:
                        insertEmpleado();
                        break;
                    case 3:
                        eliEmpleado();
                        break;
                    case 4:
                        actualizarEmpleado();
                        sc.next();
                        break;
                    case 5:
                        actualizarCampo();
                        break;
                    case 6:
                        listCampo();
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/empleados";
        String user = "root";
        String password = "Debian";
        return DriverManager.getConnection(url, user, password);
    }

    public static void menu() {
        System.out.println("\n--Gestor de empleados--");
        System.out.println("---------------------------------------");
        System.out.println("1. Mostrar empleados");
        System.out.println("2. Insertar un nuevo empleado");
        System.out.println("3. Eliminar un empleado");
        System.out.println("4. Actualizar un empleado por nombre");
        System.out.println("5. Actualizar un empleado por un campo");
        System.out.println("6. Mostrar datos por un campo");
        System.out.println("0. Salir");
        System.out.println("---------------------------------------");
    }

    private static void listEmpleados() throws SQLException {
        ResultSet rs = cnx.createStatement().executeQuery("SELECT * from empleado");
        System.out.println("--Lista de personas--");
        while (rs.next()) {
            int CodEmp =rs.getInt("CodEmp");
            String CodDep = rs.getString("CodDep");
            String ExTelEmp = rs.getString("ExTelEmp");
            Date FecInEmp = rs.getDate("FecInEmp");
            Date FecNaEmp = rs.getDate("FecNaEmp");
            String NifEmp = rs.getString("NifEmp");
            String NomEmp = rs.getString("NomEmp");
            int NumHi  = rs.getInt("NumHi");
            double SalEmp = rs.getDouble("SalEmp");


            LocalDate FecInEmpLD = FecInEmp.toLocalDate();
            LocalDate FecNaEmpLD = FecNaEmp.toLocalDate();

            System.out.printf("%d %s %s [%s] [%s] %s %s %s %s\n",CodEmp, CodDep, ExTelEmp, FecInEmpLD.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),FecNaEmpLD.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),NifEmp,NomEmp,NumHi,SalEmp);
        }
        rs.close();
        cnx.createStatement().close();
    }

    private static void listCampo() throws SQLException {
        System.out.println("--¿De que campo quieres mostrar los datos?--\n"+
                "CodEmp\n" + "CodDep\n" + "ExTelEmp\n" + "FecInEmp\n" + "FecNaEmp\n" + "NifEmp\n" + "NomEmp\n" + "NumHi\n" + "SalEmp");
        String campo = sc.nextLine();
        ResultSet rs = cnx.createStatement().executeQuery("SELECT CodEmp," + campo + " from empleado order by CodEmp");
        while (rs.next()){
            System.out.print("Código de empleado: " + rs.getString("CodEmp") + " - " + campo + ": ");
            if (campo.equals("CodEmp") || campo.equals("NumHi")) {
                System.out.println(rs.getInt(campo));
            }else if (campo.equals("CodDep") || campo.equals("ExTelEmp" ) || campo.equals("NifEmp") || campo.equals("NomEmp")) {
                System.out.println(rs.getString(campo));
            }else if (campo.equals("FecInEmp") || campo.equals("FecNaEmp")) {
                System.out.println(rs.getDate(campo).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            }else if (campo.equals("SalEmp")) {
                System.out.println(rs.getDouble(campo));
            }else
                System.out.println("Valor incorrecto");
        }
        rs.close();
    }

    private static  void insertEmpleado() throws SQLException, IOException {
        ResultSet rs = cnx.createStatement().executeQuery("SELECT * from empleado");
        ResultSet rsDep = cnx.createStatement().executeQuery("SELECT * from departamento");

        PreparedStatement ps = cnx.prepareStatement(
                "INSERT INTO empleado (CodDep, ExTelEmp, FecInEmp,FecNaEmp,NifEmp,NomEmp,NumHi,SalEmp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("--Insertar un empleado--");
        System.out.println("Código de departamento: ");
        String dep = br.readLine();
        System.out.println("Extensión telefónica: ");
        String extTel = br.readLine();
        System.out.println("Fecha de incorporación (dd/mm/aaaa): ");
        String fecInc = br.readLine();
        System.out.println("Fecha de nacimiento (dd/mm/aaaa): ");
        String fecNac = br.readLine();
        System.out.println("NIF/DNI del empleado: ");
        String nif = br.readLine();
        System.out.println("Nombre del empleado: ");
        String nom = br.readLine();
        System.out.println("Número de hijos: ");
        String numHi = br.readLine();
        System.out.println("Salario del empleado: ");
        String salEmp = br.readLine();
        boolean corr = true;
        while (rs.next()) {
            if (rs.getString("NifEmp").equals(nif)){
                corr=false;
            }
        }
        while (rsDep.next()){
            if (!rsDep.getString("CodDep").equals(dep)){
                corr=false;
            }else {
                corr=true;
                rsDep.last();
            }
        }

        if (corr){
            LocalDate fechaInc = LocalDate.parse(fecInc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate fechaNac = LocalDate.parse(fecNac, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ps.setString(1, dep);
            ps.setString(2, extTel);
            ps.setDate(3, Date.valueOf(fechaInc));
            ps.setDate(4, Date.valueOf(fechaNac));
            ps.setString(5, nif);
            ps.setString(6, nom);
            ps.setInt(7, Integer.parseInt(numHi));
            ps.setDouble(8, Double.parseDouble(salEmp));
            ps.executeUpdate();
            ps.close();
            System.out.println("\n--Empleado insertado--\n");
        }else {
            System.out.println("\n¡--Error al introducir los datos--!\n");
        }
    }

    private static void eliEmpleado() throws SQLException {
        System.out.println("--Eliminar un empleado--");
        System.out.print("Introduce el código del empleado: ");
        int CodEmp = Integer.parseInt(sc.nextLine());



        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM empleado WHERE CodEmp = ?");
        ps.setInt(1,CodEmp);

        ResultSet rs = ps.executeQuery();


        if (rs.next())
        {
            PreparedStatement ps1 = cnx.prepareStatement("DELETE FROM empleado WHERE CodEmp = ?");
            ps1.setInt(1,CodEmp);
            ps1.executeUpdate();
            ps1.close();}
        ps.close();
        System.out.println("\n--Empleado eliminado--\n");
    }

    private static void actualizarJugador() throws SQLException {
        System.out.println("--Actualización de un jugador--");
        System.out.print("Introduce el nombre del jugador a actualizar: ");
        String Nombre = sc.nextLine();
        Jugador jug1 = null;
        jug1=findByName(Nombre);
        if (jug1 == null) {
            System.out.println("--¡El jugador no está registrado en el torneo!--");
        } else {
            System.out.println("Rango inicial");
            int RangoI = Integer.parseInt(br.readLine());
            System.out.println("Título: ");
            String titulo = br.readLine();
            System.out.println("Nombre del jugador: ");
            String NomJugador = br.readLine();
            System.out.println("Federación: ");
            String Federacion = br.readLine();
            System.out.println("ELO: ");
            int elo = Integer.parseInt(br.readLine());
            System.out.println("Nacional: ");
            String nacionalidad = br.readLine();
            System.out.println("FIDE_ID: ");
            int Fide_id = Integer.parseInt(br.readLine());
            System.out.println("ID_NACIONAL: ");
            int idNacional = Integer.parseInt(br.readLine());
            System.out.println("Origen club");
            String origenClub = br.readLine();
            System.out.println("Hotel: ");
            boolean hotel = Boolean.parseBoolean(br.readLine());
            System.out.println("Comunidad Valenciana: ");
            boolean cv = Boolean.parseBoolean(br.readLine());
            System.out.println("Rango final: ");
            int RangoF = Integer.parseInt(br.readLine());
            System.out.println("Tipo torneo: ");
            //char tipoTorneo = br.readLine().charAt(0);
            char tipoTorneo = 'A';
            ps.setInt(1, RangoI);
            ps.setString(2, titulo);
            ps.setString(3, NomJugador);
            ps.setString(4, Federacion);
            ps.setInt(5, elo);
            ps.setString(6, nacionalidad);
            ps.setInt(7, Fide_id);
            ps.setInt(8, idNacional);
            ps.setString(9, origenClub);
            ps.setBoolean(10, hotel);
            ps.setBoolean(11, cv);
            ps.setInt(12, RangoF);
            ps.setString(13, String.valueOf(tipoTorneo));
            try {
                update(jug1);
                System.out.println("--Jugador actualizado--");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("");
    }

    private static void update(Jugador e) throws SQLException {

        if (e.getRangoI() == 0)
            return;
        PreparedStatement ps = cnx.prepareStatement(
                "UPDATE jugador SET RangoI = ?, Titulo = ?, Nombre = ?, Federacion = ?, ELO = ?, Nacional = ?, FIDE_ID = ?, ID_Nacional = ?, OrigenClub = ?, Hotel = ?, Comunidad_Valenciana = ?, RangoF = ?, TipoTorneo = ? WHERE RangoI = ?");
        ps.setInt(1, e.getRangoI());
        ps.setString(2, e.getTitulo());
        ps.setString(3, e.getNombre());
        ps.setString(4, e.getFederacion());
        ps.setInt(5, e.getELO());
        ps.setInt(6, e.getNacional());
        ps.setInt(7, e.getFIDE_ID());
        ps.setInt(8, e.getID_Nacional());
        ps.setString(9, e.getOrigenClub());
        ps.setBoolean(10, e.getHotel());
        ps.setBoolean(11, e.getComunidad_valenciana());
        ps.setInt(12, e.getRangoF());
        ps.setString(13, String.valueOf(e.getTipoTorneo()));
        ps.executeUpdate();
        ps.close();
    }

    private static Jugador findByName(String Nombre) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM jugador WHERE RangoI = ? AND TipoTorneo = ?");
        ps.setString(1, RangoI);
        ps.setString(2, TipoTorneo);
        ResultSet rs = ps.executeQuery();
        Empleado result = null;
        if (rs.next()) {
            result = new Empleado(rs.getInt("RangoI"), rs.getString("Titulo"), rs.getString("Nombre"),
                    rs.getString("Federacion"),rs.getInt("ELO"), rs.getString("Nacional"),rs.getInt("FIDE_ID"),rs.getInt("ID_Nacional"),rs.getString("OrigenClub"), rs.getBoolean("Hotel"), rs.getBoolean("Comunidad_Valenciana"), rs.getInt("RangoF"),
                    rs.getChar("TipoTorneo"));
        }
        return result;
    }

    private static void actualizarCampo() throws SQLException{
        System.out.println("--¿Qué campo quieres cambiar?--\n"+
                "RangoI\n" + "Titulo\n" + "Nombre\n" + "Federacion\n" + "ELO\n" + "Nacional\n" + "FIDE_ID\n" + "ID_Nacional\n" + "OrigenClub" + "Hotel\n" + "Comunidad_Valenciana\n" + "RangoF\n" + "TipoTorneo");
        String campo = sc.nextLine();
        System.out.println("--¿Qué dato quieres cambiar?--");
        String dato = sc.nextLine();
        PreparedStatement ps = cnx.prepareStatement("SELECT * FROM jugador WHERE " + campo + " = ?");
        ps.setString(1, dato);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("--¿Cuál es el nuevo dato?--");
            String nuevoDato = sc.nextLine();
            ps = cnx.prepareStatement("UPDATE jugador SET " + campo + " = ? WHERE " + campo + " = ?");
            ps.setString(1, nuevoDato);
            ps.setString(2, dato);
            ps.executeUpdate();
            ps.close();
            System.out.println("\n--Dato actualizado--");
        }else{
            System.out.println("\n--¡No existe el dato que quieres modificar!--");
        }
    }
}
