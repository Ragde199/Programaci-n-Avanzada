package view;

import javax.swing.*;
import java.awt.*;
import controller.MateriaController;
import model.Materia;

public class MateriaForm extends JDialog {
    private JTextField txtCodigo, txtNombre, txtCreditos, txtProfesor;
    private JButton btnGuardar, btnCancelar;
    private MateriaController controller;
    private MainView mainView;
    private Integer editIndex;

    public MateriaForm(MainView parent, MateriaController controller, Integer editIndex) {
        super(parent, editIndex == null ? "Nueva Materia" : "Editar Materia", true);
        this.controller = controller;
        this.mainView = parent;
        this.editIndex = editIndex;
        setupUI();
        if (editIndex != null) {
            cargarDatosMateria(editIndex);
        }
    }    private void setupUI() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Panel principal con GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título del formulario
        JLabel titleLabel = new JLabel(editIndex == null ? "Nueva Materia" : "Editar Materia");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        // Campos de texto
        JLabel[] labels = {
            new JLabel("Código:"),
            new JLabel("Nombre:"),
            new JLabel("Créditos:"),
            new JLabel("Profesor:")
        };
          for (JLabel label : labels) {
            label.setFont(new Font("Dialog", Font.PLAIN, 14));
        }

        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(labels[0], gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(20);
        mainPanel.add(txtCodigo, gbc);        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(labels[1], gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        mainPanel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(labels[2], gbc);
        gbc.gridx = 1;
        txtCreditos = new JTextField(20);
        mainPanel.add(txtCreditos, gbc);        gbc.gridx = 0; gbc.gridy = 4;
        mainPanel.add(labels[3], gbc);
        gbc.gridx = 1;
        txtProfesor = new JTextField(20);
        mainPanel.add(txtProfesor, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(btnCancelar);        // Agregar espacio vertical antes de los botones
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);  // Aumentar el margen superior
        mainPanel.add(buttonPanel, gbc);

        // Eventos
        btnGuardar.addActionListener(e -> guardarMateria());
        btnCancelar.addActionListener(e -> dispose());

        add(mainPanel);
    }

    private void cargarDatosMateria(int index) {
        Materia materia = controller.getMateria(index);
        if (materia != null) {
            txtCodigo.setText(materia.getCodigo());
            txtNombre.setText(materia.getNombre());
            txtCreditos.setText(String.valueOf(materia.getCreditos()));
            txtProfesor.setText(materia.getProfesor());
        }
    }

    private void guardarMateria() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            int creditos = Integer.parseInt(txtCreditos.getText().trim());
            String profesor = txtProfesor.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || profesor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            if (editIndex == null) {
                controller.agregarMateria(codigo, nombre, creditos, profesor);
            } else {
                controller.actualizarMateria(editIndex, codigo, nombre, creditos, profesor);
            }

            mainView.cargarMaterias();
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El campo Créditos debe ser un número");
        }
    }
}
