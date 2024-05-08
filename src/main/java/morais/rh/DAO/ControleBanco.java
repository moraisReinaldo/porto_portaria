package morais.rh.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ModelosDAO.AlteraDAO;
import morais.rh.DAO.ModelosDAO.EntradaRDAO;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.EntradaRapida;
import morais.rh.Modelo.Pessoa;

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

    public static void Rduplicados(){

        ArrayList<Pessoa> pessoas = PessoaDAO.buscarPessoaSQL();
        ArrayList<Pessoa> SemDupli = new ArrayList<>();
        // Lista para armazenar pessoas com nomes em letras maiúsculas e sem duplicatas
        SemDupli.add(pessoas.get(0));
        // Iterar sobre a lista original
        for (Pessoa pessoa : pessoas) {
            for(Pessoa s : SemDupli){
                if(!(pessoa.getNome().equals(s.getNome()))){
                    SemDupli.add(pessoa);
                }
            }
        }

        if(pessoas.size() > SemDupli.size()){
            for(Pessoa peo : pessoas){
                PessoaDAO.apagarPessoaSQL(peo.getCodigo());
            }
            for(Pessoa peo : SemDupli){
                try {
                    PessoaDAO.adicionaPessoaBancoSQL(peo);
                } catch (IOException e) {
                };
            }
        }

        ArrayList<EntradaRapida> entradas = EntradaRDAO.listarEntradasRapidas();
        ArrayList<EntradaRapida> EntradasOk = new ArrayList<>();

        EntradasOk.add(entradas.get(0));

        for (EntradaRapida entrada : entradas) {
            // Verificar se o nome está todo em maiúsculas e se ainda não foi adicionado
            for(EntradaRapida e : EntradasOk){
                if(!(e.getPesNome().toUpperCase().trim().equals(entrada.getPesNome().toUpperCase().trim()) || e.getEntPlaca().toUpperCase().trim().equals(entrada.getEntPlaca().toUpperCase().trim()) || entrada.getEntRamal() == e.getEntRamal())){
                    EntradasOk.add(entrada);
                }
            }
        }

        if(EntradasOk.size() < entradas.size()){
            for(EntradaRapida ent : entradas){
                EntradaRDAO.deletarEntradaRapida(ent.getEntCod());
            }
    
            for(EntradaRapida en : EntradasOk){
                EntradaRDAO.adicionarEntradaRapida(en);
            }

        }
    }

    public static void AtualizarB(){
        
        TipoDAO.UpTipo();
        PessoaDAO.UpPessoa();
        UsuarioDAO.UpUsuario();
        VeiculoDAO.UpVeiculo();
        VisitaDao.UpVisitas();
        AlteraDAO.atualizarSitua(1);
    }

    public Connection NovaConection(){
        conectarAoBanco();
        return conexao;
    }

}
