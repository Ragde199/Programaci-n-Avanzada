# Sistema de Gestión de Materias

Este es un sistema de gestión académica desarrollado en Java que permite administrar materias y sus estudiantes. La aplicación proporciona una interfaz gráfica intuitiva para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) tanto en materias como en la lista de estudiantes de cada materia.

## Características

- Gestión completa de materias (agregar, editar, eliminar)
- Gestión de estudiantes por materia
- Interfaz gráfica moderna con tablas organizadas
- Almacenamiento persistente en archivos JSON
- Validación de datos de entrada
- Interfaz de usuario intuitiva y responsiva

## Tecnologías Utilizadas

- Java Swing para la interfaz gráfica
- JSON para almacenamiento de datos
- Arquitectura MVC (Modelo-Vista-Controlador)

## Requisitos del Sistema

- Java Development Kit (JDK) 8 o superior
- IDE compatible con Java (Visual Studio Code, Eclipse, IntelliJ IDEA, etc.)

## Estructura del Proyecto

```
src/
├── controller/
│   └── MateriaController.java
├── model/
│   ├── Materia.java
│   └── Alumno.java
├── view/
│   ├── MainView.java
│   ├── MateriaForm.java
│   └── AlumnosForm.java
├── util/
│   └── JsonUtil.java
└── App.java
```

## Instalación y Uso

1. Clona el repositorio:
   ```bash
   git clone [URL_DEL_REPOSITORIO]
   ```

2. Abre el proyecto en tu IDE favorito

3. Ejecuta el archivo `App.java` para iniciar la aplicación

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue primero para discutir los cambios que te gustaría realizar.
