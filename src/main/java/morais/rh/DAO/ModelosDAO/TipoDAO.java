package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ControleBanco2;
import morais.rh.Modelo.Tipo;

public class TipoDAO {

    static ControleBanco controle = new ControleBanco();
    static ControleBanco2 controle2 = new ControleBanco2();

    public static void adicionaTipoSQL(Tipo tipo) throws IOException {
        String sql = "INSERT INTO Tipo(TipoCod, TipoDesc) VALUES(?, ?)";
        Connection conexao = controle.NovaConection();

        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, tipo.getCod());
                stmt.setString(2, tipo.getDesc());
    
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar tipo no banco de dados: " + e.getMessage());
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
    
    public static ArrayList<Tipo> buscarTipoSQL() {
        ResultSet resultSet = null;
        ArrayList<Tipo> tipos = new ArrayList<>();
        Connection conexao = controle.NovaConection();
    
        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Tipo")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("TipoCod");
                    String desc = resultSet.getString("TipoDesc");
    
                    Tipo tipo = new Tipo(cod, desc);
                    tipos.add(tipo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar tipos no banco de dados: " + e.getMessage());
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
        return tipos;
    }
    
    public static ArrayList<Tipo> buscarTipoPortatil() {
        ResultSet resultSet = null;
        ArrayList<Tipo> tipos = new ArrayList<>();
        Connection conexao2 = controle2.NovaConection();
    
        try {
            try (PreparedStatement preparedStatement = conexao2.prepareStatement("SELECT * FROM Tipo")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    int cod = resultSet.getInt("TipoCod");
                    String desc = resultSet.getString("TipoDesc");
    
                    Tipo tipo = new Tipo(cod, desc);
                    tipos.add(tipo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar tipos no banco de dados: " + e.getMessage());
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
        return tipos;
    }
    
    public static void apagarTipoSQL(int cod) {
        Connection conexao = controle.NovaConection();
        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Tipo WHERE TipoCod = ?")) {
                preparedStatement.setInt(1, cod);
    
                int linhasAfetadas = preparedStatement.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Tipo deletado com sucesso.");
                } else {
                    System.out.println("Nenhum Tipo foi deletado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar tipo no banco de dados: " + e.getMessage());
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
    
    public static void UpTipo() {
            ArrayList<Tipo> tipos1 = buscarTipoSQL();
            ArrayList<Tipo> tipos2 = buscarTipoPortatil();
    
            for (Tipo tip : tipos1) {
                apagarTipoSQL(tip.getCod());
            }
            for (Tipo tip : tipos2) {
                try {
                    adicionaTipoSQL(tip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}