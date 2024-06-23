package model;

public class Historico {
    private Integer id;
    private Integer idoso_id;
    private Integer vacina_id;
    private String doencas_preexistentes;

    public Historico(Integer idoso_id, Integer vacina_id, String doencas_preexistentes) {
        this.idoso_id = idoso_id;
        this.vacina_id = vacina_id;
        this.doencas_preexistentes = doencas_preexistentes;
    }

    public Historico(Integer id, Integer idoso_id, Integer vacina_id, String doencas_preexistentes) {
        this.id = id;
        this.idoso_id = idoso_id;
        this.vacina_id = vacina_id;
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

    public Integer getVacina_id() {
        return vacina_id;
    }

    public void setVacina_id(Integer vacina_id) {
        this.vacina_id = vacina_id;
    }

    public String getDoencas_preexistentes() {
        return doencas_preexistentes;
    }

    public void setDoencas_preexistentes(String doencas_preexistentes) {
        this.doencas_preexistentes = doencas_preexistentes;
    }

    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", idoso_id=" + idoso_id +
                ", vacina_id=" + vacina_id +
                ", doencas_preexistentes='" + doencas_preexistentes + '\'' +
                '}';
    }
}
