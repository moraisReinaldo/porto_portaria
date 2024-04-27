package morais.rh.DAO.ModelosDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morais.rh.DAO.ControleBanco;
import morais.rh.Modelo.EntradaRapida;

public class EntradaRDAO {

    static ControleBanco controle = new ControleBanco();
    private static Connection conexao = controle.getConnection();

    public static void adicionarEntradaRapida(EntradaRapida entrada) {
        try {
            String query = "INSERT INTO EntradaRapida (EntCod, PesNome, EntTipo, EntPlaca, EntRamal) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, entrada.getEntCod());
            statement.setString(2, entrada.getPesNome().toUpperCase().trim());
            statement.setString(3, entrada.getEntTipo());
            statement.setString(4, entrada.getEntPlaca().toUpperCase().trim());
            statement.setString(5, entrada.getEntRamal().trim());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deletarEntradaRapida(int codigo) {
        try {
            String query = "DELETE FROM EntradaRapida WHERE EntCod = ?";
            PreparedStatement statement = conexao.prepareStatement(query);
            statement.setInt(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<EntradaRapida> listarEntradasRapidas() {
        ArrayList<EntradaRapida> entradas = new ArrayList<>();
        try {
            String query = "SELECT * FROM EntradaRapida";
            PreparedStatement statement = conexao.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int entCod = resultSet.getInt("EntCod");
                String pesNome = resultSet.getString("PesNome");
                String entTipo = resultSet.getString("EntTipo");
                String entPlaca = resultSet.getString("EntPlaca");
                String entRamal = resultSet.getString("EntRamal");

                entradas.add(new EntradaRapida(entCod, pesNome, entTipo, entPlaca, entRamal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entradas;
    }

    
}
