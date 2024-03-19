package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ControleBanco2;
import morais.rh.Modelo.Veiculo;

public class VeiculoDAO {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    static ControleBanco2 controle2 = new ControleBanco2();
    private static Connection conexao2 = controle2.getConnection();

    public static void adicionaVeiculo(Veiculo veiculo) throws IOException {
        String sql = "INSERT INTO Veiculo(VeiPlaca, VeiCor, VeiModelo, VeiRamal) VALUES(?, ?, ?, ?)";
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();
        
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, veiculo.getPlaca().toUpperCase());
                stmt.setString(2, veiculo.getCor());
                stmt.setString(3, veiculo.getModelo());
                stmt.setString(4, veiculo.getRamal());
      
                stmt.execute();
            }
    
            try (PreparedStatement stmt2 = conexao2.prepareStatement(sql)) {
                stmt2.setString(1, veiculo.getPlaca().toUpperCase());
                stmt2.setString(2, veiculo.getCor());
                stmt2.setString(3, veiculo.getModelo());
                stmt2.setString(4, veiculo.getRamal());
      
                stmt2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar veículo no banco de dados: " + e.getMessage());
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
    
    public static void adicionaVeiculo1(Veiculo veiculo) throws IOException {
        String sql = "INSERT INTO Veiculo(VeiPlaca, VeiCor, VeiModelo, VeiRamal) VALUES(?, ?, ?, ?)";
        conexao = controle.NovaConection();
        
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, veiculo.getPlaca().toUpperCase());
                stmt.setString(2, veiculo.getCor());
                stmt.setString(3, veiculo.getModelo());
                stmt.setString(4, veiculo.getRamal());
      
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar veículo no banco de dados: " + e.getMessage());
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
    
    public static ArrayList<Veiculo> buscarVeiculo() {
        ResultSet resultSet = null;
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        conexao = controle.NovaConection();
        
        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Veiculo")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    String placa = resultSet.getString("VeiPlaca");
                    String cor = resultSet.getString("VeiCor");
                    String modelo = resultSet.getString("VeiModelo");
                    String ramal = resultSet.getString("VeiRamal");
    
                    Veiculo veiculo = new Veiculo(placa, cor, modelo, ramal);
                    veiculos.add(veiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar veículos no banco de dados: " + e.getMessage());
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
        System.out.println("Veiculos buscados 1");
        return veiculos;
    }
    
    public static ArrayList<Veiculo> buscarVeiculo2() {
        ResultSet resultSet = null;
        ArrayList<Veiculo> veiculos = new ArrayList<>();
        conexao2 = controle2.NovaConection();
        
        try {
            try (PreparedStatement preparedStatement = conexao2.prepareStatement("SELECT * FROM Veiculo")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    String placa = resultSet.getString("VeiPlaca");
                    String cor = resultSet.getString("VeiCor");
                    String modelo = resultSet.getString("VeiModelo");
                    String ramal = resultSet.getString("VeiRamal");
    
                    Veiculo veiculo = new Veiculo(placa, cor, modelo, ramal);
                    veiculos.add(veiculo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar veículos no banco de dados: " + e.getMessage());
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
        System.out.println("Veiculos buscados 2");
        return veiculos;
    }
    
    public static void apagarVeiculo(String placa) {
        try {
            conexao = controle.NovaConection();
            try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Veiculo WHERE VeiPlaca = ?")) {
                preparedStatement.setString(1, placa.toUpperCase());
    
                int linhasAfetadas = preparedStatement.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Veículo deletado com sucesso.");
                } else {
                    System.out.println("Nenhum veículo foi deletado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar veículo no banco de dados: " + e.getMessage());
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
    
    public static void UpVeiculo() {
        try {
            ArrayList<Veiculo> usu1 = buscarVeiculo();
            ArrayList<Veiculo> usu2 = buscarVeiculo2();
            
            for(Veiculo usu : usu1){
                apagarVeiculo(usu.getPlaca());
            }
            for(Veiculo usu : usu2){
                try {
                    adicionaVeiculo1(usu);
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
