package model;

public class Alumno {
    private String matricula;
    private String nombre;
    private String apellido;

    public Alumno(String matricula, String nombre, String apellido) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters
    public String getMatricula() { return matricula; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }

    // Setters
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    @Override
    public String toString() {
        return matricula + " - " + nombre + " " + apellido;
    }
}
