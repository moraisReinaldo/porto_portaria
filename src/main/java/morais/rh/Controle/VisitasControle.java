package morais.rh.Controle;

import java.io.IOException;
import java.util.ArrayList;

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

public class VisitasControle {

    @FXML 
    Button BFinalizar;

    @FXML 
    Label LNome;

    @FXML 
    Label LPlaca;

    @FXML 
    Label LRamal;

    @FXML 
    Label LTitulo;

    @FXML 
    TextField INome;

    @FXML 
    TextField IRamal;

    @FXML 
    TextField IPlaca;

    @FXML
    VBox Vvisitas;

    ArrayList<Visita> visitas = VisitaDao.buscarVisitas();
    ArrayList<Visita> possibilidades = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuario();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());
    
    public void initialize() {

        for (Visita vis : visitas) {
            possibilidades.add(vis);
        }

        INome.setOnKeyReleased(event -> {
            String input = INome.getText().toLowerCase().trim();
            possibilidades.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getPesNome().toLowerCase().trim().startsWith(input))) {
                        possibilidades.add(visita);
                    }
                }
            } else {
                for (Visita vis : visitas) {
                    possibilidades.add(vis);
                }
            }
        });

        IRamal.setOnKeyReleased(event -> {
            String input = IRamal.getText().toLowerCase().trim();
            possibilidades.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getRamal().toLowerCase().trim().startsWith(input))) {
                        possibilidades.add(visita);
                    }
                }
            } else {
                for (Visita vis : visitas) {
                    possibilidades.add(vis);
                }
            }
        });

        IPlaca.setOnKeyReleased(event -> {
            String input = IPlaca.getText().toUpperCase().trim();
            possibilidades.clear(); // Limpa a lista para recriá-la
        
            if (input != null && !input.isEmpty()) {
                for (Visita visita : visitas) {
                    if ((visita.getVeiPlaca().toUpperCase().trim().startsWith(input))) {
                        possibilidades.add(visita);
                    }
                }
            } else {
                for (Visita vis : visitas) {
                    possibilidades.add(vis);
                }
            }
        });

        BFinalizar.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            gerarPessoas();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Platform.runLater(() -> {
            aplicarTema(usuAtual);
        });
    }

    private void aplicarTema(Usuario usuAtual) {
        Scene scene = BFinalizar.getScene();
        if (usuAtual.getTema() == 1) {
            // Configuração de cores para o tema escuro
            scene.getRoot().setStyle("-fx-background-color: #2E2E2E;");
            aplicarEstiloEscuro(LTitulo);
            aplicarEstiloEscuro(LPlaca);
            aplicarEstiloEscuro(LRamal);
            aplicarEstiloEscuro(LNome);
            aplicarEstiloEscuro(INome);
            aplicarEstiloEscuro(IPlaca);
            aplicarEstiloEscuro(IRamal);
            aplicarEstiloEscuro(BFinalizar);

        } else {
            // Configuração de cores para o tema claro (pode ajustar conforme necessário)
            scene.getRoot().setStyle("-fx-background-color: #FFFFFF;");
            resetarEstilo(LTitulo);
            resetarEstilo(LPlaca);
            resetarEstilo(LRamal);
            resetarEstilo(LNome);
            resetarEstilo(INome);
            resetarEstilo(IPlaca);
            resetarEstilo(IRamal);
            resetarEstilo(BFinalizar);
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


    private void gerarPessoas() {
        Vvisitas.getChildren().clear(); // Limpa os registros existentes

        HBox dados = new HBox();
            dados.setSpacing(20);
            dados.setMinHeight(20);
            dados.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label NomeD = new Label();
            NomeD.setText("Nome e tipo");
            NomeD.setWrapText(true);
            NomeD.setAlignment(Pos.CENTER);
            dados.getChildren().add(NomeD);

            Label entradaD = new Label();
            entradaD.setText("Entrada");
            entradaD.setMinWidth(70);
            entradaD.setWrapText(true);
            entradaD.setMaxWidth(70);
            entradaD.wrapTextProperty();
            entradaD.setAlignment(Pos.CENTER);
            dados.getChildren().add(entradaD);

            Label SaidaD = new Label();
            SaidaD.setText("Saida");
            SaidaD.setMinWidth(70);
            SaidaD.setWrapText(true);
            SaidaD.setMaxWidth(70);
            SaidaD.wrapTextProperty();
            SaidaD.setAlignment(Pos.CENTER);
            dados.getChildren().add(SaidaD);

            Label ramalD = new Label();
            ramalD.setText("Ramal");
            ramalD.setWrapText(true);
            ramalD.setAlignment(Pos.CENTER);
            dados.getChildren().add(ramalD);

            Label placaD = new Label();
            placaD.setText("Placa");
            placaD.setWrapText(true);
            placaD.setAlignment(Pos.CENTER);
            dados.getChildren().add(placaD);

            dados.setStyle(
                "-fx-background-color: GRAY;" +
                "-fx-background-radius: 20 20 20 20;" +
                "-fx-border-radius: 20 20 20 20;"
            );

            Vvisitas.getChildren().add(dados);

        for (Visita visita : possibilidades) {
            HBox pessoa = new HBox();
            pessoa.setSpacing(10);
            pessoa.setMinHeight(30);
            pessoa.setAlignment(Pos.CENTER); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome() + " " + visita.getTipo());
            Nome.setWrapText(true);
            Nome.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(Nome);

            Label entrada = new Label();
            entrada.setText(visita.getEntrada());
            entrada.wrapTextProperty();
            entrada.setMinWidth(70);
            entrada.setWrapText(true);
            entrada.setMaxWidth(70);
            entrada.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(entrada);

            Label Saida = new Label();
            Saida.setText(visita.getSaida());
            Saida.setMinWidth(70);
            Saida.setWrapText(true);
            Saida.setMaxWidth(70);
            Saida.wrapTextProperty();
            Saida.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(Saida);

            Label ramal = new Label();
            ramal.setText(visita.getRamal());
            ramal.setWrapText(true);
            ramal.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(ramal);

            Label placa = new Label();
            placa.setText(visita.getVeiPlaca());
            placa.setWrapText(true);
            placa.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(placa);

            
            if(visita.getCod() %2 == 0){
                pessoa.setStyle(
                    "-fx-background-color: GAINSBORO;" +
                    "-fx-background-radius: 20 20 20 20;" +
                    "-fx-border-radius: 20 20 20 20;"
                );
            }else{
                pessoa.setStyle(
                    "-fx-background-color: LAVENDER;" +
                    "-fx-background-radius: 20 20 20 20;" +
                    "-fx-border-radius: 20 20 20 20;"
                );
            }
            Vvisitas.getChildren().add(pessoa);
        
        }
    }
    
}
