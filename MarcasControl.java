package Polizas.Marcas;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MarcasControl {
    private MarcasVista vista;
    private MarcasModelo modelo;

    public MarcasControl(MarcasVista vista, MarcasModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
        initController();
        cargarTabla("");
    }

    private void initController() {
        this.vista.btnConsultar.addActionListener(e -> ejecutarConsultar());
        this.vista.btnAgregar.addActionListener(e -> ejecutarAgregar());
        this.vista.btnActualizar.addActionListener(e -> ejecutarActualizacion());
        this.vista.btnEliminar.addActionListener(e -> ejecutarEliminacion());

     
        this.vista.tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = vista.tabla.getSelectedRow();
                if (fila >= 0) {
                    vista.setTextoId(vista.tabla.getValueAt(fila, 0).toString());
                    vista.setTextoNombre(vista.tabla.getValueAt(fila, 1).toString());
                }
            }
        });
    }

    private void cargarTabla(String filtro) {
        DefaultTableModel model = (DefaultTableModel) vista.tabla.getModel();
        model.setRowCount(0);
        List<Marca> lista = modelo.read(filtro);
        for (Marca m : lista) {
            model.addRow(new Object[]{m.getId(), m.getNombre()});
        }
    }

    private void ejecutarConsultar() {
        String texto = vista.getTextoNombre();
        cargarTabla(texto);
    }

    private void ejecutarAgregar() {
        String texto = vista.getTextoNombre();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El campo de nombre no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modelo.existeMarca(texto)) {
            JOptionPane.showMessageDialog(vista, "La marca ya existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Marca nuevaMarca = new Marca(0, texto); 
        if (modelo.create(nuevaMarca) > 0) {
            JOptionPane.showMessageDialog(vista, "Marca agregada exitosamente.");
            vista.setTextoId("");
            vista.setTextoNombre("");
            cargarTabla("");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al agregar la marca.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarActualizacion() {
        String idStr = vista.getTextoId();
        String nuevoNombre = vista.getTextoNombre();

        if (idStr.isEmpty() || nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debes especificar tanto el ID como el nuevo nombre en los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            Marca marcaActualizada = new Marca(id, nuevoNombre);

            if (modelo.update(marcaActualizada) > 0) {
                JOptionPane.showMessageDialog(vista, "Registro actualizado correctamente.");
                vista.setTextoId("");
                vista.setTextoNombre("");
                cargarTabla("");
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún registro con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El ID debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarEliminacion() {
        String idStr = vista.getTextoId();
        int id = -1;

        if (!idStr.isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                id = -1;
            }
        } else {
            int fila = vista.tabla.getSelectedRow();
            if (fila >= 0) {
                id = Integer.parseInt(vista.tabla.getValueAt(fila, 0).toString());
            }
        }

        if (id == -1) {
            JOptionPane.showMessageDialog(vista, "Ingresa el ID en el campo correspondiente o selecciona una fila de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            Marca marcaEliminar = new Marca(id, "");
            if (modelo.delete(marcaEliminar) > 0) {
                JOptionPane.showMessageDialog(vista, "Registro eliminado correctamente.");
                vista.setTextoId("");
                vista.setTextoNombre("");
                cargarTabla("");
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar el registro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
