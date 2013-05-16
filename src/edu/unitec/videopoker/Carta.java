/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 *  Almacena toda la información necesaria para representar una carta. Además
 *  de guardar el número y el palo, almacena también la imagen de la carta.
 *  Implementa la intefaz Serializable del paquete java.io para ser guardada
 *  posteriormente en archivos binarios.
 *  @author EdilsonFernando
 */
public class Carta implements Comparable, Serializable {
    /**
     * Constantes para identificar el palo de los Tréboles
     */
    public static String TREBOL = "Trebol";
    /**
     * Constantes para identificar el palo de las Espadas
     */
    public static String ESPADA = "Espada";
    /**
     * Constantes para identificar el palo de los Diamantes
     * @since Video Poker 1.0
     */
    public static String DIAMANTE = "Diamante";
    /**
     * Constantes para identificar el palo de los Corazones
     */
    public static String CORAZONES = "Corazones";
    /**
     * Constantes para identificar la carta especial Joker
     */
    public static String JOKER = "Joker";
    
    /**
     * Número de la carta
     */
    private int numero;
    /**
     * Palo de la carta
     */
    private String palo;
    /**
     * Imagen de la carta
     */
    private ImageIcon img;

    /**
     * Inicializa la carta con su número, palo e imagen correspondiente.
     * @param numero Numero de la cara, en el intervalo de 0-13, 
     * donde 0 identifica un Joker.
     * @param palo Una cadena con el palo de la carta. Se recomienda 
     * utilizar las constantes que proporciona la clase.
     * @param img Proporciona la imagen respectiva de la carta.
     * @throws Exception Si el numero de la carta es inválido o el palo, lanza
     * la excepción.
     * @see Carta#CORAZONES
     * @see Carta#DIAMANTE
     * @see Carta#ESPADA
     * @see Carta#TREBOL
     */
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
    
    /**
     * Metodo accesor que retorna a que palo pertenece la carta. Revisar las 
     * constantes que provee esta clase.
     * @return La constante que identifica el palo al que pertenece la carta.
     * @see Carta#CORAZONES
     * @see Carta#DIAMANTE
     * @see Carta#ESPADA
     * @see Carta#TREBOL
     */
    public String getPalo() {
        return palo;
    }

    /**
     * Metodo accesor que retorna el número de la carta.
     * @return Un número entre 0 y 13, donde 0 representa un Joker.
     */
    public int getNumero() {
        return numero;
    }
    
    /**
     * Metodo accesor que retorna la imagen de la carta.
     * @return Retorna la imagen correspondiente a la carta.
     */
    public ImageIcon getImage() {
        return this.img;
    }
    
    /**
     * Representación String de un objeto Carta.
     * @return Retorna una cadena que contiene el palo y el número de la carta.
     * @see Carta#CORAZONES
     * @see Carta#DIAMANTE
     * @see Carta#ESPADA
     * @see Carta#TREBOL
     */
    @Override
    public String toString() {
        return this.palo + " " + this.numero;
    }
    
    /**
     * Método que compara dos cartas para saber si son iguales en número.
     * @param other Objeto carta a comparar.
     * @return true si las cartas son iguales en numero, false en caso contrario.
     */
    public boolean equalsNum(Carta other) {
        return this.getNumero() == other.getNumero();
    }

    /**
     * Método que compara dos cartas. El As siempre será mayor que cualquier
     * otra carta dentro de la baraja. Retorna un entero que identifica si un 
     * el this y otro objeto es mayor, menor o igual.
     * @param other El objeto a comparar
     * @return Retorna -1 si el this es menor que el otro objeto, cero si son
     * iguales y 1 si el this es mayor que el otro objeto.
     */
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
