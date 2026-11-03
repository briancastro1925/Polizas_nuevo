package Polizas.General;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

public class Bienvenida extends JFrame {
    protected JMenuBar mb;
    protected JMenu mA, mBD;
    protected JMenuItem home;
    protected JMenuItem mensaje, marcas;
    protected CardLayout cl;
    protected JPanel pi, ps, pd, pc;

    public Bienvenida() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBackground(Color.yellow);
        this.menu();
        
        pi = new JPanel();
        pi.setBackground(Color.white);
        pi.add(new JLabel("Panel izquierdo"));
        pi.setPreferredSize(new Dimension(200, getHeight()));
        
        ps = new JPanel();
        ps.setBackground(new Color(20, 110, 55));
        JLabel titulo = new JLabel("Sistema de Gestión");
        titulo.setForeground(Color.white);
        Font fuente = new Font("Arial", Font.BOLD, 18);
        titulo.setFont(fuente);
        ps.add(titulo);

        cl = new CardLayout();
        pc = new JPanel(cl);
        pc.add(new JLabel("Welcome to Java ..."), "home");
        pc.add(new Mensaje(), "mensaje");

        pd = new JPanel();
        pd.setBackground(Color.white);
        pd.add(new JLabel("Panel derecho "));
        pd.setPreferredSize(new Dimension(200, getHeight()));

        this.setLayout(new BorderLayout());
        this.add(pi, BorderLayout.WEST);
        this.add(pd, BorderLayout.EAST);
        this.add(ps, BorderLayout.NORTH);
        this.add(pc, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void menu() {
        this.mb = new JMenuBar();
        this.setJMenuBar(mb);
        this.mA = new JMenu("Archivo");
        home = new JMenuItem("Home");
        this.mA.add(home);
        this.mensaje = new JMenuItem("Mensaje");
        this.mA.add(mensaje);

        this.mBD = new JMenu("Base de Datos");
        marcas = new JMenuItem("Marcas");
        this.mBD.add(marcas);

        this.mb.add(mA);
        this.mb.add(mBD);
    }
}
