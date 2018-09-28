/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author SEBASTIAN ROJAS
 */
public class Casilla {
    //Atributos
    
    /**
     * 
     */
    private boolean mina;
    /**
     * 
     */
    private boolean descubierta;
    /**
     * 
     */
    private int vecinos;
    /**
     * 
     */
    private String imagen;
    /**
     * 
     */
    private boolean bandera;
    /**
     * 
     */
    private boolean interrogacion;
    

//Métodos
    
    public Casilla(boolean mina) {
        this.mina = mina;
        this.descubierta = false;
        this.interrogacion = false;
        this.bandera = false;
    }

    /**
     *
     * @return 
     */
    public boolean isMina() {
        return mina;
    }
    /**
     * 
     * @param mina 
     */
    public void setMina(boolean mina) {
        this.mina = mina;
    }
    /**
     * 
     * @return 
     */
    public boolean isDescubierta() {
        return descubierta;
    }
    /**
     * 
     * @param descubierta 
     */
    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }
    /**
     * 
     * @return 
     */
    public int getVecinos() {
        return vecinos;
    }
    /**
     * 
     * @param vecinos 
     */
    public void setVecinos(int vecinos) {
        this.vecinos = vecinos;
    }
    /**
     * 
     * @return 
     */
    public String getImagen() {
        return imagen;
    }
    /**
     * 
     * @param imagen 
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    /**
     * 
     * @return 
     */
    public boolean isBandera() {
        return bandera;
    }
    /**
     * 
     * @param bandera 
     */
    public void setBandera(boolean bandera) {
        this.bandera = bandera;
    }
    /**
     * 
     * @return 
     */
    public boolean isInterrogacion() {
        return interrogacion;
    }
    /**
     * 
     * @param interrogacion 
     */
    public void setInterrogacion(boolean interrogacion) {
        this.interrogacion = interrogacion;
    }
    
    
    
}
