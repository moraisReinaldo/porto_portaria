package morais.rh.DAO.ModelosDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.DAO.ControleBanco2;
import morais.rh.Modelo.Usuario;

public class UsuarioDAO {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    static ControleBanco2 controle2 = new ControleBanco2();
    private static Connection conexao2 = controle2.getConnection();

    public static void adicionaUsuario(Usuario usuario) throws IOException {
        String sql = "INSERT INTO Usuario(UsuCod, UsuSenha, UsuNome, UsuAtual, UsuTema) VALUES(?, ?, ?, 0, 0)";
        conexao = controle.NovaConection();
    
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, usuario.getCod());
                stmt.setString(2, usuario.getSenha());
                stmt.setString(3, usuario.getUsuario());
    
                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar usuário no banco de dados: " + e.getMessage());
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
    
    public static ArrayList<Usuario> buscarUsuario() {
        ResultSet resultSet = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        conexao = controle.NovaConection();
    
        try {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Usuario")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    String senha = resultSet.getString("UsuSenha");
                    String nome = resultSet.getString("UsuNome");
                    int cod = resultSet.getInt("UsuCod");
                    int atual = resultSet.getInt("UsuAtual");
                    int tema = resultSet.getInt("UsuTema");
    
                    Usuario usuario = new Usuario(nome, senha, cod, atual, tema);
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuários no banco de dados: " + e.getMessage());
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
        return usuarios;
    }
    
    public static ArrayList<Usuario> buscarUsuario2() {
        ResultSet resultSet = null;
        ArrayList<Usuario> usuarios = new ArrayList<>();
        conexao2 = controle2.NovaConection();
    
        try {
            try (PreparedStatement preparedStatement = conexao2.prepareStatement("SELECT * FROM Usuario")) {
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    String senha = resultSet.getString("UsuSenha");
                    String nome = resultSet.getString("UsuNome");
                    int cod = resultSet.getInt("UsuCod");
                    int atual = resultSet.getInt("UsuAtual");
                    int tema = resultSet.getInt("UsuTema");
    
                    Usuario usuario = new Usuario(nome, senha, cod, atual, tema);
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuários no banco de dados: " + e.getMessage());
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
        return usuarios;
    }
    
    public static void apagarUsuario(int cod) {
        if (cod != 0) {
            try {
                conexao = controle.NovaConection();

                try (PreparedStatement preparedStatement = conexao.prepareStatement("DELETE FROM Usuario WHERE UsuCod = ?")) {
                    preparedStatement.setInt(1, cod);
    
                    int linhasAfetadas = preparedStatement.executeUpdate();
    
                    if (linhasAfetadas > 0) {
                        System.out.println("Usuário deletado com sucesso.");
                    } else {
                        System.out.println("Nenhum usuário foi deletado.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao apagar usuário no banco de dados: " + e.getMessage());
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
    }
    
    public static void atualizarTema(int cod, int tema) {
        String sql = "UPDATE Usuario SET UsuTema = ? WHERE UsuCod = ?";
        conexao = controle.NovaConection();
        
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, tema);
                stmt.setInt(2, cod);
    
                int linhasAfetadas = stmt.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Tema atualizado com sucesso.");
                } else {
                    System.out.println("Nenhuma tema foi atualizado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar tema no banco de dados: " + e.getMessage());
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
    
    public static void atualizarAtual(int cod, int novoA) {
        String sql = "UPDATE Usuario SET UsuAtual = ? WHERE UsuCod = ?";
        conexao = controle.NovaConection();
        
        try {
            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                stmt.setInt(1, novoA);
                stmt.setInt(2, cod);
    
                int linhasAfetadas = stmt.executeUpdate();
    
                if (linhasAfetadas > 0) {
                    System.out.println("Atual atualizado com sucesso.");
                } else {
                    System.out.println("Nenhum dado foi atualizado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar atual no banco de dados: " + e.getMessage());
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
    
    public static void UpUsuario() {
        try {
            ArrayList<Usuario> usu1 = buscarUsuario();
            ArrayList<Usuario> usu2 = buscarUsuario2();
    
            for (Usuario usu : usu1) {
                apagarUsuario(usu.getCod());
            }
            for (Usuario usu : usu2) {
                try {
                    if (usu.getCod() != 0) {
                        adicionaUsuario(usu);
                    }
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
