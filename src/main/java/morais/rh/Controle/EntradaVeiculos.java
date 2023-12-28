package morais.rh.Controle;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Tipo;
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

    ArrayList<Tipo> tipos = TipoDAO.buscarUsuario();
    ArrayList<Pessoa> pessoas =  PessoaDAO.buscarPessoa();
    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Veiculo> veiculos = VeiculoDAO.buscarVeiculo();
    ArrayList<Pessoa> pessoasVei = new ArrayList<>();

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

                Visita visita = new Visita(visitas.size(), Imotivo.getText().trim(), entrada, "Não informada", peo.getNome(), Iplaca.getText().toUpperCase(), peo.getTipo(), Cramal.getValue());
                try {
                    VisitaDao.adicionaVisita(visita);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                visitas.add(visita);

                Bcancela.setText("Finalizar");
                Bregistro.setText("Registro Realizado!");
            }

        });

        Bcancela.setOnAction((ActionEvent event) ->{
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });
        
    }
    
    public void gerarP(ArrayList<Pessoa> pessoas){

        PessoasV.getChildren().clear();
        for(Pessoa pesoa: pessoas){
            HBox listinha = new HBox();
            listinha.setSpacing(20);

            Label cod = new Label(Integer.toString(pesoa.getCodigo()));
            Label nome = new Label(pesoa.getNome());
            Button remove = new Button("Remover pessoa");

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
    
}
