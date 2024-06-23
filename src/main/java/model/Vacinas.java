package model;

import java.util.Date;

public class Vacinas {
    private Integer id;
    private String nome;
    private Date data_validade;

    public Vacinas(String nome, Date data_validade) {
        this.nome = nome;
        this.data_validade = data_validade;
    }

    public Vacinas(Integer id, String nome, Date data_validade) {
        this.id = id;
        this.nome = nome;
        this.data_validade = data_validade;
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

    public Date getData_validade() {
        return data_validade;
    }

    public void setData_validade(Date data_validade) {
        this.data_validade = data_validade;
    }

    @Override
    public String toString() {
        return nome;
    }
}
