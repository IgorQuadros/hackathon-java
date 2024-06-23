package dao;

import model.Cuidador;
import model.Idoso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IdosoDao {
    private Connection connection;

    public IdosoDao() throws SQLException {
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

    public List<Idoso> listarTodos() throws SQLException {
        List<Idoso> idosos = new ArrayList<>();

        String sql = "SELECT i.id, i.nome, i.telefone, i.email, c.id AS cuidador_id " +
                "FROM idosos i " +
                "LEFT JOIN cuidador_has_idoso chi ON i.id = chi.idoso_id " +
                "LEFT JOIN cuidador c ON chi.cuidador_id = c.id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cuidador cuidador = null;
                if (rs.getInt("cuidador_id") != 0) {
                    cuidador = new Cuidador();
                    cuidador.setId(rs.getInt("cuidador_id"));
                }

                Idoso idoso = new Idoso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        cuidador
                );

                idosos.add(idoso);
            }
        }

        return idosos;
    }

}
