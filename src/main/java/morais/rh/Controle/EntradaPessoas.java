package morais.rh.Controle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Tipo;
import morais.rh.Modelo.Usuario;
import morais.rh.Modelo.Visita;

public class EntradaPessoas {

    @FXML
    Button Bcancela;

    @FXML
    Button Bregistro;

    @FXML
    ComboBox<String> Ctipo;

    @FXML
    TextField IRamal;
    
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

    @FXML
    Label LTitulo;

    @FXML
    Label Lbusca;

    @FXML
    Label Ltipo;

    @FXML
    Label Lmotivo;


    ArrayList<Tipo> tipos = TipoDAO.buscarTipo1();
    ArrayList<Pessoa> pessoas =  PessoaDAO.buscarPessoa();
    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());

    public void initialize(){


        for(Tipo tipo : tipos){
            Ctipo.getItems().add(tipo.getDesc());
        }

        
        ArrayList<String> possibilidades = new ArrayList<>();
        for(Pessoa pessoa : pessoas){
            possibilidades.add(pessoa.getDocumento());
            possibilidades.add(pessoa.getNome());
        }

        Ibusca.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                for (String possibilidade : possibilidades) {
                        if (possibilidade.toLowerCase().trim().equals(Ibusca.getText().toLowerCase().trim())) {
                            for(Pessoa pessoa : pessoas){
                                if(pessoa.getNome().toLowerCase().trim().equals(possibilidade.toLowerCase().trim()) || pessoa.getDocumento().toLowerCase().trim().equals(possibilidade.toLowerCase().trim())){
                                    Inome.setText(pessoa.getNome());
                                    Itelefone.setText(pessoa.getTelefone());
                                    IRamal.setText(pessoa.getRamal());
                                    Idocumento.setText(pessoa.getDocumento());
                                    Ctipo.setValue(pessoa.getTipo());
                                    Lconfirma.setText("Confirmar os dados de cadastro:");
                                }
                            }
                        }
                }
            }
        });

        ListView<String> suggestionsListView = new ListView<>();
        suggestionsListView.setItems(FXCollections.observableArrayList(possibilidades));
        Hpossibilidades.getChildren().addAll(suggestionsListView);
        suggestionsListView.setOnMouseClicked(event -> {
            String selectedItem = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                suggestionsListView.getItems().clear();
                for(Pessoa pessoa : pessoas){
                    if(pessoa.getNome().toLowerCase().trim().equals(selectedItem.toLowerCase().trim()) || pessoa.getDocumento().toLowerCase().trim().equals(selectedItem.toLowerCase().trim())){
                        Inome.setText(pessoa.getNome());
                        Itelefone.setText(pessoa.getTelefone());
                        IRamal.setText(pessoa.getRamal());
                        Idocumento.setText(pessoa.getDocumento());
                        Ctipo.setValue(pessoa.getTipo());
                        Lconfirma.setText("Confirmar os dados de cadastro:");
                    }
                }
            }
        });

        Ibusca.setOnKeyReleased(event -> {
            String input = Ibusca.getText().toLowerCase();
            suggestionsListView.getItems().clear();

            for (String suggestion : possibilidades) {
                if (suggestion.toLowerCase().startsWith(input)) {
                    suggestionsListView.getItems().add(suggestion);
                }
            }
        });



        Bregistro.setOnAction((ActionEvent event) ->{
            if(temNoBancoP(Inome.getText()) == false && Integer.valueOf(IRamal.getText()) > 8000 && Integer.valueOf(IRamal.getText()) <= 8177){
                try {

                    int cod;
                    if(pessoas.size() == 0){
                        cod = 0;
                    }else{
                        cod = pessoas.get(pessoas.size()-1).getCodigo() + 1;
                    }
                    
                    
                    Pessoa pessNo = new Pessoa(cod, Inome.getText().trim(),Idocumento.getText().trim(), Itelefone.getText().trim(), IRamal.getText(), Ctipo.getValue());
                    PessoaDAO.adicionaPessoa(pessNo);
                    pessoas = PessoaDAO.buscarPessoa();

                    if(Imotivo.getText() == null){
                            Imotivo.setText( "  ");
                    }
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String data = currentDate.format(formatter);
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
                    String horaCompleta = currentTime.format(timeFormatter);
        
                    String HoraE = horaCompleta.substring(0, 8);

                    String entrada = data + " - " + HoraE;

                    int codV;
                    if(visitas.size() == 0){
                        codV = 0;
                    }else{
                        codV = visitas.get(visitas.size()-1).getCod() + 1;
                    }
                    if(temV(Inome.getText(),visitas)){
                        Bregistro.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
                    }else{
                        Visita visita = new Visita(codV, Imotivo.getText().trim(), entrada, "Não informada", Inome.getText().trim(), "A pé", Ctipo.getValue(), IRamal.getText(), usuAtual.getUsuario());
                        VisitaDao.adicionaVisita(visita);
                        visitas = VisitaDao.buscarVisitas();
                    }

                    

                    Bcancela.setText("Finalizar");
                    Lconfirma.setText("Registro Realizado!");


                } catch (IOException e) {   
                    e.printStackTrace();
                }
            }else{
                try {
                    if(Imotivo.getText() == null){
                            Imotivo.setText( "  ");
                    }
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String data = currentDate.format(formatter);
                    LocalTime currentTime = LocalTime.now();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
                    String horaCompleta = currentTime.format(timeFormatter);
        
                    String HoraE = horaCompleta.substring(0, 8);

                    String entrada = data + " - " + HoraE;

                    int codV;
                    if(visitas.size() == 0){
                        codV = 0;
                    }else{
                        codV = visitas.get(visitas.size()-1).getCod() + 1;
                    }

                    if(temV(Inome.getText(),visitas)){
                        Bregistro.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
                    }else{
                        Visita visita = new Visita(codV, Imotivo.getText().trim(), entrada, "Não informada", Inome.getText().trim(), "A pé", Ctipo.getValue(), IRamal.getText(), usuAtual.getUsuario());
                        VisitaDao.adicionaVisita(visita);
                        visitas = VisitaDao.buscarVisitas();
                        Bcancela.setText("Finalizar");
                        Lconfirma.setText("Registro Realizado!");
                    }

                } catch (IOException e) {   
                    e.printStackTrace();
                }
            }
        });


        Bcancela.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });

        Platform.runLater(() -> {
            aplicarTema(usuAtual);
        });
        
    }

    public Boolean temNoBancoP(String Nome){
        ArrayList<Pessoa> pessoasBanco = PessoaDAO.buscarPessoa();
        Boolean tem = false;
        for(Pessoa p : pessoasBanco){
            if(p.getNome().toLowerCase().trim().equals(Nome.toLowerCase().trim())){
                tem = true;
            }
        }

        return tem;
    }

    private void aplicarTema(Usuario usuAtual) {
        Scene scene = Bcancela.getScene(); // Troque Bpeople para Bcancela ou ajuste conforme necessário
        if (usuAtual.getTema() == 1) {
            // Configuração de cores para o tema escuro
            scene.getRoot().setStyle("-fx-background-color: #2E2E2E;");
            aplicarEstiloEscuro(Bcancela);
            aplicarEstiloEscuro(Bregistro);
            aplicarEstiloEscuro(Ctipo);
            aplicarEstiloEscuro(Lconfirma);
            aplicarEstiloEscuro(Imotivo);
            aplicarEstiloEscuro(Ibusca);
            aplicarEstiloEscuro(Idocumento);
            aplicarEstiloEscuro(Itelefone);
            aplicarEstiloEscuro(Inome);
            aplicarEstiloEscuro(LTitulo);
            aplicarEstiloEscuro(Lbusca);
            aplicarEstiloEscuro(Lmotivo);
            aplicarEstiloEscuro(Ltipo);
            aplicarEstiloEscuro(IRamal);
        } else {
            // Configuração de cores para o tema claro (pode ajustar conforme necessário)
            scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");
            resetarEstilo(Bcancela);
            resetarEstilo(IRamal);
            resetarEstilo(Bregistro);
            resetarEstilo(Ctipo);
            resetarEstilo(Lconfirma);
            resetarEstilo(Imotivo);
            resetarEstilo(Ibusca);
            resetarEstilo(Idocumento);
            resetarEstilo(Itelefone);
            resetarEstilo(Inome);
            resetarEstilo(LTitulo);
            resetarEstilo(Lbusca);
            resetarEstilo(Lmotivo);
            resetarEstilo(Ltipo);
        }
    }

    private void aplicarEstiloEscuro(Button button) {
        button.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
    }

    private void aplicarEstiloEscuro(Label label) {
        label.setTextFill(Color.WHITE);
    }

    private void aplicarEstiloEscuro(TextField textField) {
        textField.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
    }

    private void resetarEstilo(Button button) {
        button.setStyle(""); // Resetar para os estilos padrão
    }

    private void resetarEstilo(Label label) {
        label.setTextFill(Color.BLACK); // Resetar para a cor padrão
    }

    private void resetarEstilo(TextField textField) {
        textField.setStyle(""); // Resetar para os estilos padrão
    }


    private void aplicarEstiloEscuro(ComboBox<String> choiceBox) {
        choiceBox.setStyle(
            "-fx-background-color: #424242; " +
            "-fx-text-fill: White; " +
            "-fx-text-inner-color: white; " +
            "-fx-accent: white; "
        );

    }
    
    private void resetarEstilo(ComboBox<String> choiceBox) {
        choiceBox.setStyle(""); // Resetar para os estilos padrão
    }

    private void aplicarEstiloEscuro(TextArea textArea) {
        textArea.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF; -fx-control-inner-background: #424242;");
    }
    
    private void resetarEstilo(TextArea textArea) {
        textArea.setStyle("-fx-control-inner-background: #FFFFFF;"); // Resetar a cor de fundo interna para a cor padrão
    }

    public Boolean temV(String Nome, ArrayList<Visita> ops){
        Boolean tem = false;
        for(Visita p : ops){
            if(p.getPesNome().toLowerCase().trim().equals(Nome.toLowerCase().trim()) && p.getSaida().equals("Não informada")){
                tem = true;
            }
        }
        return tem;
    }
    
}
