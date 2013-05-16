/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import edu.unitec.adt.SLList;
import java.io.Serializable;

/**
 * Esta clase sirve para almacenar las cartas de una partida (5) y una 
 * descripción de esta (si ha perdido o ha ganado con alguna
 * combinación). Implementa Serializable para guardarse en archivos
 * binarios.
 * @author EdilsonFernando
 */
public class Partida implements Serializable {
    /**
     * Lista de cartas
     * @see SLList
     */
    private SLList cartas;
    /**
     * Cadena con una descripción del juego
     */
    private String descripción;
    
    /**
     * Contructor que inicializa una partida a partir de un arreglo de cartas
     * y una descripción de lo que sucedió en la partida.
     * @param cards Arreglo de cartas que identifica la partida
     * @param des Cadena con la descripción de lo que sucedió en la partida
     */
    public Partida(Carta[] cards, String des) {
        cartas = new SLList();
        
        for (int i = 0; i < cards.length; i++) {
            cartas.insert(cards[i], cartas.getSize());
        }
        
        this.descripción = des;
    }
    
    /**
     * Obtiene alguna de las cartas 
     * @param p
     * @return 
     */
    public Carta getCarta(int p) {
        return (Carta) cartas.get(p);
    }
    
    public String getDescripcion() {
        return this.descripción;
    }
    
}
