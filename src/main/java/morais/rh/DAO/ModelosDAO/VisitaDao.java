package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ControleBanco2;
import morais.rh.Modelo.Visita;

public class VisitaDao {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    static ControleBanco2 controle2 = new ControleBanco2();
    private static Connection conexao2 = controle2.getConnection();

    public static void adicionaVisita(Visita visita) throws IOException {
        String sql = "INSERT INTO Visita(VisCod, VisMotivo, VisEntrada, VisSaida, PesNome, VisTipo, VeiPlaca, VisRamal, VisPort) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, visita.getCod());
                stmt.setString(2, visita.getMotivo());
                stmt.setString(3, visita.getEntrada());
                stmt.setString(4, visita.getSaida());
                stmt.setString(5, visita.getPesNome());
                stmt.setString(6, visita.getTipo());
                stmt.setString(7, visita.getVeiPlaca());
                stmt.setString(8, visita.getRamal());
                stmt.setString(9, visita.getPort());
      
                stmt.execute();
            }
    
            try (PreparedStatement stmt2 = conexao2.prepareStatement(sql)) {
                stmt2.setInt(1, visita.getCod());
                stmt2.setString(2, visita.getMotivo());
                stmt2.setString(3, visita.getEntrada());
                stmt2.setString(4, visita.getSaida());
                stmt2.setString(5, visita.getPesNome());
                stmt2.setString(6, visita.getTipo());
                stmt2.setString(7, visita.getVeiPlaca());
                stmt2.setString(8, visita.getRamal());
                stmt2.setString(9, visita.getPort());
      
                stmt2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar visita no banco de dados: " + e.getMessage());
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
    
    public static void adicionaVisita2(Visita visita) throws IOException {
        String sql = "INSERT INTO Visita(VisCod, VisMotivo, VisEntrada, VisSaida, PesNome, VisTipo, VeiPlaca, VisRamal, VisPort) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        conexao2 = controle2.NovaConection();

        try {
            try (PreparedStatement stmt2 = conexao2.prepareStatement(sql)) {
                stmt2.setInt(1, visita.getCod());
                stmt2.setString(2, visita.getMotivo());
                stmt2.setString(3, visita.getEntrada());
                stmt2.setString(4, visita.getSaida());
                stmt2.setString(5, visita.getPesNome());
                stmt2.setString(6, visita.getTipo());
                stmt2.setString(7, visita.getVeiPlaca());
                stmt2.setString(8, visita.getRamal());
                stmt2.setString(9, visita.getPort());
      
                stmt2.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar visita no banco de dados: " + e.getMessage());
        } finally {
            try {
                if (conexao2 != null) {
                    conexao2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static ArrayList<Visita> buscarVisitas() {
        ResultSet resultSet = null;
        ArrayList<Visita> visitas = new ArrayList<>();
        conexao = controle.NovaConection();
        
        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Visita")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("VisCod");
                    String motivo = resultSet.getString("VisMotivo");
                    String entrada = resultSet.getString("VisEntrada");
                    String saida = resultSet.getString("VisSaida");
                    String pesNome = resultSet.getString("PesNome");
                    String tipo = resultSet.getString("VisTipo");
                    String vei = resultSet.getString("VeiPlaca");
                    String ramal = resultSet.getString("VisRamal");
                    String port = resultSet.getString("VisPort");
        
                    Visita visita = new Visita(cod, motivo, entrada, saida, pesNome, vei, tipo, ramal, port);
                    visitas.add(visita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar visitas no banco de dados: " + e.getMessage());
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
        System.out.println("visitas encontradas");
        return visitas;
    }
    
    public static ArrayList<Visita> buscarVisitas2() {
        ResultSet resultSet = null;
        ArrayList<Visita> visitas = new ArrayList<>();
        conexao2 = controle2.NovaConection();
        
        try {
            try (PreparedStatement preparedStatement = conexao2.prepareStatement("SELECT * FROM Visita")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("VisCod");
                    String motivo = resultSet.getString("VisMotivo");
                    String entrada = resultSet.getString("VisEntrada");
                    String saida = resultSet.getString("VisSaida");
                    String pesNome = resultSet.getString("PesNome");
                    String tipo = resultSet.getString("VisTipo");
                    String vei = resultSet.getString("VeiPlaca");
                    System.out.println(vei);
                    String ramal = resultSet.getString("VisRamal");
                    String port = resultSet.getString("VisPort");
        
                    Visita visita = new Visita(cod, motivo, entrada, saida, pesNome, vei, tipo, ramal, port);
                    visitas.add(visita);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar visitas no banco de dados: " + e.getMessage());
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
        System.out.println("visitas 2");
        return visitas;
    }
    
    public static void apagarVisita(int cod) {
        try {
            conexao = controle.NovaConection();
            try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Visita WHERE VisCod = ?")) {
                preparedStatement.setInt(1, cod);
                int linhasAfetadas = preparedStatement.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Visita deletada com sucesso.");
                } else {
                    System.out.println("Nenhuma visita foi deletada.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar visita no banco de dados: " + e.getMessage());
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
    
    public static void atualizarVisitaData(int cod, String novaS) {
        String sql = "UPDATE Visita SET VisSaida = ? WHERE VisCod = ?";
        conexao = controle.NovaConection();
        conexao2 = controle2.NovaConection();
        int linhasAfetadas;
        int linhasAfetadas2;    
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setString(1, novaS);
                stmt.setInt(2, cod);
                linhasAfetadas = stmt.executeUpdate();
            }
    
            try (PreparedStatement stmt2 = conexao2.prepareStatement(sql)) {
                stmt2.setString(1, novaS);
                stmt2.setInt(2, cod);
                linhasAfetadas2 = stmt2.executeUpdate();
            }
    
            if (linhasAfetadas > 0 && linhasAfetadas2 > 0) {
                System.out.println("Visita atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma visita foi atualizada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar visita no banco de dados: " + e.getMessage());
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
    
    public static void UpVisitas() {
        ArrayList<Visita> usu1 = buscarVisitas();
        ArrayList<Visita> usu2 = buscarVisitas2();
            
        if (usu1.size() > usu2.size()) {
            for (int i = (usu2.size() - 1); i < usu1.size(); i++) {
                try {
                    usu2.add(usu1.get(i));
                    adicionaVisita2(usu1.get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
