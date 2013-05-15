/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *
 * @author EdilsonFernando
 */
public class Carta implements Comparable, Serializable {
    public static String TREBOL = "Trebol";
    public static String ESPADA = "Espada";
    public static String DIAMANTE = "Diamante";
    public static String CORAZONES = "Corazones";
    public static String JOKER = "Joker";
    
    
    private int numero;
    private String palo;
    private ImageIcon img;

    public Carta(int numero, String palo, ImageIcon img) throws Exception {
        if (numero >= 0 && numero <= 13) {
            this.numero = numero;
        } else {
            throw new Exception("Número no válido");
        }
        
        if (palo.equals(TREBOL) || palo.equals(ESPADA) || palo.equals(DIAMANTE) || palo.equals(CORAZONES) || palo.equals(JOKER)) {
            this.palo = palo;
        } else {
            throw new Exception("Palo no válido");
        }
        
        this.img = img;
    }
    
    public String getPalo() {
        return palo;
    }

    public int getNumero() {
        return numero;
    }
    
    public ImageIcon getImage() {
        return this.img;
    }
    
    @Override
    public String toString() {
        return this.palo + " " + this.numero;
    }
    
    public boolean equalsNum(Carta other) {
        return this.getNumero() == other.getNumero();
    }

    
    @Override
    public int compareTo(Object other) {
        if (other instanceof Carta) {
            if (this.getNumero() == 1) {
                if (((Carta) other).getNumero() == 1) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                if (((Carta) other).getNumero() == 1) {
                    return -1;
                } else if (this.getNumero() > ((Carta) other).getNumero()) {
                    return 1;
                } else if (this.getNumero() < ((Carta) other).getNumero()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
        
        return 0;
    }
 
}
