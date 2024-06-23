package model;

import java.util.Date;

public class Vacinas {
    private Integer id;
    private String nome;
    private Date dataValidade;

    public Vacinas(String nome, Date dataValidade) {
        this.nome = nome;
        this.dataValidade = dataValidade;
    }

    public Vacinas(Integer id, String nome, Date dataValidade) {
        this.id = id;
        this.nome = nome;
        this.dataValidade = dataValidade;
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

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    @Override
    public String toString() {
        return "Vacinas{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataValidade=" + dataValidade +
                '}';
    }
}
