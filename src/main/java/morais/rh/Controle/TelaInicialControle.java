package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import morais.rh.App;

public class TelaInicialControle {

    @FXML
    Button Bconf;


    public void initialize(){

        Bconf.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("Configuracao");
            } catch (IOException e) {
            }
        });
        
    }

}
