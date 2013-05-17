/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

import java.io.Serializable;

/**
 * Clase Padre Abstracta ADTQueue, define todos los métodos necesarios para una
 * cola. Implementa Serializable para ser guardada en archivos binarios.
 * @author EdilsonFernando
 */
public abstract class ADTQueue implements Serializable {
    /**
     * Tamaño de la cola.
     */
    protected int size;

    /**
     * Contructor predeterminado que inicializa la cola.
     */
    public ADTQueue() {
        size = 0;
    }
    
    /**
     * Inserta un elemento al final de la cola.
     * @param E Elemento a insertar.
     * @return Retorna true si es posible agregarlo a la cola, false en caso
     * contrario.
     */
    public abstract boolean queue(Object E);
    
    /**
     * Elimina un elemento del inicio de la cola.
     * @return Retorna el elemento eliminado, si la cola está vacía retorna
     * null.
     */
    public abstract Object dequeue();
    
    /**
     * Método que retorna el elemento inicial de la cola sin extraerlo de ella.
     * @return Retorna el primer elemento de la cola, si la cola está vacía
     * retorna null.
     */
    public abstract Object peek();
    
    /**
     * Metodo que retorna un booleano que identifica si la cola está vacía o
     * no.
     * @return Retorna true si la cola está vacía, false en caso contrario.
     */
    public boolean isEmpty() {
        return size == 0;
    }
}
