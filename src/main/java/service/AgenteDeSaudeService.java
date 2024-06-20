package service;

import dao.AgenteDeSaudeDao;
import dao.CuidadorDao;
import model.AgenteDeSaude;
import model.Cuidador;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;

public class AgenteDeSaudeService {
    public void salvar(AgenteDeSaude agente){
        try{
            validarCamposObrigatorios(agente);
            var dao = new AgenteDeSaudeDao();

            if(agente.getId() == null){
                //dao.inserir(agente);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        try{
            var dao = new AgenteDeSaudeDao();
            dao.deletar(id);
        }catch(SQLException e){
            System.out.println("Erro! Não foi possível deletar o agente: " + e.getMessage());
        }
    }

    public List<AgenteDeSaude> listarAgentes(){
        try {
            var dao = new AgenteDeSaudeDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void validarCamposObrigatorios(AgenteDeSaude agente) throws Exception{
        if(agente.getNome() == null || agente.getNome().trim().isEmpty()){
            showMessageDialog(null, "O nome do agente não pode estar vazio!");
            throw new Exception("Nome do agente inválido!");
        }
        if(agente.getTelefone() == null || agente.getTelefone().trim().isEmpty()){
            showMessageDialog(null, "O telefone do agente não pode estar vazio!");
        }
        if(agente.getCpf() == null || agente.getCpf().trim().isEmpty()){
            showMessageDialog(null, "O cpf do agente não pode estar vazio!");
        }
        if(agente.getEspecialidade() == null || agente.getEspecialidade().trim().isEmpty()){
            showMessageDialog(null, "A especialidade do agente não pode estar vazio!");
        }

        if(!isStringValida(agente.getNome())){
            showMessageDialog(null,
                    "O nome do agente é inválido! Deve conter apenas letras e espaços.");
            throw new Exception("Nome do agente inválido.");
        }
    }

    private boolean isStringValida(String str) {
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, str);
    }
}
