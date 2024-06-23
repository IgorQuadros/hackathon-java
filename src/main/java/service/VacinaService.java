package service;

import dao.VacinaDao;
import model.Vacinas;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.*;

public class VacinaService {
    public void salvar(Vacinas vacina) {
        try {
            validarCamposObrigatorios(vacina);
            var dao = new VacinaDao();

            if (vacina.getId() == null) {
                dao.salvar(vacina);
            } else {
                dao.atualizar(vacina);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException {
        try {
            var dao = new VacinaDao();
            dao.deletar(id);
        } catch (SQLException e) {
            System.out.println("Erro! Não foi possível deletar a vacina: " + e.getMessage());
        }
    }

    public List<Vacinas> listarTodos() {
        try {
            var dao = new VacinaDao();
            return dao.listarTodos();
        } catch (SQLException e) {
            System.out.println("Erro ao listar vacinas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private void validarCamposObrigatorios(Vacinas vacina) throws Exception {
        if (vacina.getNome() == null || vacina.getNome().trim().isEmpty()) {
            showMessageDialog(null, "O campo Nome da vacina é obrigatório.", "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Nome da vacina é obrigatório.");
        } else if (!isStringValida(vacina.getNome())) {
            showMessageDialog(null, "São permitidos somente caracteres e espaços no campo nome.", "Erro de validação", ERROR_MESSAGE);
            throw new Exception("São permitidos somente caracteres e espaços no campo nome.");
        }
        if (vacina.getDataValidade() == null) {
            showMessageDialog(null, "O campo Data de Validade da vacina é obrigatório.", "Erro de validação", ERROR_MESSAGE);
            throw new Exception("O campo Data de Validade da vacina é obrigatório.");
        }
    }

    private boolean isStringValida(String str) {
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, str);
    }
}
