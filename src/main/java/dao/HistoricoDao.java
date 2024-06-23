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
        String sql = "INSERT INTO historico(idoso_id,doencas_preexistentes) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, historico.getIdoso_id());
        ps.setString(2, historico.getDoencas_preexistentes());
        ps.execute();
    }

    public List<Historico> listarTodos() throws SQLException{
        List<Historico> historicos = new ArrayList<Historico>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM historico").executeQuery();
        while(rs.next()){
            historicos.add(new Historico(
                    rs.getInt("id"),
                    rs.getInt("idoso_id"),
                    rs.getString("doencas_preexistentes")));
        }
        rs.close();

        return historicos;
    }

    public void atualizar(Historico historico) throws SQLException {
        String sql = "UPDATE historico SET idoso_id = ?, doencas_preexistentes = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, historico.getIdoso_id());
        ps.setString(2, historico.getDoencas_preexistentes());
        ps.setInt(3, historico.getId());
        ps.execute();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM historico WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
