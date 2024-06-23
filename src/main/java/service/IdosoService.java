package service;

import dao.CuidadorDao;
import dao.IdosoDao;
import model.Cuidador;
import model.Idoso;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class IdosoService {
    private IdosoDao idosoDao;

    public IdosoService() {
        try {
            idosoDao = new IdosoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Idoso> listarIdosos(){
        try {
            var dao = new IdosoDao();
            return dao.listarTodos();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Idoso> listarIdososComCuidadores() {
        try {
            return idosoDao.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
