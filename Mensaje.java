package Polizas.General;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Mensaje extends JPanel {
    
    public Mensaje() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 30));
        add(
            new JLabel(
                    "Actualización mensajes actualizados...",
                    SwingConstants.CENTER)
        );
    }
}
