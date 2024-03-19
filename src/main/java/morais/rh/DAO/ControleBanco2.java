package morais.rh.DAO;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ControleBanco2 {

    private static final String DB_FILE_NAME = "db.sql";
    private static final String DB_PATH = Paths.get("").toAbsolutePath().resolve(DB_FILE_NAME).toString();

    private Connection conexao = null;

    public ControleBanco2() {
        if (verificarBancoExistente()) {
            conectarAoBanco();

        } else {
            criarBanco();
            conectarAoBanco();
            criarTabelas();  
        }
    }

    private void conectarAoBanco() {
        try {
            // Carregar o driver JDBC do SQLite
            Class.forName("org.sqlite.JDBC");

            // Estabelecer a conexão com o banco de dados SQLite
            conexao = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            System.out.println("Conexão bem-sucedida!");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver do SQLite não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Falha na conexão com o banco de dados!");
            e.printStackTrace();
        }
    }

    private void criarBanco() {
        try {
            // Criar o diretório do banco de dados se não existir
            File diretorioBanco = new File(DB_PATH).getParentFile();
            if (!diretorioBanco.exists()) {
                diretorioBanco.mkdirs();
            }

            // Criar o arquivo do banco de dados se não existir
            File arquivoBanco = new File(DB_PATH);
            if (arquivoBanco.createNewFile()) {
                System.out.println("Banco de dados criado: " + DB_PATH);
            } else {
                System.out.println("O banco de dados já existe: " + DB_PATH);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar o banco de dados!");
            e.printStackTrace();
        }
    }

    private void criarTabelas() {
        try (Statement statement = conexao.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Usuario (" +
                         "UsuSenha VARCHAR(20)," +
                         "UsuNome VARCHAR(60)," +
                         "UsuAtual INT," +
                         "UsuTema INT," +
                         "UsuCod INTEGER PRIMARY KEY" +
                         ");" +
                         "CREATE TABLE IF NOT EXISTS Veiculo (" +
                         "VeiPlaca VARCHAR(8) PRIMARY KEY," +
                         "VeiCor VARCHAR(60)," +
                         "VeiModelo VARCHAR(60)," +
                         "VeiRamal VARCHAR(4)" +
                         ");" +
                         "CREATE TABLE IF NOT EXISTS Pessoa (" +
                         "PesCodigo INTEGER PRIMARY KEY," +
                         "PesNome VARCHAR(100)," +
                         "PesDoc VARCHAR(13)," +
                         "PesTelefone VARCHAR(13)," +
                         "PesRamal VARCHAR(4)," +
                         "PesTipo VARCHAR(30)" +
                         ");" +
                         "CREATE TABLE IF NOT EXISTS Visita (" +
                         "VisCod INTEGER PRIMARY KEY," +
                         "VisMotivo VARCHAR(300)," +
                         "VisEntrada VARCHAR(100)," +
                         "VisSaida VARCHAR(100)," +
                         "PesNome VARCHAR(100)," +
                         "VisTipo VARCHAR(30)," +
                         "VeiPlaca VARCHAR(10)," +
                         "VisRamal VARCHAR(4)," +
                         "VisPort Varchar(300)"+
                         ");" +
                         "CREATE TABLE IF NOT EXISTS Tipo (" +
                         "TipoCod INTEGER PRIMARY KEY," +
                         "TipoDesc VARCHAR(60)" +
                         ");" +
                         "CREATE TABLE IF NOT EXISTS Altera (AterCod Integer primary key,situacao integer);" +
                         "INSERT INTO Usuario(UsuSenha, UsuNome,UsuCod, UsuAtual, UsuTema) VALUES('07051980', 'lucas', 0 , 0, 0);"+
                         "Insert into Altera(AterCod, situacao) Values (0,0)";
            statement.executeUpdate(sql);
            System.out.println("Tabelas criadas com sucesso!");
    
        } catch (SQLException e) {
            System.out.println("Erro ao criar as tabelas!");
            e.printStackTrace();
        }
    }

    public void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexão fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão!");
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return conexao;
    }

    private boolean verificarBancoExistente() {
        File arquivoBanco = new File(DB_PATH);
        return arquivoBanco.exists();
    }

    public Connection NovaConection(){
        conectarAoBanco();
        return conexao;
    }
}