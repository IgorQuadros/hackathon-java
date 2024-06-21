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
                dao.inserir(agente);
            }else{
                dao.atualizar(agente);
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
        StringBuilder erros = new StringBuilder();

        if (agente.getNome() == null || agente.getNome().trim().isEmpty()) {
            erros.append("O nome do agente não pode estar vazio!\n");
        } else if (!isStringValida(agente.getNome())) {
            erros.append("O nome do agente é inválido! Deve conter apenas letras e espaços.\n");
        }

        if (agente.getTelefone() == null || agente.getTelefone().trim().isEmpty()) {
            erros.append("O telefone do agente não pode estar vazio!\n");
        } else if (!isTelefoneValido(agente.getTelefone())) {
            erros.append("O telefone do agente é inválido! Deve conter apenas números.\n");
        }

        if (agente.getCpf() == null || agente.getCpf().trim().isEmpty()) {
            erros.append("O CPF do agente não pode estar vazio!\n");
        } else if (!isCpfValido(agente.getCpf())) {
            erros.append("O CPF do agente é inválido! Deve conter apenas números.\n");
        }

        if (agente.getEspecialidade() == null || agente.getEspecialidade().trim().isEmpty()) {
            erros.append("A especialidade do agente não pode estar vazia!\n");
        }

        if (agente.getDataContratacao() == null) {
            erros.append("A data da contratação do agente não pode estar vazia!\n");
        }

        if (erros.length() > 0) {
            showMessageDialog(null, erros.toString());
            throw new Exception(erros.toString());
        }
    }

    private boolean isStringValida(String str) {
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, str);
    }
    private boolean isTelefoneValido(String telefone) {
        String regex = "^\\d+$";
        return Pattern.matches(regex, telefone);
    }

    private boolean isCpfValido(String cpf) {
        String regex = "^\\d+$";
        return Pattern.matches(regex, cpf);
    }
}
