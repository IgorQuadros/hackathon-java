package dao;

import model.Cuidador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuidadorDao {
    private Connection connection;

    public CuidadorDao() throws SQLException {
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

    public void inserir(Cuidador cuidador) throws SQLException{
        String sql = "INSERT INTO cuidador(nome,telefone,cpf,data_registro) VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cuidador.getNome());
        ps.setString(2, cuidador.getTelefone());
        ps.setString(3, cuidador.getCpf());
        ps.setDate(4, cuidador.getDataRegistro());
        ps.execute();
    }

    public List<Cuidador> listarTodos() throws SQLException{
        List<Cuidador> cuidadores = new ArrayList<Cuidador>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM cuidador").executeQuery();
        while(rs.next()){
            cuidadores.add(new Cuidador(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cpf"),
                    rs.getDate("data_registro")));
        }
        rs.close();

        return cuidadores;
    }

    public void atualizar(Cuidador cuidador) throws SQLException {
        String sql = "UPDATE cuidador SET nome = ?, telefone = ?, cpf = ?, data_registro = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, cuidador.getNome());
        ps.setString(2, cuidador.getTelefone());
        ps.setString(3, cuidador.getCpf());
        ps.setDate(4, cuidador.getDataRegistro());
        ps.setInt(5, cuidador.getId());
        ps.execute();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM cuidador WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
