package dao;

import model.Vacinas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacinaDao {
    private static Connection connection;

    public VacinaDao() throws SQLException {
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

    public void salvar(Vacinas vacina) throws SQLException {
        String sql = "INSERT INTO vacina(nome, data_validade) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, vacina.getNome());
            ps.setDate(2, new java.sql.Date(vacina.getDataValidade().getTime()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                vacina.setId(rs.getInt(1));
            }
        }
    }

    public void atualizar(Vacinas vacina) throws SQLException {
        String sql = "UPDATE vacina SET nome = ?, data_validade = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vacina.getNome());
            ps.setDate(2, new java.sql.Date(vacina.getDataValidade().getTime()));
            ps.setInt(3, vacina.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM vacina WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static List<Vacinas> listarTodos() throws SQLException {
        List<Vacinas> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM vacina";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vacinas vacina = new Vacinas(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDate("data_validade")
                );
                vacinas.add(vacina);
            }
        }
        return vacinas;
    }
}