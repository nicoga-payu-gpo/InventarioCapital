/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import aplicacion.InformacionPC;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ngarcia
 */
public class InventarioGUI extends JFrame {

    private static Container contenedor;
    private static CardLayout card = new CardLayout();
    private InformacionPC infoPC;
    private MenuInventario menuInventario;

    public static void main(String[] args) throws IOException {
        new InventarioGUI();
        InformacionPC a=new InformacionPC();
        a.buscarInformacion(1, true);

    }

    public InventarioGUI() {
        prepareElementos();
        prepareAcciones();
        this.setVisible(true);
    }

    private void prepareElementos() {
        infoPC = new InformacionPC();
        setTitle("Inventario Computadores Constructora Capital");
        contenedor = getContentPane();
        menuInventario = new MenuInventario();
        card.addLayoutComponent(menuInventario, "menuInventario");
        contenedor.add(menuInventario);
        contenedor.setLayout(card);
        card.show(contenedor, "menuInventario");
        setSize(790, 490);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getSize().width) / 2, (screen.height - getSize().height) / 2);

    }

    private void prepareAcciones() {
        setFocusable(true);
        addWindowListener(
                new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                salga();

            }
        }
        );
    }

    private void salga() {
        int siNo = JOptionPane.showConfirmDialog(null, "Realmente desea salir del programa?");
        if (siNo == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
