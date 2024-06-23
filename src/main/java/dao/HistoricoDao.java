package dao;

import model.Cuidador;
import model.Historico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDao {
    private Connection connection;

    public HistoricoDao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/banco_hackathon?useTimezone=true&serverTimezone=UTC", "root", "");
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void inserir(Historico historico) throws SQLException{
        String sql = "INSERT INTO historico (idoso_id, vacina_id, doencas_preexistentes) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, historico.getIdoso_id());
            ps.setInt(2, historico.getVacina_id());
            ps.setString(3, historico.getDoencas_preexistentes());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                historico.setId(rs.getInt(1));
            }
        }
    }

    public List<Historico> listarTodos() throws SQLException{
        List<Historico> historicos = new ArrayList<>();
        String sql = "SELECT h.id, h.idoso_id, v.id AS vacina_id, h.doencas_preexistentes " +
                "FROM historico h " +
                "JOIN vacina v ON h.vacina_id = v.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Historico historico = new Historico(
                        resultSet.getInt("id"),
                        resultSet.getInt("idoso_id"),
                        resultSet.getInt("vacina_id"),
                        resultSet.getString("doencas_preexistentes")
                );
                historicos.add(historico);
            }
        }
        return historicos;
    }

    public void atualizar(Historico historico) throws SQLException {
        String sql = "UPDATE historico SET idoso_id = ?, vacina_id = ?, doencas_preexistentes = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, historico.getIdoso_id());
            ps.setInt(2, historico.getVacina_id());
            ps.setString(3, historico.getDoencas_preexistentes());
            ps.setInt(4, historico.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM historico WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
