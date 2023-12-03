package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import morais.rh.App;

public class ConfiguracaoControle {

    @FXML
    Button Bfinaliza;


    public void initialize(){

        Bfinaliza.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
}
