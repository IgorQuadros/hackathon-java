package model;

public class Idoso {
    private Integer id;
    private String nome;
    private String agenda;
    private String vacinas;
    private String cuidador;

    public Idoso(String nomeIdoso, String agenda, String vacinas, String cuidador) {
        this.nome = nomeIdoso;
        this.agenda = agenda;
        this.vacinas = vacinas;
        this.cuidador = cuidador;
    }

    public Idoso(Integer id, String nomeIdoso, String agenda, String vacinas, String cuidador) {
        this.id = id;
        this.nome = nomeIdoso;
        this.agenda = agenda;
        this.vacinas = vacinas;
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

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getVacinas() {
        return vacinas;
    }

    public void setVacinas(String vacinas) {
        this.vacinas = vacinas;
    }

    public String getCuidador() {
        return cuidador;
    }

    public void setCuidador(String cuidador) {
        this.cuidador = cuidador;
    }

    @Override
    public String toString() {
        return nome;
    }
}
