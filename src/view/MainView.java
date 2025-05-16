package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.MateriaController;
import model.Materia;

public class MainView extends JFrame {
    private MateriaController controller;
    private JTable materiasTable;
    private DefaultTableModel tableModel;
    private JButton btnAgregar, btnEditar, btnEliminar;

    public MainView() {
        controller = new MateriaController();
        setupUI();
        cargarMaterias();
    }

    private void setupUI() {
        setTitle("Gestión de Materias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Sistema de Gestión de Materias");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Tabla de materias
        String[] columnNames = {"Código", "Nombre", "Créditos", "Profesor", "Alumnos"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        materiasTable = new JTable(tableModel);
        materiasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        materiasTable.setFont(new Font("Dialog", Font.PLAIN, 14));
        materiasTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 14));
        materiasTable.setRowHeight(25);
        
        // Configurar anchos de columna
        materiasTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Código
        materiasTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        materiasTable.getColumnModel().getColumn(2).setPreferredWidth(80);  // Créditos
        materiasTable.getColumnModel().getColumn(3).setPreferredWidth(200); // Profesor
        materiasTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Alumnos
        
        JScrollPane scrollPane = new JScrollPane(materiasTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        JButton btnGestionarAlumnos = new JButton("Gestionar Alumnos");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnGestionarAlumnos);        btnGestionarAlumnos.addActionListener(_ -> {
            int selectedRow = materiasTable.getSelectedRow();
            if (selectedRow != -1) {
                Materia materia = controller.getMateria(selectedRow);
                AlumnosForm alumnosForm = new AlumnosForm(this, materia, controller);
                alumnosForm.setVisible(true);
                cargarMaterias(); // Actualizar la tabla para mostrar el número de alumnos
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una materia");
            }
        });

        // Agregar componentes al panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar panel principal al frame
        add(mainPanel);

        // Eventos
        btnAgregar.addActionListener(_ -> mostrarFormulario(null));        btnEditar.addActionListener(_ -> {
            int selectedRow = materiasTable.getSelectedRow();
            if (selectedRow != -1) {
                mostrarFormulario(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una materia para editar");
            }
        });        btnEliminar.addActionListener(_ -> {
            int selectedRow = materiasTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar esta materia?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.eliminarMateria(selectedRow);
                    cargarMaterias();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione una materia para eliminar");
            }
        });
    }

    private void mostrarFormulario(Integer index) {
        MateriaForm form = new MateriaForm(this, controller, index);
        form.setVisible(true);
    }

    public void cargarMaterias() {
        tableModel.setRowCount(0);
        for (Materia materia : controller.getMaterias()) {
            Object[] rowData = {
                materia.getCodigo(),
                materia.getNombre(),
                materia.getCreditos(),
                materia.getProfesor(),
                materia.getAlumnos().size() + " alumnos"
            };
            tableModel.addRow(rowData);
        }
    }
}
