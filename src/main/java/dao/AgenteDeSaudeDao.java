package dao;

import model.AgenteDeSaude;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgenteDeSaudeDao {
    private Connection connection;

    public AgenteDeSaudeDao() throws SQLException {
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

    public void inserir(AgenteDeSaude agente) throws SQLException{
        String sql = "INSERT INTO agente_de_saude(nome,telefone,cpf,especialidade,data_contratacao) VALUES(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, agente.getNome());
        ps.setString(2, agente.getTelefone());
        ps.setString(3, agente.getCpf());
        ps.setString(4, agente.getEspecialidade());
        ps.setDate(5, agente.getDataContratacao());
        ps.execute();
    }

    public List<AgenteDeSaude> listarTodos() throws SQLException{
        List<AgenteDeSaude> agentes = new ArrayList<AgenteDeSaude>();

        ResultSet rs = connection.prepareStatement("SELECT * FROM agente_de_saude").executeQuery();
        while(rs.next()){
            agentes.add(new AgenteDeSaude(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("cpf"),
                    rs.getString("especialidade"),
                    rs.getDate("data_contratacao")));
        }
        rs.close();

        return agentes;
    }

    public void atualizar(AgenteDeSaude agente) throws SQLException {
        String sql = "UPDATE agente_de_saude SET nome = ?, telefone = ?, cpf = ?, especialidade = ? , data_contratacao = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, agente.getNome());
        ps.setString(2, agente.getTelefone());
        ps.setString(3, agente.getCpf());
        ps.setString(4, agente.getEspecialidade());
        ps.setDate(5, agente.getDataContratacao());
        ps.setInt(6, agente.getId());
        ps.execute();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM agente_de_saude WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }
}
