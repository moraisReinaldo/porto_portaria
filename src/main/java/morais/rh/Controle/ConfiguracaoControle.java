package morais.rh.Controle;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import morais.rh.App;

public class ConfiguracaoControle {

    @FXML
    Button Bfinaliza;

    @FXML
    Button BadicionarF;

    @FXML
    Button BremoveC;

    @FXML
    Button BremoveP;

    @FXML
    Button BremoveV;

    @FXML
    Label Lretorno;

    @FXML
    TableColumn TCcod;

    @FXML 
    TableColumn TCusu;

    @FXML
    TableView Tusuarios;

    @FXML
    TableColumn TCcodP;

    @FXML 
    TableColumn TCnomep;

    @FXML
    TableView Tpessoas;

    @FXML
    TableColumn TCcodV;

    @FXML 
    TableColumn TCnomeV;

    @FXML
    TableView Tveiculos;

    @FXML
    TextField Iusuario;

    @FXML
    TextField Isenha;

    @FXML
    TextField LCodFun;

    @FXML
    TextField LCodP;

    @FXML
    TextField LCodVei;




    public void initialize(){

        Bfinaliza.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
}
