package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.Modelo.Pessoa;

public class PessoaDAO {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    public static void adicionaPessoa(Pessoa pessoa) throws IOException {
        String sql = "INSERT INTO Pessoa(PesCodigo, PesNome, PesDoc, PesTelefone, PesRamal) VALUES(?, ?, ?, ?, ?)";
    
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, pessoa.getCodigo());
            stmt.setString(2, pessoa.getNome());
            stmt.setString(3, pessoa.getDocumento());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setString(5, pessoa.getRamal());
    
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar visita no banco de dados: " + e.getMessage());
        }
    }

    public static ArrayList<Pessoa> buscarPessoa() {
        ResultSet resultSet = null;
        ArrayList<Pessoa> pessoas = new ArrayList<>();
    
        try {
            String consulta = "SELECT * FROM Pessoa";
            PreparedStatement preparedStatement = conexao.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {

                int cod = resultSet.getInt("PesCodigo");
                String motivo = resultSet.getString("PesNome");
                String entrada = resultSet.getString("PesDoc");
                String saida = resultSet.getString("PesTelefone");
                String pesRamal = resultSet.getString("PesRamal");
    
                Pessoa pessoa = new Pessoa(cod, motivo, entrada, saida, pesRamal);
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pessoas no banco de dados: " + e.getMessage());
        }
    
        return pessoas;
    }
    

    public static void apagarPessoa(int cod) {
        try {
            String sql = "DELETE FROM Pessoa WHERE PesCodigo = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, cod);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Pessoa deletada com sucesso.");
            } else {
                System.out.println("Nenhuma pessoa foi deletada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar pessoa no banco de dados: " + e.getMessage());
        }
    }

}
