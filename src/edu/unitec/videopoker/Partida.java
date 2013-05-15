/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.videopoker;

import edu.unitec.adt.SLList;
import java.io.Serializable;

/**
 *
 * @author EdilsonFernando
 */
public class Partida implements Serializable {
    
    private SLList cartas;
    private String descripción;
    
    public Partida(Carta[] cards, String des) {
        cartas = new SLList();
        
        for (int i = 0; i < cards.length; i++) {
            cartas.insert(cards[i], cartas.getSize());
        }
        
        this.descripción = des;
    }
    
    public Carta getCarta(int p) {
        return (Carta) cartas.get(p);
    }
    
    public String getDescripcion() {
        return this.descripción;
    }
    
}
