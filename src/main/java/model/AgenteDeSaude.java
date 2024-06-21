package model;

import java.sql.Date;

public class AgenteDeSaude {
    private Integer id;
    private String nome;
    private String telefone;
    private String cpf;
    private String especialidade;
    private Date dataContratacao;

    public AgenteDeSaude(String nome, String telefone, String cpf,
                         String especialidade, Date dataContratacao) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.dataContratacao = dataContratacao;
    }

    public AgenteDeSaude(Integer id, String nome,
                         String telefone, String cpf, String especialidade, Date dataContratacao) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.especialidade = especialidade;
        this.dataContratacao = dataContratacao;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public java.sql.Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    @Override
    public String toString() {
        return nome;
    }
}
