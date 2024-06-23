package model;

public class Historico {
    private Integer id;
    private Integer idoso_id;
    private String doencas_preexistentes;

    public Historico(Integer idoso_id, String doencas_preexistentes) {
        this.idoso_id = idoso_id;
        this.doencas_preexistentes = doencas_preexistentes;
    }

    public Historico(Integer id, Integer idoso_id, String doencas_preexistentes) {
        this.id = id;
        this.idoso_id = idoso_id;
        this.doencas_preexistentes = doencas_preexistentes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdoso_id() {
        return idoso_id;
    }

    public void setIdoso_id(Integer idoso_id) {
        this.idoso_id = idoso_id;
    }

    public String getDoencas_preexistentes() {
        return doencas_preexistentes;
    }

    public void setDoencas_preexistentes(String doencas_preexistentes) {
        this.doencas_preexistentes = doencas_preexistentes;
    }
}
