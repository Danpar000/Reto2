module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.mariadb.jdbc;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}