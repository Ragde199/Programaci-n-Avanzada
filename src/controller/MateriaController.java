package controller;

import model.Materia;
import util.JsonUtil;
import java.util.List;
import java.util.ArrayList;

public class MateriaController {
    private List<Materia> materias;

    public MateriaController() {
        this.materias = JsonUtil.cargarMaterias();
    }

    public void agregarMateria(String codigo, String nombre, int creditos, String profesor) {
        Materia materia = new Materia(codigo, nombre, creditos, profesor);
        materias.add(materia);
        JsonUtil.guardarMaterias(materias);
    }

    public void actualizarMateria(int index, String codigo, String nombre, int creditos, String profesor) {
        if (index >= 0 && index < materias.size()) {
            Materia materia = materias.get(index);
            materia.setCodigo(codigo);
            materia.setNombre(nombre);
            materia.setCreditos(creditos);
            materia.setProfesor(profesor);
            JsonUtil.guardarMaterias(materias);
        }
    }

    public void eliminarMateria(int index) {
        if (index >= 0 && index < materias.size()) {
            materias.remove(index);
            JsonUtil.guardarMaterias(materias);
        }
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public Materia getMateria(int index) {
        if (index >= 0 && index < materias.size()) {
            return materias.get(index);
        }
        return null;
    }

    public void actualizarMateria(Materia materia) {
        for (int i = 0; i < materias.size(); i++) {
            if (materias.get(i).getCodigo().equals(materia.getCodigo())) {
                materias.set(i, materia);
                JsonUtil.guardarMaterias(materias);
                break;
            }
        }
    }
}
