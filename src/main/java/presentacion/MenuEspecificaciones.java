/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ngarcia
 */
public class MenuEspecificaciones extends JPanel {
    private JFrame inventarioGUI;
    private JPanel panelInformacion;
    private JTextField personaAsignada;
    private JTextField placaCPU ;
    private JTextField placaPantalla;

    
    

    public MenuEspecificaciones(JFrame GUI) {
        
        prepareElementos(GUI);
        prepareAcciones();
    }
    
    private void prepareElementos(JFrame GUI){
        inventarioGUI=GUI;
        setLayout(new FlowLayout(FlowLayout.CENTER,100,150));
    }
    
    private void prepareAcciones(){
    
    }
}
