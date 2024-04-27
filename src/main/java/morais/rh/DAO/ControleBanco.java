package morais.rh.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import morais.rh.DAO.ModelosDAO.AlteraDAO;
import morais.rh.DAO.ModelosDAO.EntradaRDAO;
import morais.rh.DAO.ModelosDAO.PessoaDAO;
import morais.rh.DAO.ModelosDAO.TipoDAO;
import morais.rh.DAO.ModelosDAO.UsuarioDAO;
import morais.rh.DAO.ModelosDAO.VeiculoDAO;
import morais.rh.DAO.ModelosDAO.VisitaDao;
import morais.rh.Modelo.EntradaRapida;
import morais.rh.Modelo.Pessoa;
import morais.rh.Modelo.Veiculo;

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

        ArrayList<Pessoa> pessoas = PessoaDAO.buscarPessoa();
        Set<String> nomesMaiusculos = new HashSet<>();
        // Lista para armazenar pessoas com nomes em letras maiúsculas e sem duplicatas
        List<Pessoa> pessoasNomesMaiusculosSemDuplicatas = new ArrayList<>();

        // Iterar sobre a lista original
        for (Pessoa pessoa : pessoas) {
            // Verificar se o nome está todo em maiúsculas e se ainda não foi adicionado
            if (nomesMaiusculos.add(pessoa.getNome().toUpperCase())) {
                // Se sim, adicionar à lista de pessoas com nomes em maiúsculas e sem duplicatas
                pessoasNomesMaiusculosSemDuplicatas.add(pessoa);
            }
        }

        for(Pessoa pe : pessoas){
            PessoaDAO.apagarPessoa(pe.getCodigo());
        }

        for(Pessoa peo : pessoasNomesMaiusculosSemDuplicatas){
            try {
                PessoaDAO.adicionaPessoa(peo);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        ArrayList<Veiculo> veicuilos = VeiculoDAO.buscarVeiculo();
        // Lista para armazenar pessoas com nomes em letras maiúsculas e sem duplicatas
        List<Veiculo> VeiculosSDu = new ArrayList<>();
        Set<String> placasOk = new HashSet<>();

        // Iterar sobre a lista original
        for (Veiculo veiculo : veicuilos) {
            // Verificar se o nome está todo em maiúsculas e se ainda não foi adicionado
            if (placasOk.add(veiculo.getPlaca())) {
                // Se sim, adicionar à lista de pessoas com nomes em maiúsculas e sem duplicatas
                VeiculosSDu.add(veiculo);
            }
        }

        for(Veiculo ve : veicuilos){
            VeiculoDAO.apagarVeiculo(ve.getPlaca());
        }

        for(Veiculo v : VeiculosSDu){
            try {
                VeiculoDAO.adicionaVeiculo1(v);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        ArrayList<EntradaRapida> entradas = EntradaRDAO.listarEntradasRapidas();
        List<EntradaRapida> EntradasOk = new ArrayList<>();
        Set<String> nomesOk = new HashSet<>();

        // Iterar sobre a lista original
        for (EntradaRapida entrada : entradas) {
            // Verificar se o nome está todo em maiúsculas e se ainda não foi adicionado
            if (nomesOk.add(entrada.getPesNome())) {
                // Se sim, adicionar à lista de pessoas com nomes em maiúsculas e sem duplicatas
                EntradasOk.add(entrada);
            }
        }

        for(EntradaRapida ent : entradas){
            EntradaRDAO.deletarEntradaRapida(ent.getEntCod());
        }

        for(EntradaRapida en : EntradasOk){
            EntradaRDAO.adicionarEntradaRapida(en);
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
