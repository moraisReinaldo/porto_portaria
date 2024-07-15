package morais.rh.Controle;

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
import morais.rh.DAO.ModelosDAO.EntradaRDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.EntradaRapida;
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


    ArrayList<Visita> visitas = VisitaDao.buscarVisitasSQL();
    ArrayList<Visita> possibilidades = VisitaDao.buscarVisitasSQL();    
    ArrayList<EntradaRapida> fastE = EntradaRDAO.listarEntradasRapidas();
    ArrayList<EntradaRapida> fastEE = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuarioSQL();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());
    int ultimoC =  VisitaDao.buscarVisitasFechadas().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod();

    public void initialize() {

        int ad = 0;
        for(EntradaRapida ite : fastE){
            if(ad < 30){
                fastEE.add(ite);
                ad++;
            }
        }

        if(VisitaDao.buscarVisitasFechadas().size() == 0 || VisitaDao.buscarVisitasSQL().size() == 0){
            if(VisitaDao.buscarVisitasFechadas().size() > VisitaDao.buscarVisitasSQL().size()){
                ultimoC =  VisitaDao.buscarVisitasFechadas().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod();
            }else{
                ultimoC =  VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod();
            }
        }else{
            if(VisitaDao.buscarVisitasFechadas().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod() < VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasSQL().size() - 1).getCod()){
                ultimoC = VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasSQL().size() - 1).getCod();
            }else if(VisitaDao.buscarVisitasFechadas().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod() == VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasSQL().size() - 1).getCod()){
                ultimoC = VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasSQL().size() - 1).getCod() + 2;
            }else if(VisitaDao.buscarVisitasFechadas().get(VisitaDao.buscarVisitasFechadas().size() - 1).getCod() == 0 && VisitaDao.buscarVisitasSQL().get(VisitaDao.buscarVisitasSQL().size() - 1).getCod() == 0){
                ultimoC = 0;
            }
        }

        atualizarTimers(); 
        atualizarTimers2();
        

        IBE.setOnKeyReleased(event -> {
            String input = IBE.getText().toLowerCase().trim();
            fastEE.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (EntradaRapida fast : fastE) {
                    if ((fast.getPesNome().toLowerCase().startsWith(input) ||
                        fast.getEntPlaca().toLowerCase().startsWith(input))){
                        fastEE.add(fast);
                    }
                }
                atualizarTimers2();
            } else {
                int pq = 0;
                for (EntradaRapida ita : fastE) {
                    if(pq < 20){
                        fastEE.add(ita);
                        pq ++;
                    }
                    
                }
                atualizarTimers2();
            }

        });

        Tbusca.setOnKeyReleased(event -> {
            String input = Tbusca.getText().toLowerCase().trim();
            possibilidades.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getPesNome().toLowerCase().startsWith(input) ||
                        visita.getVeiPlaca().toLowerCase().startsWith(input))) {
                        possibilidades.add(visita);
                    }
                }
                atualizarTimers();
            } else {
                for (Visita vis : visitas) {
                    if (vis.getSaida().equals("Não informada")) {
                        possibilidades.add(vis);
                    }
                }
                atualizarTimers();
            }

        });


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
            finaliza.setText("Saida");
            finaliza.setMinWidth(100);
            finaliza.setMaxWidth(100); 
            finaliza.setAlignment(Pos.CENTER);
            finaliza.setOnAction((ActionEvent event) -> {

                Visita visitaF = new Visita(ultimoC + 1, visita.getMotivo(), visita.getEntrada(), hSaiada, visita.getPesNome(), visita.getVeiPlaca(), visita.getTipo(), visita.getRamal(), visita.getPort());
                
                try {
                    VisitaDao.adicionaVisitaFechada(visitaF);
                    VisitaDao.apagarVisitaSQL(visita.getCod());
                } catch (Exception e) {
                }

                try {
                    VisitaDao.adicionaVisitaPortatil(visitaF);
                } catch (Exception e) {
                }
                
                if(!temPE(visita.getPesNome().toUpperCase().trim(), fastE, visita.getVeiPlaca().toUpperCase().trim(), visita.getRamal().trim())){
                    EntradaRapida  ent = new EntradaRapida(ultimoC +1, visita.getPesNome(),visita.getTipo(), visita.getVeiPlaca(), visita.getRamal());
                    EntradaRDAO.adicionarEntradaRapida(ent);
                    fastE.add(ent);
                    fastEE.add(ent);
                }
                ultimoC = ultimoC +1;

                visitas = VisitaDao.buscarVisitasSQL();
                possibilidades.clear();
                for (Visita vis : visitas) {
                    if (vis.getSaida().equals("Não informada")) {
                        possibilidades.add(vis);
                    }
                }

                atualizarTimers();
                atualizarTimers2();
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

        for (EntradaRapida visita : fastEE) {
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
            ramal.setText(visita.getEntRamal());
            ramal.setMinWidth(50);
            ramal.setWrapText(true);
            ramal.setMaxWidth(40); 
            ramal.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getEntPlaca());
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
                    if(temV(visita.getPesNome(), visitas)){
                        finaliza.setStyle("-fx-background-color: #ff0000; ");
                        finaliza.setText("Visita em aberto");
                        
                    }else{
                        Visita viu = new Visita(ultimoC, " ", Hentrada, "Não informada",visita.getPesNome() , visita.getEntPlaca(), visita.getEntTipo(), visita.getEntRamal() , usuAtual.getUsuario());
                        VisitaDao.adicionaVisitaSQL(viu);
                        visitas.add(viu);
                        possibilidades.add(viu);
                        ultimoC = ultimoC +1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                atualizarTimers();
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

    public Boolean temPE(String Nome, ArrayList<EntradaRapida> ops, String placa, String ramal){
        Boolean tem = false;
        for(EntradaRapida p : ops){
            if(p.getPesNome().toUpperCase().trim().equals(Nome.toUpperCase().trim()) && p.getEntPlaca().trim().toUpperCase().equals(placa.trim().toUpperCase()) && p.getEntRamal().equals(ramal)){
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
