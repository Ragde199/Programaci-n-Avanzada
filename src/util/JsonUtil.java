package util;

import org.json.JSONArray;
import org.json.JSONObject;
import model.Materia;
import model.Alumno;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {
    private static final String FILE_PATH = "materias.json";

    public static void guardarMaterias(List<Materia> materias) {
        try {
            JSONArray jsonArray = new JSONArray();            for (Materia materia : materias) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("codigo", materia.getCodigo());
                jsonObject.put("nombre", materia.getNombre());
                jsonObject.put("creditos", materia.getCreditos());
                jsonObject.put("profesor", materia.getProfesor());
                
                // Save students
                JSONArray alumnosArray = new JSONArray();
                for (Alumno alumno : materia.getAlumnos()) {
                    JSONObject alumnoObj = new JSONObject();
                    alumnoObj.put("matricula", alumno.getMatricula());
                    alumnoObj.put("nombre", alumno.getNombre());
                    alumnoObj.put("apellido", alumno.getApellido());
                    alumnosArray.put(alumnoObj);
                }
                jsonObject.put("alumnos", alumnosArray);
                
                jsonArray.put(jsonObject);
            }
            Files.write(Paths.get(FILE_PATH), jsonArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Materia> cargarMaterias() {
        List<Materia> materias = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Materia materia = new Materia(
                        jsonObject.getString("codigo"),
                        jsonObject.getString("nombre"),
                        jsonObject.getInt("creditos"),
                        jsonObject.getString("profesor")
                    );
                    
                    // Load students
                    if (jsonObject.has("alumnos")) {
                        JSONArray alumnosArray = jsonObject.getJSONArray("alumnos");
                        for (int j = 0; j < alumnosArray.length(); j++) {
                            JSONObject alumnoObj = alumnosArray.getJSONObject(j);
                            Alumno alumno = new Alumno(
                                alumnoObj.getString("matricula"),
                                alumnoObj.getString("nombre"),
                                alumnoObj.getString("apellido")
                            );
                            materia.addAlumno(alumno);
                        }
                    }
                    
                    materias.add(materia);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return materias;
    }
}
