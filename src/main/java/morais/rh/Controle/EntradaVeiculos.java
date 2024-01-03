package morais.rh.Controle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Tipo;
import morais.rh.Modelo.Usuario;
import morais.rh.Modelo.Veiculo;
import morais.rh.Modelo.Visita;

public class EntradaVeiculos {

    @FXML
    Button BadiPassa;

    @FXML
    Button Bcancela;

    @FXML
    Button Bregistro;

    @FXML
    ChoiceBox<String> Cramal;

    @FXML
    ChoiceBox<String> Ctipo;

    @FXML
    HBox Hpassageiro;

    @FXML
    HBox HVeiculos;

    @FXML
    TextArea Imotivo;
    
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

    @FXML
    VBox PessoasV;

    @FXML 
    Label Ltitulo;

    @FXML 
    Label Llis;

    @FXML 
    Label LDados;

    @FXML 
    Label LCads;

    @FXML 
    Label Lplaca;

    @FXML 
    Label LCor;

    @FXML 
    Label Lmode;

    @FXML 
    Label Lrama;

    @FXML 
    Label Lpessoas;

    @FXML 
    Label Lpeop;

    @FXML 
    Label Ltip;

    @FXML 
    Label Lmot;

    @FXML 
    Label Lenvio;

    ArrayList<Tipo> tipos = TipoDAO.buscarTipo1();
    ArrayList<Pessoa> pessoas =  PessoaDAO.buscarPessoa();
    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Veiculo> veiculos = VeiculoDAO.buscarVeiculo();
    ArrayList<Pessoa> pessoasVei = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());

    public void initialize(){

        ObservableList<String> tiposO = FXCollections.observableArrayList();
        for(Tipo tipo : tipos){
            tiposO.add(tipo.getDesc());
        }
        Ctipo.setItems(tiposO);
        
        ObservableList<String> ramais = FXCollections.observableArrayList();
        for(int i = 8001; i <=  8177; i++){
            ramais.add(Integer.toString(i));
        }
        Cramal.setItems(ramais);

        
        //Pessoa by Nome;
        ArrayList<String> possibilidadesP = new ArrayList<>();
        for(Pessoa pessoa : pessoas){
            possibilidadesP.add(pessoa.getNome());
        }
        Inome.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                for (String possibilidade : possibilidadesP) {
                        if (possibilidade.toLowerCase().trim().equals(Inome.getText().toLowerCase().trim())) {
                            for(Pessoa pessoa : pessoas){
                                if(pessoa.getNome().toLowerCase().trim().equals(possibilidade.toLowerCase().trim())){
                                    Inome.setText(pessoa.getNome());
                                    Itelefone.setText(pessoa.getTelefone());
                                    Idocumento.setText(pessoa.getDocumento());
                                    Ctipo.setValue(pessoa.getTipo());
                                }
                            }
                        }
                }
            }
        });
        ListView<String> suggestionsListView = new ListView<>();
        suggestionsListView.setItems(FXCollections.observableArrayList(possibilidadesP));
        Hpassageiro.getChildren().addAll(suggestionsListView);
        suggestionsListView.setOnMouseClicked(event -> {
            String selectedItem = suggestionsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                suggestionsListView.getItems().clear();
                for(Pessoa pessoa : pessoas){
                    if(pessoa.getNome().toLowerCase().trim().equals(selectedItem.toLowerCase().trim())){
                        Inome.setText(pessoa.getNome());
                        Itelefone.setText(pessoa.getTelefone());
                        Idocumento.setText(pessoa.getDocumento());
                        Ctipo.setValue(pessoa.getTipo());
                    }
                }
            }
        });
        Inome.setOnKeyReleased(event -> {
            String input = Inome.getText().toLowerCase();
            suggestionsListView.getItems().clear();

            for (String suggestion : possibilidadesP) {
                if (suggestion.toLowerCase().startsWith(input)) {
                    suggestionsListView.getItems().add(suggestion);
                }
            }
        });

        //Veiculo by Placa;
        ArrayList<String> possibilidadesV = new ArrayList<>();
        for(Veiculo pessoa : veiculos){
            possibilidadesV.add(pessoa.getPlaca());
        }
        Iplaca.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                for (String possibilidade : possibilidadesV) {
                        if (possibilidade.toLowerCase().trim().equals(Iplaca.getText().toLowerCase().trim())) {
                            for(Veiculo veiculo : veiculos){
                                if(veiculo.getPlaca().toLowerCase().trim().equals(possibilidade.toLowerCase().trim())){
                                    Iplaca.setText(veiculo.getPlaca());
                                    Icor.setText(veiculo.getCor());
                                    Imodelo.setText(veiculo.getModelo());
                                    Cramal.setValue(veiculo.getRamal());
                                }
                            }
                        }
                }
            }
        });
        ListView<String> suggestionsListViewV = new ListView<>();
        suggestionsListViewV.setItems(FXCollections.observableArrayList(possibilidadesV));
        HVeiculos.getChildren().addAll(suggestionsListViewV);
        suggestionsListViewV.setOnMouseClicked(event -> {
            String selectedItem = suggestionsListViewV.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                suggestionsListViewV.getItems().clear();
                for(Veiculo veiculo : veiculos){
                    if(veiculo.getPlaca().toLowerCase().trim().equals(selectedItem.toLowerCase().trim())){
                        Iplaca.setText(veiculo.getPlaca());
                        Icor.setText(veiculo.getCor());
                        Imodelo.setText(veiculo.getModelo());
                        Cramal.setValue(veiculo.getRamal());
                    }
                }
            }
        });
        Iplaca.setOnKeyReleased(event -> {
            String input = Iplaca.getText().toLowerCase();
            suggestionsListViewV.getItems().clear();

            for (String suggestion : possibilidadesV) {
                if (suggestion.toLowerCase().startsWith(input)) {
                    suggestionsListViewV.getItems().add(suggestion);
                }
            }
        });


        BadiPassa.setOnAction((ActionEvent event) ->{
            if(Iplaca.getText()!= null && Cramal.getValue()!= null){
                BadiPassa.setText("Adicionar passageiro");  
                    try {
                        Pessoa pessoa = new Pessoa(pessoasVei.size(), Inome.getText(), Idocumento.getText(), Itelefone.getText(), Cramal.getValue(), Ctipo.getValue());
                        pessoasVei.add(pessoa);
                        gerarP(pessoasVei);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }else{
            BadiPassa.setText("Preencha os dados do veiculo!");  
            }
        });

        Bregistro.setOnAction((ActionEvent event) ->{

            if(temNoBancoV(Iplaca.getText()) == false){
                Veiculo vei = new Veiculo(Iplaca.getText().toUpperCase(), Icor.getText(), Imodelo.getText(), Cramal.getValue());
                try {
                    VeiculoDAO.adicionaVeiculo(vei);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(verificaPlaca(Iplaca.getText().toUpperCase().trim()) && Iplaca.getText().length() == 7){       
                for(Pessoa peo : pessoasVei){
                    if(temNoBancoP(peo.getNome()) == false){
                        peo.setCodigo(pessoas.size());
                        try {
                            PessoaDAO.adicionaPessoa(peo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        pessoas.add(peo);
                    }

                    if(Imotivo.getText() == null){
                            Imotivo.setText("  ");
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

                    Visita visita = new Visita(codV, Imotivo.getText().trim(), entrada, "Não informada", peo.getNome(), Iplaca.getText().toUpperCase(), peo.getTipo(), Cramal.getValue());
                    try {
                        VisitaDao.adicionaVisita(visita);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    visitas.add(visita);

                    Bcancela.setText("Finalizar");
                    Bregistro.setText("Registro Realizado!");
                }
            }else{
                Lplaca.setText("Placa fora dos padrões!");
                Lplaca.setStyle("-fx-text-fill: #FF0000;");
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
    
    public void gerarP(ArrayList<Pessoa> pessoas){

        PessoasV.getChildren().clear();
        for(Pessoa pesoa: pessoas){
            HBox listinha = new HBox();
            listinha.setSpacing(20);

            Label cod = new Label(Integer.toString(pesoa.getCodigo()));
            Label nome = new Label(pesoa.getNome());
            Button remove = new Button("Remover passageiro");

            remove.setOnAction((ActionEvent event) ->{
                pessoasVei.remove(pesoa);
                gerarP(pessoasVei);

            });

            listinha.getChildren().addAll(cod, nome, remove);
            PessoasV.getChildren().add(listinha);

        }
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

    public Boolean temNoBancoV(String Placa){
        ArrayList<Veiculo> VeiculosBanco = VeiculoDAO.buscarVeiculo();
        Boolean tem = false;
        for(Veiculo v : VeiculosBanco){
            if(v.getPlaca().toUpperCase().trim().equals(Placa.toUpperCase().trim())){
                tem = true;
            }
        }

        return tem;
    }

    private void aplicarTema(Usuario usuAtual) {
        Scene scene = BadiPassa.getScene();
    
        if (usuAtual.getTema() == 1) {
            // Configuração de cores para o tema escuro
            scene.getRoot().setStyle("-fx-background-color: #2E2E2E;");
    
            // Aplicar estilos escuros
            aplicarEstiloEscuro(BadiPassa);
            aplicarEstiloEscuro(Bcancela);
            aplicarEstiloEscuro(Bregistro);
            aplicarEstiloEscuro(Cramal);
            aplicarEstiloEscuro(Ctipo);
            aplicarEstiloEscuro(Imotivo);
            aplicarEstiloEscuro(Icor);
            aplicarEstiloEscuro(Idocumento);
            aplicarEstiloEscuro(Imodelo);
            aplicarEstiloEscuro(Inome);
            aplicarEstiloEscuro(Iplaca);
            aplicarEstiloEscuro(Itelefone);
            aplicarEstiloEscuro(Ltitulo);
            aplicarEstiloEscuro(LDados);
            aplicarEstiloEscuro(LCads);
            aplicarEstiloEscuro(Lplaca);
            aplicarEstiloEscuro(LCor);
            aplicarEstiloEscuro(Lmode);
            aplicarEstiloEscuro(Lrama);
            aplicarEstiloEscuro(Lpessoas);
            aplicarEstiloEscuro(Lpeop);
            aplicarEstiloEscuro(Ltip);
            aplicarEstiloEscuro(Lmot);
            aplicarEstiloEscuro(Lenvio);
            aplicarEstiloEscuro(Llis);
    
        } else {
            // Configuração de cores para o tema claro
            scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");
    
            // Resetar estilos para os padrões claros
            resetarEstilo(BadiPassa);
            resetarEstilo(Bcancela);
            resetarEstilo(Bregistro);
            resetarEstilo(Cramal);
            resetarEstilo(Ctipo);
            resetarEstilo(Imotivo);
            resetarEstilo(Icor);
            resetarEstilo(Idocumento);
            resetarEstilo(Imodelo);
            resetarEstilo(Inome);
            resetarEstilo(Iplaca);
            resetarEstilo(Itelefone);
            resetarEstilo(Ltitulo);
            resetarEstilo(LDados);
            resetarEstilo(LCads);
            resetarEstilo(Lplaca);
            resetarEstilo(LCor);
            resetarEstilo(Lmode);
            resetarEstilo(Lrama);
            resetarEstilo(Lpessoas);
            resetarEstilo(Lpeop);
            resetarEstilo(Ltip);
            resetarEstilo(Lmot);
            resetarEstilo(Lenvio);
            resetarEstilo(Llis);
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


    private void aplicarEstiloEscuro(ChoiceBox<String> choiceBox) {
        choiceBox.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
    }
    
    private void resetarEstilo(ChoiceBox<String> choiceBox) {
        choiceBox.setStyle(""); // Resetar para os estilos padrão
    }

    private void aplicarEstiloEscuro(TextArea textArea) {
        textArea.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF; -fx-control-inner-background: #424242;");
    }
    
    private void resetarEstilo(TextArea textArea) {
        textArea.setStyle("-fx-control-inner-background: #FFFFFF;"); // Resetar a cor de fundo interna para a cor padrão
    }

    public static boolean verificaPlaca(String placa) {
        // Padrão para AAA0000 ou AAA0A00
        String regex = "^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}$";
        
        // Compilar o padrão
        Pattern pattern = Pattern.compile(regex);

        // Criar um objeto Matcher
        Matcher matcher = pattern.matcher(placa);

        // Verificar se a placa corresponde ao padrão
        return matcher.matches();
    }

}
