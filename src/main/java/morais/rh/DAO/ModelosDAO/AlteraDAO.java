package morais.rh.DAO.ModelosDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import morais.rh.DAO.ControleBanco2;

public class AlteraDAO {

    static ControleBanco2 controle = new ControleBanco2();
    private static Connection conexao = controle.getConnection();

    public static int buscarStatus() {
        int result = 0;
        ResultSet resultSet = null;
    
        try {
            String consulta = "SELECT * FROM Altera WHERE AterCod = 0";
            PreparedStatement preparedStatement = conexao.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {

                int cod = resultSet.getInt("AterCod");
                int desc = resultSet.getInt("situacao");
    
                result = desc;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar tipos no banco de dados: " + e.getMessage());
        }
    
        return result;
    }

    public static void atualizarSitua(int cod) {
        String sql = "UPDATE Altera SET situacao = ? WHERE AterCod = 0";
        
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, cod);
        
            int linhasAfetadas = stmt.executeUpdate();
    
            if (linhasAfetadas > 0) {
                System.out.println("Atualizado com sucesso.");
            } else {
                System.out.println("Nenhuma atualizado.");
            }
            
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar no banco de dados: " + e.getMessage());
        }
    }
    
}
