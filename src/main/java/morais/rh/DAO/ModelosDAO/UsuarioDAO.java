package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.Modelo.Usuario;

public class UsuarioDAO {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    public static void adicionaUsuario(Usuario usuario) throws IOException {
        String sql = "INSERT INTO Usuario(UsuCod,UsuSenha, UsuNome) VALUES(?,?, ?)";
    
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, usuario.getCod());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getUsuario());
    
            stmt.execute();
            stmt.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar usuario no banco de dados: " + e.getMessage());
        }
    }

    public static ArrayList<Usuario> buscarUsuario() {
        ResultSet resultSet = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
    
        try {
            String consulta = "SELECT * FROM Usuario";
            PreparedStatement preparedStatement = conexao.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {

                String senha = resultSet.getString("UsuSenha");
                String nome = resultSet.getString("UsuNome");
                int cod = resultSet.getInt("UsuCod");
    
                Usuario usuario = new Usuario(nome, senha,cod);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuarios no banco de dados: " + e.getMessage());
        }
    
        return usuarios;
    }
    

    public static void apagarUsuario(int cod) {
        if(cod != 0){
        try {
            String sql = "DELETE FROM Usuario WHERE UsuCod = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            preparedStatement.setInt(1, cod);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuario deletado com sucesso.");
            } else {
                System.out.println("Nenhum Usuario foi deletado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao apagar usuario no banco de dados: " + e.getMessage());
        }
        }
    }
}
