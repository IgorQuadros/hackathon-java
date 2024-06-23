package service;

import dao.CuidadorDao;
import model.Cuidador;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static javax.swing.JOptionPane.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class CuidadorService {
    public void salvar(Cuidador cuidador){
        try{
            validarCamposObrigatorios(cuidador);
            var dao = new CuidadorDao();

            if(cuidador.getId() == null){
                dao.inserir(cuidador);
            }else{
                dao.atualizar(cuidador);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void excluir(int id) throws SQLException{
        try{
            var dao = new CuidadorDao();
            dao.deletar(id);
        }catch(SQLException e){
            System.out.println("Erro! Não foi possível deletar o cuidador: " + e.getMessage());
        }
    }

    public List<Cuidador> listarCuidadores(){
        try {
            var dao = new CuidadorDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void validarCamposObrigatorios(Cuidador cuidador) throws Exception {
        StringBuilder erros = new StringBuilder();

        if (cuidador.getNome() == null || cuidador.getNome().trim().isEmpty()) {
            erros.append("O nome do cuidador não pode estar vazio!\n");
        } else if (!isStringValida(cuidador.getNome())) {
            erros.append("O nome do cuidador é inválido! Deve conter apenas letras e espaços.\n");
        }

        if (cuidador.getTelefone() == null || cuidador.getTelefone().trim().isEmpty()) {
            erros.append("O telefone do cuidador não pode estar vazio!\n");
        } else if (!isTelefoneValido(cuidador.getTelefone())) {
            erros.append("O telefone do cuidador é inválido! Deve conter apenas números.\n");
        }

        if (cuidador.getCpf() == null || cuidador.getCpf().trim().isEmpty()) {
            erros.append("O CPF do cuidador não pode estar vazio!\n");
        } else if (!isCpfValido(cuidador.getCpf())) {
            erros.append("O CPF do cuidador é inválido! Deve conter apenas números.\n");
        }

        if (cuidador.getDataRegistro() == null) {
            erros.append("A data de registro do cuidador não pode estar vazia ou em formato inválido!\n");
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

    private boolean isTelefoneValido(String telefone) {
        String regex = "^\\d+$";
        return Pattern.matches(regex, telefone);
    }

    private boolean isCpfValido(String cpf) {
        String regex = "^\\d+$";
        return Pattern.matches(regex, cpf);
    }
}
