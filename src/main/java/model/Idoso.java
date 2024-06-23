package model;

import java.util.List;

public class Idoso {
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private Cuidador cuidador;

    public Idoso(String nome, String telefone, String email, Cuidador cuidador) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cuidador = cuidador;
    }

    public Idoso(Integer id, String nome, String telefone, String email, Cuidador cuidador) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cuidador = cuidador;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    @Override
    public String toString() {
        return nome;
    }
}
