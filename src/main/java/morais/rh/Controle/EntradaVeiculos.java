package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import morais.rh.App;

public class EntradaVeiculos {

    @FXML
    Button BadiPassa;

    @FXML
    Button Bcancela;

    @FXML
    Button Bregistro;

    @FXML
    Button Bremove;

    @FXML
    ChoiceBox Cramal;

    @FXML
    ChoiceBox Ctipo;

    @FXML
    HBox Hpassageiro;

    @FXML
    HBox HVeiculos;

    @FXML
    TableColumn Tcodigo;

    @FXML
    TableColumn Tnome;

    @FXML
    TableView Tpassa;

    @FXML
    TextArea Imotivo;
    
    @FXML
    TextField IcodPassa;

    @FXML
    TextField Icor;

    @FXML
    TextField Idocumento;

    @FXML
    TextField Imodelo;

    @FXML
    TextField Inome;

    @FXML
    TextField Iplaca;

    @FXML
    TextField Itelefone;

    public void initialize(){

        Bcancela.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
}
