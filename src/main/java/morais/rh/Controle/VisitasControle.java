package morais.rh.Controle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    @FXML
    Button Bplan;

    ArrayList<Visita> visitas = VisitaDao.buscarVisitasFechadas();
    ArrayList<Visita> possibilidades = new ArrayList<>();
    ArrayList<Usuario> usuarios = UsuarioDAO.buscarUsuarioSQL();
    Usuario usuAtual = usuarios.get(usuarios.get(0).getAtual());
    
    public void initialize() {

        for (Visita vis : visitas) {
            if (vis.getCod() > (visitas.size() - 50)) {
                possibilidades.add(vis);
            }
        }

        gerarPessoas();

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
            gerarPessoas();
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
            gerarPessoas();
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
            gerarPessoas();
        });

        BFinalizar.setOnAction((ActionEvent event) -> {
            try {
                App.setRoot("TelaInicial");
            } catch (IOException e) {
            }
        });

        Bplan.setOnAction((ActionEvent event) -> {
            ControleBanco controle = new ControleBanco();
            Connection conexao = controle.NovaConection();
            List<String> TABLES = Arrays.asList("Usuario", "Veiculo", "Pessoa", "Visita", "Tipo", "VisitaFechada", "EntradaRapida");

        try (Workbook workbook = new XSSFWorkbook()){

            for (String table : TABLES) {
                createSheetForTable(conexao, workbook, table);
            }

            try (FileOutputStream fileOut = new FileOutputStream("Relatorio.xlsx")) {
                workbook.write(fileOut);
            }
            Bplan.setText("Planilha Gerada");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }});

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
            aplicarEstiloEscuro(Bplan);

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
            resetarEstilo(Bplan);
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
            dados.setMinHeight(30);
            dados.setMinWidth(890);
            dados.setAlignment(Pos.CENTER_LEFT); // Centraliza verticalmente

            Label NomeD = new Label();
            NomeD.setText("Nome e tipo");
            NomeD.setWrapText(true);
            NomeD.setAlignment(Pos.CENTER);
            NomeD.setMinWidth(300);
            dados.getChildren().add(NomeD);

            Label entradaD = new Label();
            entradaD.setText("Entrada");
            entradaD.setMinWidth(190);
            entradaD.setWrapText(true);
            entradaD.setMaxWidth(190);
            entradaD.wrapTextProperty();
            entradaD.setAlignment(Pos.CENTER);
            dados.getChildren().add(entradaD);

            Label SaidaD = new Label();
            SaidaD.setText("Saida");
            SaidaD.setMinWidth(180);
            SaidaD.setWrapText(true);
            SaidaD.setMaxWidth(180);
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
            if(visita.getCod() > possibilidades.size() - 200){

            
            HBox pessoa = new HBox();
            pessoa.setSpacing(10);
            pessoa.setMinHeight(20);
            pessoa.setMinWidth(890);
            pessoa.setAlignment(Pos.CENTER_LEFT); // Centraliza verticalmente

            Label Nome = new Label();
            Nome.setText(visita.getPesNome() + " " + visita.getTipo());
            Nome.setWrapText(true);
            Nome.setAlignment(Pos.CENTER);
            Nome.setMinWidth(300);
            pessoa.getChildren().add(Nome);


            Label entrada = new Label();
            entrada.setText(visita.getEntrada());
            entrada.wrapTextProperty();
            entrada.setMinWidth(200);
            entrada.setWrapText(true);
            entrada.setMaxWidth(200);
            entrada.setAlignment(Pos.CENTER);
            pessoa.getChildren().add(entrada);

            Label Saida = new Label();
            Saida.setText(visita.getSaida());
            Saida.setMinWidth(200);
            Saida.setWrapText(true);
            Saida.setMaxWidth(200);
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

    private static void createSheetForTable(Connection conn, Workbook workbook, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Sheet sheet = workbook.createSheet(tableName);
            createHeaderRow(rs, sheet);

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int colNum = 1; colNum <= rs.getMetaData().getColumnCount(); colNum++) {
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(colNum - 1);
                    cell.setCellValue(rs.getString(colNum));
                }
            }
        }
    }

    private static void createHeaderRow(ResultSet rs, Sheet sheet) throws SQLException {
        Row headerRow = sheet.createRow(0);
        for (int colNum = 1; colNum <= rs.getMetaData().getColumnCount(); colNum++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(colNum - 1);
            cell.setCellValue(rs.getMetaData().getColumnName(colNum));
        }
    }
    
}
