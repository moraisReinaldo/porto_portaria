module morais.rh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens morais.rh to javafx.fxml;
    opens morais.rh.Controle to javafx.fxml;
    opens morais.rh.Modelo to javafx.base;
    exports morais.rh;
}
