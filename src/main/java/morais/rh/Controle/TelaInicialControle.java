package morais.rh.Controle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import morais.rh.App;
import morais.rh.DAO.ModelosDAO.VisitaDao;
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
    Button Bconf;

    @FXML
    Button Bpeople;

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

    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Visita> possibilidades = new ArrayList<>();
    int moradores = 0;
    int veiculos = 0;
    int Nvisita = 0;

    public void initialize() {

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

        Bconf.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("Configuracao");
            } catch (IOException e) {
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
    }

    private void atualizarTimers() {
        Vregistros.getChildren().clear(); // Limpa os registros existentes

        for (Visita visita : possibilidades) {
            HBox pessoa = new HBox();
            pessoa.setSpacing(20);

            pessoa.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome() + "\n" + visita.getTipo());
            Nome.setMinWidth(100); // Define a largura mínima
            pessoa.getChildren().add(Nome);

            Label entrada = new Label();
            entrada.setText(visita.getEntrada());
            entrada.setWrapText(true);
            entrada.setMaxWidth(75);
            entrada.wrapTextProperty(); // Define a largura mínima
            pessoa.getChildren().add(entrada);


            

            Label ramal = new Label();
            ramal.setText(visita.getRamal());
            ramal.setMinWidth(20); // Define a largura mínima
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getVeiPlaca());
            placa.setMinWidth(20); // Define a largura mínima
            pessoa.getChildren().add(placa);

            Label timer = new Label();
            timer.setText(calcularTempoPassado(visita.getEntrada()));
            timer.setMinWidth(50); // Define a largura mínima
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
            finaliza.setMinWidth(100); // Define a largura mínima
            finaliza.setOnAction((ActionEvent event) -> {
                VisitaDao.atualizarVisitaData(visita.getCod(), hSaiada);
                visitas = VisitaDao.buscarVisitas();
                possibilidades.clear();
                for (Visita vis : visitas) {
                    if (vis.getSaida().equals("Não informada")) {
                        possibilidades.add(vis);
                    }
                }
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
}
