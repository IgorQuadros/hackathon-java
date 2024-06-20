package model;

import service.CuidadorService;

import java.sql.Date;

public class Cuidador {
    private Integer id;
    private String nome;
    private String telefone;
    private String cpf;
    private Date dataRegistro;

    public Cuidador(String nome, String telefone, String cpf, Date dataRegistro) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataRegistro = dataRegistro;
    }

    public Cuidador(Integer id, String nome, String telefone, String cpf, Date dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataRegistro = dataRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public String toString() {
        return nome;
    }
}
