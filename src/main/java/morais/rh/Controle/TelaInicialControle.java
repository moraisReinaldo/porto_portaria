package morais.rh.Controle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import morais.rh.App;
import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ModelosDAO.AlteraDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Usuario;
import morais.rh.Modelo.Visita;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TelaInicialControle {

    @FXML
    Button Bpeople;

    @FXML
    Button Btema;

    @FXML
    Button Bveiculos;

    @FXML
    TextField Tbusca;

    @FXML
    VBox Vregistros;

    @FXML
    Label LTitulo;

    @FXML 
    HBox HFundo;

    @FXML 
    Label LDesc;

    @FXML
    Label LEntrada;

    @FXML 
    Label LRamal;

    @FXML
    Label LPlaca;

    @FXML 
    Label LConfirmar;

    @FXML
    Label LBE;

    @FXML 
    TextField IBE;

    @FXML 
    Label LPE;

    @FXML 
    Label LPA;

    @FXML
    Label LER;

    @FXML
    Label LRE;

    @FXML
    VBox VEM;

    @FXML 
    HBox HFundo1;

    @FXML 
    Button BControle;


    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Visita> possibilidades = new ArrayList<>();
    ArrayList<Visita> fastE = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());

    public void initialize() {

        System.out.println(visitas.size());
        for (Visita vis : visitas) {
            
            if (vis.getSaida().equals("Não informada")) {
                possibilidades.add(vis);
            }
            if(visitas.size() > 100){
                if(vis.getCod() > visitas.size() - 100 && !vis.getSaida().equals("Não informada") && temP(vis.getPesNome(), fastE, vis.getVeiPlaca(), vis.getRamal()) == false){
                    fastE.add(vis);
                }
            }else if(!vis.getSaida().equals("Não informada") && temP(vis.getPesNome(), fastE, vis.getVeiPlaca(), vis.getRamal()) == false){
                fastE.add(vis);
            }
        }
        

        IBE.setOnKeyReleased(event -> {
            String input = IBE.getText().toLowerCase().trim();
            fastE.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getPesNome().toLowerCase().startsWith(input) ||
                        visita.getVeiPlaca().toLowerCase().startsWith(input)) && !visita.getSaida().equals("Não informada") && temP(visita.getPesNome(), fastE, visita.getVeiPlaca(), visita.getRamal()) == false){
                        fastE.add(visita);
                    }
                }
            } else {
                for (Visita visita : visitas) {
                    if (!visita.getSaida().equals("Não informada") && temP(visita.getPesNome(), fastE, visita.getVeiPlaca(), visita.getRamal()) == false) {
                        fastE.add(visita);
                    }
                }
            }

        });

        Tbusca.setOnKeyReleased(event -> {
            String input = Tbusca.getText().toLowerCase().trim();
            possibilidades.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getPesNome().toLowerCase().startsWith(input) ||
                        visita.getVeiPlaca().toLowerCase().startsWith(input)) && visita.getSaida().equals("Não informada")) {
                        possibilidades.add(visita);
                    }
                }
            } else {
                for (Visita vis : visitas) {
                    if (vis.getSaida().equals("Não informada")) {
                        possibilidades.add(vis);
                    }
                }
            }

        });

        // Criação do Timeline para atualizar a cada segundo
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            atualizarTimers(); // Função para atualizar os timers
            atualizarTimers2();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Bpeople.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("EntradaPessoas");
            } catch (IOException e) {
            }
        });

        Bveiculos.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("EntradaVeiculos");
            } catch (IOException e) {
            }
        });

        BControle.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("ControleV");
            } catch (IOException e) {
            }
        });

        Btema.setOnAction((ActionEvent event) -> {
            //Tema 0 = claro
            //Tema 1 = escuro
            if(usuAtual.getTema() == 0){
                usuAtual.setTema(1);
                UsuarioDAO.atualizarTema(usuAtual.getCod(), 1);
                //Mudar tema 

            }else{
                usuAtual.setTema(0);
                UsuarioDAO.atualizarTema(usuAtual.getCod(), 0);
                //Mudar tema
            }
            aplicarTema(usuAtual);
        });

        Platform.runLater(() -> {
            aplicarTema(usuAtual);
        });
    }

    private void atualizarTimers() {
        Vregistros.getChildren().clear(); // Limpa os registros existentes

        for (Visita visita : possibilidades) {
            if(visita.getCod() > possibilidades.size() - 50){
            HBox pessoa = new HBox();
            pessoa.setSpacing(10);
            pessoa.setMaxWidth(550);
            pessoa.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome() + " " + visita.getTipo());
            Nome.setMinWidth(120);
            Nome.setWrapText(true);
            Nome.setMaxWidth(120); 
            Nome.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(Nome);

            Label entrada = new Label();
            entrada.setText(visita.getEntrada());
            entrada.setMinWidth(100);
            entrada.setWrapText(true);
            entrada.setMaxWidth(100);
            entrada.wrapTextProperty();
            entrada.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(entrada);

            Label ramal = new Label();
            ramal.setText(visita.getRamal());
            ramal.setMinWidth(50);
            ramal.setWrapText(true);
            ramal.setMaxWidth(50); 
            ramal.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getVeiPlaca());
            placa.setMinWidth(60);
            placa.setWrapText(true);
            placa.setMaxWidth(60); 
            placa.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(placa);

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = currentDate.format(formatter);
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
            String horaCompleta = currentTime.format(timeFormatter);

            String HoraE = horaCompleta.substring(0, 8);

            String hSaiada = data + " - " + HoraE;

            Button finaliza = new Button();
            finaliza.setText("Registrar");
            finaliza.setMinWidth(100);
            finaliza.setMaxWidth(100); 
            finaliza.setAlignment(Pos.CENTER);
            finaliza.setOnAction((ActionEvent event) -> {
                VisitaDao.atualizarVisitaData(visita.getCod(), hSaiada);
                visitas = VisitaDao.buscarVisitas();
                possibilidades.clear();
                for (Visita vis : visitas) {
                    if (vis.getSaida().equals("Não informada")) {
                        possibilidades.add(vis);
                    }
                    if(visitas.size() > 100){
                        if(vis.getCod() > visitas.size() - 100 && !vis.getSaida().equals("Não informada") && temP(vis.getPesNome(), fastE, vis.getVeiPlaca(), vis.getRamal()) == false){
                            fastE.add(vis);
                        }
                    }else if(!vis.getSaida().equals("Não informada") && temP(vis.getPesNome(), fastE, vis.getVeiPlaca(), vis.getRamal()) == false){
                        fastE.add(vis);
                    }
                }
            });

            if(visita.getCod() %2 == 0){
                pessoa.setStyle(
                    "-fx-background-color: GAINSBORO;" +
                    "-fx-background-radius: 20 20 20 20;" +
                    "-fx-border-radius: 20 20 20 20;"
                );
                aplicarEstiloEscuro(finaliza);
            }else{
                pessoa.setStyle(
                    "-fx-background-color: LAVENDER;" +
                    "-fx-background-radius: 20 20 20 20;" +
                    "-fx-border-radius: 20 20 20 20;"
                );
            }
            pessoa.getChildren().add(finaliza);
            Vregistros.getChildren().add(pessoa);
            }
        }
    }

    private void atualizarTimers2(){
        VEM.getChildren().clear();
         
        if(fastE.size() == 0){
            Label mes = new Label("Registros recentes não encontrados");
            Button novoV = new Button("Entrada de Veiculo");
            Button novoP = new Button("Entrada de pessoa");

            novoP.setOnAction((ActionEvent event) -> {
                try {
                    App.setRoot("EntradaPessoas");
                } catch (IOException e) {
                }
            });
    
            novoV.setOnAction((ActionEvent event) -> {
                try {
                    App.setRoot("EntradaVeiculos");
                } catch (IOException e) {
                }
            });

            VEM.getChildren().addAll(mes, novoV, novoP);

            
        }else{

        for (Visita visita : fastE) {
            HBox pessoa = new HBox();
            pessoa.setSpacing(10);
            pessoa.setMaxWidth(550);
            pessoa.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome());
            Nome.setMinWidth(100);
            Nome.setWrapText(true);
            Nome.setMaxWidth(100); 
            Nome.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(Nome);

            Label ramal = new Label();
            ramal.setText(visita.getRamal());
            ramal.setMinWidth(50);
            ramal.setWrapText(true);
            ramal.setMaxWidth(40); 
            ramal.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getVeiPlaca());
            placa.setMinWidth(70);
            placa.setWrapText(true);
            placa.setMaxWidth(70); 
            placa.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(placa);

            
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = currentDate.format(formatter);
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
            String horaCompleta = currentTime.format(timeFormatter);

            String HoraE = horaCompleta.substring(0, 8);

            String Hentrada = data + " - " + HoraE;

            Button finaliza = new Button();
            finaliza.setText("Entrada Rapída");
            finaliza.setMinWidth(100);
            finaliza.setMaxWidth(100); 
            finaliza.setAlignment(Pos.CENTER);
            finaliza.setOnAction((ActionEvent event) -> {                
                try {
                    if(temV(visita.getPesNome(), possibilidades)){
                        finaliza.setStyle("-fx-background-color: #FF0000; -fx-text-fill: #FFFFFF;");
                    }else{
                        Visita viu = new Visita(visitas.size(), visita.getMotivo(), Hentrada, "Não informada",visita.getPesNome() , visita.getVeiPlaca(), visita.getTipo(), visita.getRamal(), usuAtual.getUsuario());
                        VisitaDao.adicionaVisita(viu);
                        visitas.add(viu);
                        possibilidades.add(viu);
                        atualizarTimers();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            pessoa.setStyle(
                    "-fx-background-color: GAINSBORO;" +
                    "-fx-background-radius: 20 20 20 20;" +
                    "-fx-border-radius: 20 20 20 20;"
                );
            pessoa.getChildren().add(finaliza);
            VEM.getChildren().add(pessoa);
        
        }
        }
    }

    private void aplicarTema(Usuario usuAtual) {
        Scene scene = Bpeople.getScene();
        if (usuAtual.getTema() == 1) {
            // Configuração de cores para o tema escuro
            scene.getRoot().setStyle("-fx-background-color: #2E2E2E;");
            aplicarEstiloEscuro(Bpeople);
            aplicarEstiloEscuro(Btema);
            aplicarEstiloEscuro(Bveiculos);
            aplicarEstiloEscuro(LTitulo);
            aplicarEstiloEscuro(Tbusca);
            aplicarEstiloEscuro(LConfirmar);
            aplicarEstiloEscuro(LPlaca);
            aplicarEstiloEscuro(LRamal);
            aplicarEstiloEscuro(LEntrada);
            aplicarEstiloEscuro(LDesc);
            aplicarEstiloEscuro(HFundo);
            aplicarEstiloEscuro(LBE);
            aplicarEstiloEscuro(IBE);
            aplicarEstiloEscuro(LPA);
            aplicarEstiloEscuro(LER);
            aplicarEstiloEscuro(LPE);
            aplicarEstiloEscuro(LRE);
            aplicarEstiloEscuro(HFundo1);
            aplicarEstiloEscuro(BControle);

        } else {
            // Configuração de cores para o tema claro (pode ajustar conforme necessário)
            scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");
            resetarEstilo(Bpeople);
            resetarEstilo(Btema);
            resetarEstilo(Bveiculos);
            resetarEstilo(LTitulo);
            resetarEstilo(Tbusca);
            resetarEstilo(LConfirmar);
            resetarEstilo(LPlaca);
            resetarEstilo(LRamal);
            resetarEstilo(LEntrada);
            resetarEstilo(LDesc);
            resetarEstilo(HFundo);
            resetarEstilo(LBE);
            resetarEstilo(IBE);
            resetarEstilo(LPA);
            resetarEstilo(LER);
            resetarEstilo(LPE);
            resetarEstilo(LRE);
            resetarEstilo(HFundo1);
            resetarEstilo(BControle);

        }
    }

    private void aplicarEstiloEscuro(Button button) {
        button.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
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

    private void resetarEstilo(HBox textField) {
        textField.setStyle(""); // Resetar para os estilos padrão
    }
    
    private void aplicarEstiloEscuro(Label label) {
        label.setTextFill(Color.WHITE);
    }
    private void aplicarEstiloEscuro(HBox label) {
        label.setStyle("-fx-background-color: #424242;");
    }

    private void aplicarEstiloEscuro(TextField textField) {
        textField.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
    }

    public Boolean temP(String Nome, ArrayList<Visita> ops, String placa, String ramal){
        Boolean tem = false;
        for(Visita p : ops){
            if(p.getPesNome().toLowerCase().trim().equals(Nome.toLowerCase().trim()) && p.getVeiPlaca().trim().toUpperCase().equals(placa.trim().toUpperCase()) && p.getRamal().equals(ramal)){
                tem = true;
            }
        }
        return tem;
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
