package service;

import dao.AgendaDao;
import model.Agenda;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class AgendaService {
    public void salvar(Agenda agenda){
        try{
            validarCamposObrigatorios(agenda);
            var dao = new AgendaDao();

            if(agenda.getId() == null){
                dao.salvar(agenda);
            }else{
                dao.atualizar(agenda);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        try{
            var dao = new AgendaDao();
            dao.deletar(id);
        }catch(SQLException e){
            System.out.println("Erro! Não foi possível deletar o agendamento: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Agenda> listarAgendamentos(){
        try {
            var dao = new AgendaDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void validarCamposObrigatorios( Agenda agenda) throws Exception{
        if (agenda.getIdosoId() == null) {
            throw new Exception("O campo Idoso é obrigatório.");
        }
        if (agenda.getAgenteDeSaudeId() == null) {
            showMessageDialog(null,"O campo Agente de Saúde é obrigatório.",
                    "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Agente de Saúde é obrigatório.");
        }
        if (agenda.getData() == null) {
            showMessageDialog(null,"O campo Data é obrigatório.",
                    "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Data é obrigatório.");
        }
        if (agenda.getHora() == null) {
            showMessageDialog(null,"O campo Hora é obrigatório.",
                    "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Hora é obrigatório.");
        }
        if (agenda.getDescricao() == null || agenda.getDescricao().trim().isEmpty()) {
        showMessageDialog(null,"O campo Descrição é obrigatório.",
                "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Descrição é obrigatório.");
        }
    }

}
