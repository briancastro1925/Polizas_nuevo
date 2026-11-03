package Polizas;

import Polizas.General.Controlador;
import Polizas.General.Bienvenida;
import javax.swing.SwingUtilities;

public class Inicio {
    private Controlador controlador;
    private Bienvenida vista;

    public Inicio() {
        vista = new Bienvenida();
        controlador = new Controlador(vista);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Inicio::new);
    }
}
