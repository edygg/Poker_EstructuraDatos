/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import javax.swing.ImageIcon;

/**
 *
 * @author EdilsonFernando
 */
public class Carta {
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

}
