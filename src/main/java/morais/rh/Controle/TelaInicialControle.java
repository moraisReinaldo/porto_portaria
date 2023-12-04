package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import morais.rh.App;

public class TelaInicialControle {

    @FXML
    Button Bconf;

    @FXML
    Button Bpeople;

    @FXML
    Button Bveiculos;

    @FXML
    Label Lmoradores;

    @FXML
    Label Lveiculos;

    @FXML
    Label Lvisitantes;

    @FXML 
    TextField Tbusca;

    @FXML
    VBox Vregistros;

    public void initialize(){

        Bconf.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("Configuracao");
            } catch (IOException e) {
            }
        });

        Bpeople.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("EntradaPessoas");
            } catch (IOException e) {
            }
        });

        Bveiculos.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("EntradaVeiculos");
            } catch (IOException e) {
            }
        });
        
    }

}
