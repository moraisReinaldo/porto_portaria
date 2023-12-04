package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import morais.rh.App;

public class LoginControle {

    @FXML
    Button Bentrar;

    @FXML
    Button Bolho;

    @FXML
    Label Lrevela;

    @FXML
    PasswordField Lsenha;

    @FXML
    TextField Lusuario;


    public void initialize(){

        Bentrar.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
}
