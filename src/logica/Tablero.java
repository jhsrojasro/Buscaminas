/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.security.SecureRandom;
import javax.swing.JOptionPane;

/**
 *
 * @author SEBASTIAN ROJAS
 */
public class Tablero {
    //Atributos
    
    /**
     * 
     */
    private int filas;
    /**
     * 
     */
    private int columnas;
    /**
     * 
     */
    private int minas;
    /**
     * 
     */
    private int minasRestantes;
    
    private boolean finalizado;
    
    //Asociacion de clase
    private Casilla[][] casillas;
    
    //Métodos

    /**
     * 
     * @param filas
     * @param columnas
     * @param minas 
     */
    public Tablero(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
        this.finalizado = false;
        int minasPuestas = 0;
        this.casillas = new Casilla[filas][columnas];
        
        //inicializamos las casillas en ninguna hay mina
        for(int i=0; i<filas; i++){
            for(int j=0; j<columnas; j++){
                    this.casillas[i][j] = new Casilla(false);     
            }
        }
        
        //ponemos las minas de manera aleatoria
        for (int i = 0; i < minas; i++) {
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(new byte[1]);
            int f = sr.nextInt(filas);
            int c = sr.nextInt(columnas);
            if(!casillas[f][c].isMina()){
                casillas[f][c].setMina(true);
            }else{
                i--;
            }
        }
        
        //calculamos el numero de minas vecinas en cada casilla
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j].setVecinos(vecinos(i,j));
            }
        }
        
        
        
        
    }
    /**
     * Funcion que retorna el numero de minas alrededor de la casilla i,j
     * @param i
     * @param j
     * @return 
     */
    public int vecinos(int i, int j){
        int vecinos = 0;
        //si la casilla es una esquina del tablero
        if((i==0||i==filas-1) && (j==0||j==columnas-1)){
            int df = (i==0)?1 :-1 ,dc = (j==0)?1:-1;
            if(casillas[i][j+dc].isMina())
                vecinos++;
            if(casillas[i+df][j].isMina())
                vecinos++;
            if(casillas[i+df][j+dc].isMina())
                vecinos++;
            return vecinos;
        }
        //si la casilla esta en la primera o ultima columna del tablero
        if(j==0||j==columnas-1){
            int dc = (j==0)?1:-1;
            for(int k=-1; k<2; k++){
                if(casillas[i+k][j].isMina())
                    vecinos++;
                if(casillas[i+k][j+dc].isMina())
                    vecinos++;
            }
            return vecinos;
        }
        //si la casilla esta en la primera o ultima fila del tablero
        if(i==0||i==filas-1){
            int df = (i==0)? 1: -1;
            for(int k=-1; k<2; k++){
                if(casillas[i][j+k].isMina())
                    vecinos++;
                if(casillas[i+df][j+k].isMina())
                    vecinos++;
            }
            return vecinos;
        }
        //en otro caso
        for (int k = -1; k < 2; k++) {
            for (int s = -1; s < 2; s++) {
                if(casillas[i+k][j+s].isMina())
                    vecinos++;
            }
        }
        return vecinos;
        
    }
    
    //funcion para imprimir la matriz de casillas en el tablero () si la casilla esta oculta,
    //-1 si es mina, [] si esta marcada como mina, {}si esta marcada como interrogante..
    public void imprimirTablero(){
        System.out.println();
        System.out.println();
        //imprimimos prueba por consola
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++){ 
                String casilla = Integer.toString((this.casillas[i][j].isMina())? -1 : casillas[i][j].getVecinos());
                if(!casillas[i][j].isDescubierta())
                    casilla = "("+casilla+")";
                if((casillas[i][j].isBandera() || casillas[i][j].isInterrogacion())){
                    if(casillas[i][j].isBandera()){
                        casilla = "["+casilla+"]";
                    }else{
                        casilla = "{"+casilla+"}";
                    }      
                }
                System.out.print(casilla+"\t");
            }
            System.out.println();
        }
    }
   
    //Metodo que permite descubrir una casilla escogida por el usuario
    public void descubrirCasilla(int fila, int columna){
        //Si la casilla seleccionada es una mina, la destapa y da por finalizada la partida.
        if(casillas[fila][columna].isMina()){
            casillas[fila][columna].setDescubierta(true);
            finalizado = true;
            descubrirTodo();
            JOptionPane.showMessageDialog(null, "Perdiste GG :(");
        }// Si la casilla seleccionada no tiene minas alrededor.
        else if(casillas[fila][columna].getVecinos() == 0 && !casillas[fila][columna].isDescubierta()){
            casillas[fila][columna].setDescubierta(true);
            descubrirVecinosRecursivo(fila, columna);
            
        }//En caso de que la casilla seleccionada ya esté descubierta y el 
        //numero de casillas vecinas marcadas con bandera sea igual al número de minas vecinas, descubre todas las casillas vecinas.
        else if(banderasVecinas(fila, columna) == casillas[fila][columna].getVecinos()){
            descubrirVecinos(fila, columna);
        }//si la casilla no esta descubierta, la descubre normalmente.
        else if(!casillas[fila][columna].isDescubierta()){
            casillas[fila][columna].setDescubierta(true);
        }
    }
    
    /**
     * 
     * @param fila
     * @param columna 
     */
    public void descubrirVecinos(int fila, int columna){
        //si la casilla es una esquina del tablero
        if((fila==0||fila==filas-1) && (columna==0||columna==columnas-1)){
            int df = (fila==0)?1 :-1 ,dc = (columna==0)?1:-1;
            casillas[fila][columna+dc].setDescubierta(true);
            casillas[fila+df][columna].setDescubierta(true);
            casillas[fila+df][columna+dc].setDescubierta(true);    
        }//si la casilla esta en la primera o ultima columna del tablero
        else if(columna==0||columna==columnas-1){
            int dc = (columna==0)?1:-1;
            for(int k=-1; k<2; k++){
                casillas[fila+k][columna].setDescubierta(true);
                casillas[fila+k][columna+dc].setDescubierta(true);    
            }           
        }//si la casilla esta en la primera o ultima fila del tablero
        else if(fila==0||fila==filas-1){
            int df = (fila==0)? 1: -1;
            for(int k=-1; k<2; k++){
                casillas[fila][columna+k].setDescubierta(true);
                casillas[fila+df][columna+k].setDescubierta(true);   
            }
        }//en otro caso
        else{                
            for (int k = -1; k < 2; k++) {
                for (int s = -1; s < 2; s++) {
                    casillas[fila+k][columna+s].setDescubierta(true);
                }    
            }
        }
    }
    
    
    /**
     * Metodo que descubre las casillas vecinas a una dada que no tiene minas alrededor..
     * @param fila
     * @param columna 
     */
    public void descubrirVecinosRecursivo(int fila, int columna){ 
        //si la casilla es una esquina del tablero
        if((fila==0||fila==filas-1) && (columna==0||columna==columnas-1)){
            int df = (fila==0)?1 :-1 ,dc = (columna==0)?1:-1;
            if(casillas[fila][columna+dc].getVecinos()==0){descubrirVecinos(fila,columna+dc);}
            else{casillas[fila][columna+dc].setDescubierta(true);}
            
            if(casillas[fila+df][columna].getVecinos()==0){descubrirVecinos(fila+df,columna);}
            else{casillas[fila+df][columna].setDescubierta(true);}
            
            if(casillas[fila+df][columna+dc].getVecinos()==0){descubrirVecinos(fila+df,columna+dc);}
            else{casillas[fila+df][columna+dc].setDescubierta(true);}
        }
        //si la casilla esta en la primera o ultima columna del tablero
        else if(columna==0||columna==columnas-1){
            int dc = (columna==0)?1:-1;
            for (int i = -1; i < 2; i++) {
                if(casillas[fila+i][columna].getVecinos()==0){descubrirVecinos(fila+i,columna);}
                else{casillas[fila+i][columna].setDescubierta(true);}
                
                if(casillas[fila+i][columna+dc].getVecinos()==0){descubrirVecinos(fila+i,columna+dc);}
                else{casillas[fila+i][columna+dc].setDescubierta(true);}
            }
                   
        }
        //si la casilla esta en la primera o ultima fila del tablero
        else if(fila==0||fila==filas-1){
            int df = (fila==0)? 1: -1;
            for(int i=-1; i<2; i++){
                if(casillas[fila][columna+i].getVecinos()==0){descubrirVecinosRecursivo(fila,columna+i);}
                else{casillas[fila][columna+i].setDescubierta(true);}
                
                if(casillas[fila+df][columna+i].getVecinos()==0){descubrirVecinos(fila+df,columna+i);}
                else{casillas[fila+df][columna+i].setDescubierta(true);}
                   
            }
        }//en otro caso
        else{                
            for (int k = -1; k < 2; k++) {
                for (int s = -1; s < 2; s++) {
                    if(casillas[fila+k][columna+s].getVecinos()==0){descubrirVecinos(fila+k,columna+s);}
                    else{casillas[fila+k][columna+s].setDescubierta(true);}
                }    
            }
        }
    }    
    
    public boolean vecinosNulos(int fila, int columna){
        boolean exist = false;
        //Si la casilla es una esquina
        if((fila==0||fila==filas-1) && (columna==0||columna==columnas-1)){
            int df = (fila==0)?1 :-1 ,dc = (columna==0)?1:-1;
            return ((casillas[fila][columna+dc].getVecinos()==0)
                    || (casillas[fila+df][columna].getVecinos()==0)
                    || (casillas[fila+df][columna+dc].getVecinos()==0));
        }//Si la casilla esta en la primera o ultima columna del tablero
        else if(columna==0||columna==columnas-1){
            int dc = (columna==0)?1:-1;
            for (int i = -1; i < 2 && !exist; i++) {
                exist = casillas[fila+i][columna].getVecinos()==0 || casillas[fila+i][columna+dc].getVecinos()==0;
            }
            return exist;
        }//Si la casilla esta en la primer o ultima fila del tablero
        else if(fila==0||fila==filas-1){
            int df = (fila==0)? 1: -1;
            for (int i = -1; i < 2 && !exist; i++) {
                exist = casillas[fila][columna+i].getVecinos()==0 || casillas[fila+df][columna+i].getVecinos()==0;
            }
            return exist;
        }else{
            for (int i = -1; i < 2 && !exist; i++) {
                for (int j = -1; j < 2 && !exist; j++) {
                   exist = casillas[fila+i][columna+j].getVecinos()==0 || casillas[fila+i][columna+j].getVecinos()==0;
                }
            }
            return exist;
        }
    }
    
    /**
     * Metodo que descubre todas las casillas una vez terminada la partida.
     */
    public void descubrirTodo(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                casillas[i][j].setDescubierta(true);
            }
        }
    }
    
    /**
     * Metodo que retorna el numero de casillas marcadas con bandera en las casillas vecinas a la seleccionada.
     * @param i
     * @param j
     * @return 
     */
    public int banderasVecinas(int i, int j){
        int banderas = 0;
        //si la casilla es una esquina del tablero
        if((i==0||i==filas-1) && (j==0||j==columnas-1)){
            int df = (i==0)?1 :-1 ,dc = (j==0)?1:-1;
            if(casillas[i][j+dc].isBandera())
                banderas++;
            if(casillas[i+df][j].isBandera())
                banderas++;
            if(casillas[i+df][j+dc].isBandera())
                banderas++;
            return banderas;
        }
        //si la casilla esta en la primera o ultima columna del tablero
        if(j==0||j==columnas-1){
            int dc = (j==0)?1:-1;
            for(int k=-1; k<2; k++){
                if(casillas[i+k][j].isBandera())
                    banderas++;
                if(casillas[i+k][j+dc].isBandera())
                    banderas++;
            }
            return banderas;
        }
        //si la casilla esta en la primera o ultima fila del tablero
        if(i==0||i==filas-1){
            int df = (i==0)? 1: -1;
            for(int k=-1; k<2; k++){
                if(casillas[i][j+k].isBandera())
                    banderas++;
                if(casillas[i+df][j+k].isBandera())
                    banderas++;
            }
            return banderas;
        }
        //en otro caso
        for (int k = -1; k < 2; k++) {
            for (int s = -1; s < 2; s++) {
                if(casillas[i+k][j+s].isBandera())
                    banderas++;
            }
        }
        return banderas;
    }
    
    /**
     * 
     * @return 
     */
    public int getFilas() {
        return filas;
    }
    
    /**
     * 
     * @param filas 
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }
    
    /**
     * 
     * @return 
     */
    public int getColumnas() {
        return columnas;
    }
    
    /**
     * 
     * @param columnas 
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
    
    /**
     * 
     * @return 
     */
    public int getMinas() {
        return minas;
    }
    
    /**
     * 
     * @param minas 
     */
    public void setMinas(int minas) {
        this.minas = minas;
    }
    
    /**
     * 
     * @return 
     */
    public int getMinasRestantes() {
        return minasRestantes;
    }
    
    /**
     * 
     * @param minasRestantes 
     */
    public void setMinasRestantes(int minasRestantes) {
        this.minasRestantes = minasRestantes;
    }
    
    /**
     * 
     * @return 
     */
    public Casilla[][] getCasillas() {
        return casillas;
    }
    
    /**
     * 
     * @param casillas 
     */
    public void setCasillas(Casilla[][] casillas) {
        this.casillas = casillas;
    }
    
    
    
    
}
