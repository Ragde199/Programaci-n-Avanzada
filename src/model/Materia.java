package model;

import java.util.ArrayList;
import java.util.List;

public class Materia {
    private String codigo;
    private String nombre;
    private int creditos;
    private String profesor;
    private List<Alumno> alumnos;

    public Materia(String codigo, String nombre, int creditos, String profesor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.profesor = profesor;
        this.alumnos = new ArrayList<>();
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCreditos() { return creditos; }
    public String getProfesor() { return profesor; }
    public List<Alumno> getAlumnos() { return alumnos; }

    // Setters
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCreditos(int creditos) { this.creditos = creditos; }
    public void setProfesor(String profesor) { this.profesor = profesor; }

    // Methods for managing students
    public void addAlumno(Alumno alumno) {
        if (!alumnos.contains(alumno)) {
            alumnos.add(alumno);
        }
    }

    public void removeAlumno(Alumno alumno) {
        alumnos.remove(alumno);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " (Prof. " + profesor + ") [" + alumnos.size() + " alumnos]";
    }
}
