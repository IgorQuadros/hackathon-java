package model;

import java.sql.Date;
import java.sql.Time;

public class Agenda {
    private Integer id;
    private Integer idosoId;
    private Integer agenteDeSaudeId;
    private Date data;
    private Time hora;
    private String descricao;

    public Agenda(){
        this.id = id;
        this.idosoId = idosoId;
        this.agenteDeSaudeId = agenteDeSaudeId;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
    }

    public Agenda(Integer idosoId, Integer agenteDeSaudeId, Date data, Time hora, String descricao) {
        this.idosoId = idosoId;
        this.agenteDeSaudeId = agenteDeSaudeId;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
    }

    public Agenda(Integer id, Integer idosoId, Integer agenteDeSaudeId, Date data, Time hora, String descricao) {
        this.id = id;
        this.idosoId = idosoId;
        this.agenteDeSaudeId = agenteDeSaudeId;
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdosoId() {
        return idosoId;
    }

    public void setIdosoId(Integer idosoId) {
        this.idosoId = idosoId;
    }

    public Integer getAgenteDeSaudeId() {
        return agenteDeSaudeId;
    }

    public void setAgenteDeSaudeId(Integer agenteDeSaudeId) {
        this.agenteDeSaudeId = agenteDeSaudeId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}