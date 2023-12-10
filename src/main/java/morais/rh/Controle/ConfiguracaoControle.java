package morais.rh.Controle;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Usuario;
import morais.rh.Modelo.Veiculo;

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
    TableColumn<Usuario, Integer> TCcod;

    @FXML 
    TableColumn<Usuario, String> TCusu;

    @FXML
    TableView<Usuario> Tusuarios;

    @FXML
    TableColumn<Pessoa, Integer> TCcodP;

    @FXML 
    TableColumn<Pessoa, String> TCnomeP;

    @FXML
    TableView<Pessoa> Tpessoas;

    @FXML
    TableColumn<Veiculo, Integer> TCcodV;

    @FXML 
    TableColumn<Veiculo, String> TCnomeV;

    @FXML
    TableView<Veiculo> Tveiculos;

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

    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    ArrayList<Pessoa> pessoas = PessoaDAO.buscarPessoa();
    ArrayList<Veiculo> veiculos = VeiculoDAO.buscarVeiculo();

    public void initialize(){


        TCcod.setCellValueFactory(
        new PropertyValueFactory<>("cod")
        );
        TCusu.setCellValueFactory(
        new PropertyValueFactory<>("usuario")
        );
        Tusuarios.setItems(
        FXCollections.observableList(usuarios)
        );

        TCcodP.setCellValueFactory(
        new PropertyValueFactory<>("codigo")
        );
        TCnomeP.setCellValueFactory(
        new PropertyValueFactory<>("nome")
        );
        Tpessoas.setItems(
        FXCollections.observableList(pessoas)
        );

        TCcodV.setCellValueFactory(
        new PropertyValueFactory<>("placa")
        );
        TCnomeV.setCellValueFactory(
        new PropertyValueFactory<>("desc")
        );
        Tveiculos.setItems(
        FXCollections.observableList(veiculos)
        );

        Tusuarios.setItems(FXCollections.observableList(usuarios));
        Tpessoas.setItems(FXCollections.observableList(pessoas));
        Tveiculos.setItems(FXCollections.observableList(veiculos));


        Bfinaliza.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });

        BadicionarF.setOnAction((ActionEvent event) ->{
            try {

                usuarios = UsuarioDAO.buscarUsuario();
                String usu = Iusuario.getText();
                String senha = Isenha.getText();

                Usuario cadas = new Usuario( usu, senha, usuarios.size());
                UsuarioDAO.adicionaUsuario(cadas);

                Lretorno.setText("Usuario adicionado!");

            } catch (IOException e) {
            }
        });

        BremoveC.setOnAction((ActionEvent event) ->{
            try {

                int cod = Integer.parseInt(LCodFun.getText());
                UsuarioDAO.apagarUsuario(cod);

                Lretorno.setText("Usuario removido");

            } catch (Exception e) {
            }
        });

        BremoveP.setOnAction((ActionEvent event) ->{
            try {
                int cod = Integer.parseInt(LCodP.getText());
                PessoaDAO.apagarPessoa(cod);

                Lretorno.setText("Pessoa removida");
            } catch (Exception e) {
            }
        });

        BremoveV.setOnAction((ActionEvent event) ->{
            try {
                String placa = LCodVei.getText();
                VeiculoDAO.apagarVeiculo(placa);

                Lretorno.setText("Veiculo removido");
            } catch (Exception e) {
            }
        });
        
    }
    
}
