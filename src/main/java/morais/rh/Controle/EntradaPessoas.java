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
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Tipo;
import morais.rh.Modelo.Visita;

public class EntradaPessoas {

    @FXML
    Button Bcancela;

    @FXML
    Button Bregistro;

    @FXML
    ChoiceBox<String> Ctipo;

    @FXML
    ChoiceBox<String> Cramais;
    
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

    ArrayList<Tipo> tipos = TipoDAO.buscarUsuario();
    ArrayList<Pessoa> pessoas =  PessoaDAO.buscarPessoa();
    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    Boolean pessoaNova = true;

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
        Cramais.setItems(ramais);


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
                                    Cramais.setValue(pessoa.getRamal());
                                    Idocumento.setText(pessoa.getDocumento());
                                    Ctipo.setValue(pessoa.getTipo());
                                    pessoaNova = false;
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
                        Cramais.setValue(pessoa.getRamal());
                        Idocumento.setText(pessoa.getDocumento());
                        Ctipo.setValue(pessoa.getTipo());
                        pessoaNova = false;
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
            if(pessoaNova){
                try {
                    Pessoa pessNo = new Pessoa(pessoas.size(), Inome.getText().trim(),Idocumento.getText().trim(), Itelefone.getText().trim(), Cramais.getValue(), Ctipo.getValue());
                    PessoaDAO.adicionaPessoa(pessNo);

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

                    Visita visita = new Visita(visitas.size(), Imotivo.getText().trim(), entrada, "Não informada", Inome.getText().trim(), "A pé", Ctipo.getValue(), Cramais.getValue());
                    VisitaDao.adicionaVisita(visita);
                    visitas = VisitaDao.buscarVisitas();

                    Bcancela.setText("Finalizar");
                    Lconfirma.setText("Registro Realizado! Novo registro?");


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

                    Visita visita = new Visita(visitas.size(), Imotivo.getText().trim(), entrada, "Não informada", Inome.getText().trim(), "A pé", Ctipo.getValue(), Cramais.getValue());
                    VisitaDao.adicionaVisita(visita);
                    visitas = VisitaDao.buscarVisitas();

                    Bcancela.setText("Finalizar");
                    Lconfirma.setText("Registro Realizado! Novo registro?");

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
        
    }
    
}
