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
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.Usuario;
import morais.rh.Modelo.Visita;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    Label Lmoradores;

    @FXML
    Label Lveiculos;

    @FXML
    Label Lvisitantes;

    @FXML
    TextField Tbusca;

    @FXML
    VBox Vregistros;

    @FXML
    Label LTitulo;

    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Visita> possibilidades = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());
    int moradores = 0;
    int veiculos = 0;
    int Nvisita = 0;

    public void initialize() {

        MuL(visitas);

        for (Visita vis : visitas) {
            if (vis.getSaida().equals("Não informada")) {
                possibilidades.add(vis);
            }
        }

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
            pessoa.setSpacing(20);
            pessoa.setMinWidth(100);
            pessoa.setMaxWidth(100);

            pessoa.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome() + " " + visita.getTipo());
            Nome.setMinWidth(100);
            Nome.setWrapText(true);
            Nome.setMaxWidth(100); 
            Nome.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(Nome);

            Label entrada = new Label();
            entrada.setText(visita.getEntrada());
            entrada.setMinWidth(70);
            entrada.setWrapText(true);
            entrada.setMaxWidth(70);
            entrada.wrapTextProperty();
            entrada.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(entrada);

            Label ramal = new Label();
            ramal.setText(visita.getRamal());
            ramal.setMinWidth(40);
            ramal.setWrapText(true);
            ramal.setMaxWidth(40); 
            ramal.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getVeiPlaca());
            placa.setMinWidth(30);
            placa.setWrapText(true);
            placa.setMaxWidth(30); 
            placa.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(placa);

            Label timer = new Label();
            timer.setText(calcularTempoPassado(visita.getEntrada()));
            timer.setMinWidth(70);
            timer.setWrapText(true);
            timer.setMaxWidth(70); 
            timer.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(timer);

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String data = currentDate.format(formatter);
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSSSSS");
            String horaCompleta = currentTime.format(timeFormatter);

            String HoraE = horaCompleta.substring(0, 8);

            String hSaiada = data + " - " + HoraE;

            Button finaliza = new Button();
            finaliza.setText("Registrar saída");
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
                }
                MuL(visitas);
            });
            pessoa.getChildren().add(finaliza);

            pessoa.setAlignment(Pos.CENTER); // Centraliza horizontalmente
            Vregistros.getChildren().add(pessoa);
        
        }
    }

    public static String calcularTempoPassado(String dataHoraString) {
        LocalDateTime dataHoraInformada = converteStringParaLocalDateTime(dataHoraString);
        Duration tempoPassado = calculaTempoPassado(dataHoraInformada);
        return formatarDuracao(tempoPassado);
    }

    private static LocalDateTime converteStringParaLocalDateTime(String dataHoraString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return LocalDateTime.parse(dataHoraString, formatter);
    }

    private static Duration calculaTempoPassado(LocalDateTime dataHoraInformada) {
        LocalDateTime momentoAtual = LocalDateTime.now();
        return Duration.between(dataHoraInformada, momentoAtual);
    }

    private static String formatarDuracao(Duration duracao) {
        long segundos = duracao.getSeconds();
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        segundos = segundos % 60;

        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    public void MuL(ArrayList<Visita> visitas){
        moradores = 0;
        veiculos = 0;
        Nvisita = 0;
        
        for (Visita visita : visitas) {
            if (visita.getTipo().equals("Morador") && visita.getSaida().equals("Não informada")) {
                moradores += 1;
            }
            if (!visita.getVeiPlaca().equals("A pé") && visita.getSaida().equals("Não informada")) {
                veiculos += 1;
            }
            if (!visita.getTipo().equals("Morador") && visita.getSaida().equals("Não informada")) {
                Nvisita += 1;
            }
        }

        Lmoradores.setText("Moradores: " + moradores);
        Lvisitantes.setText("Visitantes: " + Nvisita);
        Lveiculos.setText("Veiculos: " + veiculos);
    }

    private void aplicarTema(Usuario usuAtual) {
        Scene scene = Bpeople.getScene();
        if (usuAtual.getTema() == 1) {
            // Configuração de cores para o tema escuro
            scene.getRoot().setStyle("-fx-background-color: #2E2E2E;");
            aplicarEstiloEscuro(Bpeople);
            aplicarEstiloEscuro(Btema);
            aplicarEstiloEscuro(Bveiculos);
            aplicarEstiloEscuro(Lmoradores);
            aplicarEstiloEscuro(Lveiculos);
            aplicarEstiloEscuro(Lvisitantes);
            aplicarEstiloEscuro(LTitulo);
            aplicarEstiloEscuro(Tbusca);
        } else {
            // Configuração de cores para o tema claro (pode ajustar conforme necessário)
            scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");
            resetarEstilo(Bpeople);
            resetarEstilo(Btema);
            resetarEstilo(Bveiculos);
            resetarEstilo(Lmoradores);
            resetarEstilo(Lveiculos);
            resetarEstilo(Lvisitantes);
            resetarEstilo(LTitulo);
            resetarEstilo(Tbusca);
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
    
    private void aplicarEstiloEscuro(Label label) {
        label.setTextFill(Color.WHITE);
    }

    private void aplicarEstiloEscuro(TextField textField) {
        textField.setStyle("-fx-background-color: #424242; -fx-text-fill: #FFFFFF;");
    }
}
