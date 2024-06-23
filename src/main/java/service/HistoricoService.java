package service;

import dao.CuidadorDao;
import dao.HistoricoDao;
import model.Cuidador;
import model.Historico;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.showMessageDialog;

public class HistoricoService {
    public void salvar(Historico historico) throws SQLException{
        try{
            validarCamposObrigatorios(historico);
            var dao = new HistoricoDao();

            if(historico.getId() == null){
                dao.inserir(historico);
            }else{
                dao.atualizar(historico);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        try{
            var dao = new HistoricoDao();
            dao.deletar(id);
        }catch(SQLException e){
            System.out.println("Erro! Não foi possível deletar o historico do idoso: " + e.getMessage());
        }
    }

    public List<Historico> listarHistoricos(){
        try {
            var dao = new HistoricoDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void validarCamposObrigatorios(Historico historico) throws Exception {
        StringBuilder erros = new StringBuilder();

        if (historico.getIdoso_id() == null) {
            erros.append("O ID do idoso é obrigatório!\n");
        }

        if (historico.getDoencas_preexistentes() == null || historico.getDoencas_preexistentes().trim().isEmpty()) {
            erros.append("As doenças preexistentes não podem estar vazias!\n");
        } else if (!isStringValida(historico.getDoencas_preexistentes())) {
            erros.append("As doenças preexistentes são inválidas! Devem conter apenas letras e espaços.\n");
        }

        if (erros.length() > 0) {
            showMessageDialog(null, erros.toString(),
                    "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            throw new Exception(erros.toString());
        }
    }

    private boolean isStringValida(String str) {
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, str);
    }
}
