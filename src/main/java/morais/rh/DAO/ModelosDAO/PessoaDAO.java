package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ControleBanco2;
import morais.rh.Modelo.Pessoa;

public class PessoaDAO {

    static ControleBanco controle = new ControleBanco();
    static Connection conexao = controle.getConnection();

    static ControleBanco2 controle2 = new ControleBanco2();
    static Connection conexao2 = controle2.getConnection();

    public static void adicionaPessoa(Pessoa pessoa) throws IOException {
        String sql = "INSERT INTO Pessoa(PesCodigo, PesNome, PesDoc, PesTelefone, PesRamal, PesTipo) VALUES(?, ?, ?, ?, ?,?)";
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, pessoa.getCodigo());
                stmt.setString(2, pessoa.getNome());
                stmt.setString(3, pessoa.getDocumento());
                stmt.setString(4, pessoa.getTelefone());
                stmt.setString(5, pessoa.getRamal());
                stmt.setString(6, pessoa.getTipo());
    
                stmt.execute();
            }
    
            try (PreparedStatement stmt2 = conexao2.prepareStatement(sql)) {
                stmt2.setInt(1, pessoa.getCodigo());
                stmt2.setString(2, pessoa.getNome());
                stmt2.setString(3, pessoa.getDocumento());
                stmt2.setString(4, pessoa.getTelefone());
                stmt2.setString(5, pessoa.getRamal());
                stmt2.setString(6, pessoa.getTipo());
    
                stmt2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar pessoa no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
                if (conexao2 != null) {
                    conexao2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static void adicionaPessoa1(Pessoa pessoa) throws IOException {
        String sql = "INSERT INTO Pessoa(PesCodigo, PesNome, PesDoc, PesTelefone, PesRamal, PesTipo) VALUES(?, ?, ?, ?, ?,?)";
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, pessoa.getCodigo());
                stmt.setString(2, pessoa.getNome());
                stmt.setString(3, pessoa.getDocumento());
                stmt.setString(4, pessoa.getTelefone());
                stmt.setString(5, pessoa.getRamal());
                stmt.setString(6, pessoa.getTipo());
    
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar visita no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static ArrayList<Pessoa> buscarPessoa() {
        ResultSet resultSet = null;
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Pessoa")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("PesCodigo");
                    String nome = resultSet.getString("PesNome");
                    String documento = resultSet.getString("PesDoc");
                    String telefone = resultSet.getString("PesTelefone");
                    String ramal = resultSet.getString("PesRamal");
                    String tipo = resultSet.getString("PesTipo");
    
                    Pessoa pessoa = new Pessoa(cod, nome, documento, telefone, ramal, tipo);
                    pessoas.add(pessoa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pessoas no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pessoas;

    }

    public static ArrayList<Pessoa> buscarPessoa2() {
        ResultSet resultSet = null;
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement preparedStatement = conexao2.prepareStatement("SELECT * FROM Pessoa")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("PesCodigo");
                    String nome = resultSet.getString("PesNome");
                    String documento = resultSet.getString("PesDoc");
                    String telefone = resultSet.getString("PesTelefone");
                    String ramal = resultSet.getString("PesRamal");
                    String tipo = resultSet.getString("PesTipo");
    
                    Pessoa pessoa = new Pessoa(cod, nome, documento, telefone, ramal, tipo);
                    pessoas.add(pessoa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pessoas no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (conexao2 != null) {
                    conexao2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return pessoas;
    }
    

    public static void apagarPessoa(int cod) {
        try {
            conexao = controle.NovaConection();
            conexao2 = controle2.NovaConection();

            try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Pessoa WHERE PesCodigo = ?")) {
                preparedStatement.setInt(1, cod);
                int linhasAfetadas = preparedStatement.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Pessoa deletada com sucesso.");
                } else {
                    System.out.println("Nenhuma pessoa foi deletada.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar pessoa no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void UpPessoa() {
        try {
            ArrayList<Pessoa> pessoas1 = buscarPessoa();
            ArrayList<Pessoa> pessoas2 = buscarPessoa2();
            
            for (Pessoa pep : pessoas1) {
                apagarPessoa(pep.getCodigo());
            }
            for (Pessoa pep : pessoas2) {
                try {
                    adicionaPessoa1(pep);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                if (conexao != null) {
                    conexao.close();
                }
                if (conexao2 != null) {
                    conexao2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
