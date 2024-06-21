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

    public void validarCamposObrigatorios(Cuidador cuidador) throws Exception{
        if(cuidador.getNome() == null || cuidador.getNome().trim().isEmpty()){
            showMessageDialog(null, "O nome do cuidador não pode estar vazio!");
            throw new Exception("Nome inválido!");
        }
        else if(cuidador.getTelefone() == null || cuidador.getTelefone().trim().isEmpty()){
            showMessageDialog(null, "O telefone do cuidador não pode estar vazio!");
            throw new Exception("Telefone inválido!");
        }
        else if(cuidador.getCpf() == null || cuidador.getCpf().trim().isEmpty()){
            showMessageDialog(null, "O cpf do cuidador não pode estar vazio!");
            throw new Exception("CPF inválido!");
        }
        else if(cuidador.getDataRegistro() == null){
            showMessageDialog(null,
                    "A data de registro do cuidador não pode estar vazia ou em formato inválido!");
            throw new Exception("Data de registro inválida!");
        }

        if(!isStringValida(cuidador.getNome())){
            showMessageDialog(null,
                    "O nome do cuidador é inválido! Deve conter apenas letras e espaços.");
            throw new Exception("Nome inválido.");
        }
        if (!isTelefoneValido(cuidador.getTelefone())) {
            showMessageDialog(null,
                    "O telefone do cuidador é inválido! Deve conter apenas números.");
            throw new Exception("Telefone inválido.");
        }

        if (!isCpfValido(cuidador.getCpf())) {
            showMessageDialog(null,
                    "O CPF do cuidador é inválido! Deve conter apenas números.");
            throw new Exception("CPF inválido.");
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
