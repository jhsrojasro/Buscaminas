/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

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
        int i = Integer.parseInt(JOptionPane.showInputDialog("ingrese una fila"));
        int j = Integer.parseInt(JOptionPane.showInputDialog("ingrese una columna"));
        prueba.descubrirCasilla(i, j);
        prueba.imprimirTablero();
        
        
        
    }
    
}
