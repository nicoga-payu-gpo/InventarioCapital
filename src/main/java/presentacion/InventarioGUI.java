/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import aplicacion.ExcepcionInventario;
import aplicacion.InformacionPC;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private MenuInventario menuInventarioOficina;

    public static void main(String[] args) throws IOException {
        new InventarioGUI();
        /*try {
            a.buscarInformacion(1, true);
        } catch (ExcepcionInventario ex) {
            Logger.getLogger(InventarioGUI.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

    public InventarioGUI() {
        prepareElementos();
        prepareAcciones();
        this.setVisible(true);
    }

    private void prepareElementos() {
        try {
            infoPC = new InformacionPC(0);
        } catch (ExcepcionInventario ex) {
            new JOptionPane(ex.getMessage()).setVisible(true);
            System.out.println(ex.getMessage());
        }
        setTitle("Inventario Computadores Constructora Capital");
        contenedor = getContentPane();
        menuInventarioOficina = new MenuInventario(this);
        card.addLayoutComponent(menuInventarioOficina, "menuInventario");
        contenedor.add(menuInventarioOficina);
        contenedor.setLayout(card);
        card.show(contenedor, "menuInventario");
        setSize(925, 680);
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

    public InformacionPC getInfoPC() {
        return infoPC;
    }
}
