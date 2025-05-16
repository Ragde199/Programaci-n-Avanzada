package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Materia;
import model.Alumno;
import controller.MateriaController;

/**
 * Formulario para la gestión de alumnos en una materia
 */

public class AlumnosForm extends JDialog {
    private Materia materia;
    private MateriaController controller;    private DefaultTableModel tableModel;
    private JTable alumnosTable;
    private JTextField txtMatricula, txtNombre, txtApellido;
    private JButton btnAgregar, btnEliminar;

    public AlumnosForm(JFrame parent, Materia materia, MateriaController controller) {
        super(parent, "Gestionar Alumnos - " + materia.getNombre(), true);
        this.materia = materia;
        this.controller = controller;
        setupUI();
        cargarAlumnos();
    }    private void setupUI() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Título
        JLabel titleLabel = new JLabel("Gestión de Alumnos - " + materia.getNombre());
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // Campos de texto con etiquetas
        String[] labelTexts = {"Matrícula:", "Nombre:", "Apellido:"};
        JLabel[] labels = new JLabel[labelTexts.length];
        
        for (int i = 0; i < labelTexts.length; i++) {
            labels[i] = new JLabel(labelTexts[i]);
            labels[i].setFont(new Font("Dialog", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            formPanel.add(labels[i], gbc);
        }

        // Campos de texto
        gbc.gridx = 1; gbc.gridy = 1;
        txtMatricula = new JTextField(20);
        formPanel.add(txtMatricula, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        txtNombre = new JTextField(20);
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        txtApellido = new JTextField(20);
        formPanel.add(txtApellido, gbc);

        // Botón agregar
        gbc.gridx = 1; gbc.gridy = 4;
        btnAgregar = new JButton("Agregar Alumno");
        formPanel.add(btnAgregar, gbc);        // Tabla de alumnos
        JPanel listPanel = new JPanel(new BorderLayout(5, 5));
        JLabel listTitle = new JLabel("Alumnos Inscritos");
        listTitle.setFont(new Font("Dialog", Font.BOLD, 14));
        listPanel.add(listTitle, BorderLayout.NORTH);

        // Crear modelo de tabla con columnas
        String[] columnNames = {"Matrícula", "Nombre", "Apellido"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        alumnosTable = new JTable(tableModel);
        alumnosTable.setFont(new Font("Dialog", Font.PLAIN, 14));
        alumnosTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
        alumnosTable.setRowHeight(25);
        alumnosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar anchos de columna
        alumnosTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        alumnosTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        alumnosTable.getColumnModel().getColumn(2).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(alumnosTable);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnEliminar = new JButton("Eliminar Alumno");
        JButton btnCerrar = new JButton("Cerrar");
        
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnCerrar);

        // Eventos
        btnAgregar.addActionListener(e -> agregarAlumno());
        btnEliminar.addActionListener(e -> eliminarAlumno());
        btnCerrar.addActionListener(e -> dispose());

        // Layout final
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }    private void cargarAlumnos() {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (Alumno alumno : materia.getAlumnos()) {
            Object[] row = {
                alumno.getMatricula(),
                alumno.getNombre(),
                alumno.getApellido()
            };
            tableModel.addRow(row);
        }
    }

    private void agregarAlumno() {
        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();

        if (matricula.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        Alumno alumno = new Alumno(matricula, nombre, apellido);
        materia.addAlumno(alumno);
        controller.actualizarMateria(materia);
        cargarAlumnos();
        limpiarCampos();
    }    private void eliminarAlumno() {
        int selectedRow = alumnosTable.getSelectedRow();
        if (selectedRow != -1) {
            String matricula = (String) alumnosTable.getValueAt(selectedRow, 0);
            String nombre = (String) alumnosTable.getValueAt(selectedRow, 1);
            String apellido = (String) alumnosTable.getValueAt(selectedRow, 2);

            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar al alumno " + nombre + " " + apellido + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Alumno alumnoAEliminar = null;
                for (Alumno alumno : materia.getAlumnos()) {
                    if (alumno.getMatricula().equals(matricula)) {
                        alumnoAEliminar = alumno;
                        break;
                    }
                }
                if (alumnoAEliminar != null) {
                    materia.removeAlumno(alumnoAEliminar);
                    controller.actualizarMateria(materia);
                    cargarAlumnos();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un alumno para eliminar");
        }
    }

    private void limpiarCampos() {
        txtMatricula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtMatricula.requestFocus();
    }
}
