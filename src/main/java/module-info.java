module com.team2.oosdworkshop6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.team2.oosdworkshop6 to javafx.fxml;
    exports com.team2.oosdworkshop6;
}