/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author SEBASTIAN ROJAS
 */
public class Buscaminas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tablero prueba = new Tablero(9,9,10);
        prueba.imprimirTablero();
        while(!prueba.isFinalizado()){
            String[] opciones = {"descubrir", "bandera"};
            int opcion = JOptionPane.showOptionDialog(null, "ingrese una opcion:", "Hacer jugada", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opciones, opciones[0]);
            int i = Integer.parseInt(JOptionPane.showInputDialog("ingrese una fila"));
            int j = Integer.parseInt(JOptionPane.showInputDialog("ingrese una columna"));
            if(opcion == 0){
                prueba.descubrirCasilla(i, j);
            }else{
                prueba.ponerBandera(i, j);
            }
            prueba.imprimirTablero();
        }
        
        
        
        
    }
    
}
