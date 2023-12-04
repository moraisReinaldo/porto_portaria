module morais.rh {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens morais.rh to javafx.fxml;
    opens morais.rh.Controle to javafx.fxml;
    exports morais.rh;
}
