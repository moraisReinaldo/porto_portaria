package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import morais.rh.App;

public class EntradaPessoas {

    @FXML
    Button Bcancela;

    @FXML
    Button Bregistro;

    @FXML
    ChoiceBox Ctipo;

    @FXML
    ChoiceBox Cramais;
    
    @FXML
    HBox Hpossibilidades;

    @FXML
    Label Lconfirma;

    @FXML
    TextArea Imotivo;

    @FXML
    TextField Ibusca;

    @FXML
    TextField Idocumento;

    @FXML
    TextField Itelefone;

    @FXML
    TextField Inome;

    public void initialize(){

        Bcancela.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
}
