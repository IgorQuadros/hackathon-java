package dao;

import model.Agenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDao {
    private Connection connection;

    public AgendaDao() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/banco_hackathon?useTimezone=true&serverTimezone=UTC", "root", "");
    }

    public Connection getConnection() {
        return connection;
    }

    public void salvar(Agenda agenda) throws SQLException {
        String sql = "INSERT INTO agenda (idoso_id, agente_de_saude_id, data, hora, descricao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, agenda.getIdosoId());
            stmt.setInt(2, agenda.getAgenteDeSaudeId());
            stmt.setDate(3, agenda.getData());
            stmt.setTime(4, agenda.getHora());
            stmt.setString(5, agenda.getDescricao());
            stmt.executeUpdate();
        }
    }

    public List<Agenda> listarTodos() throws SQLException {
        List<Agenda> agendas = new ArrayList<>();

        String sql = "SELECT * FROM agenda";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Agenda agenda = new Agenda(
                        rs.getInt("id"),
                        rs.getInt("idoso_id"),
                        rs.getInt("agente_de_saude_id"),
                        rs.getDate("data"),
                        rs.getTime("hora"),
                        rs.getString("descricao"));
                agendas.add(agenda);
            }
        }

        return agendas;
    }

    public void atualizar(Agenda agenda) throws SQLException {
        String sql = "UPDATE agenda SET idoso_id = ?, agente_de_saude_id = ?, data = ?, hora = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, agenda.getIdosoId());
            ps.setInt(2, agenda.getAgenteDeSaudeId());
            ps.setDate(3, agenda.getData());
            ps.setTime(4, agenda.getHora());
            ps.setString(5, agenda.getDescricao());
            ps.setInt(6, agenda.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM agenda WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
