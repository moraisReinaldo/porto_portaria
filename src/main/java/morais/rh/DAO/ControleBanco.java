package morais.rh.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import morais.rh.DAO.ModelosDAO.AlteraDAO;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;

public class ControleBanco {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/porto";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "rh0712";

    private Connection conexao = null;

    // Constructor to establish the database connection
    public ControleBanco() {
        conectarAoBanco();
    }

    private void conectarAoBanco() {
        try {
            // Load the JDBC driver for MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection to the MySQL database
            conexao = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Conexão bem-sucedida!");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver do MySQL não encontrado!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Falha na conexão com o banco de dados!");
            e.printStackTrace();
        }
    }

    // Close the database connection
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

    // Getter method to retrieve the connection
    public Connection getConnection() {
        return conexao;
    }

    public static void AtualizarB(){
        
        TipoDAO.UpTipo();
        PessoaDAO.UpPessoa();
        UsuarioDAO.UpUsuario();
        VeiculoDAO.UpVeiculo();
        VisitaDao.UpVisitas();
        AlteraDAO.atualizarSitua(1);
    }
}
