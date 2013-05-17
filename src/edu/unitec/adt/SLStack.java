/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 * Implementación mediante nodos con enlaces simples de una pila.
 * @author EdilsonFernando
 * @see SLNode
 */
public class SLStack extends ADTStack {
    
    /**
     * Nodo cabeza de la pila.
     */
    private SLNode head;
    
    /**
     * Implementación del método push de ADTStack. Ingresa un elemento a la pila.
     * @param E Elemento que será ingresado a la pila
     * @return Retorna true si es posible agregar el elemento, false en caso
     * contrario.
     * @see ADTStack#push(java.lang.Object) 
     */
    @Override
    public boolean push(Object E) {
        SLNode neo = new SLNode(E);
        
        //Revisa que hubo espacio para crear el nodo
        if (neo == null) {
            return false;
        }
        
        //Caso 1: La pila está vacía.
        if (isEmpty()) {
            head = neo;
        } else {
            //Caso 2: La pila contiene elementos, se inserta en la cabeza.
            neo.setNext(head);
            head = neo;
        }
        
        size++;
        return true;
    }
    
    /**
     * Implementación del método pop de ADTStack. Saca el último elemento 
     * ingresado a la pila. Retorna null si la pila está vacía.
     * @return Retorna el último objeto ingresado a la pila, si está vacía
     * retorna null.
     * @see ADTStack#pop() 
     */
    @Override
    public Object pop() {
        //La pila está vacía, no hay datos que devolver
        if (isEmpty()) {
            return null;
        }
        
        SLNode rem = head;
        Object retval = null;
        
        //Remueve la cabeza y devuelve los datos contenidos en el nodo.
        head = head.getNext();
        rem.setNext(null);
        retval = rem.getData();
        rem.setData(null);
        size--;
        
        return retval;
    }
    
    /**
     * Implementación del método peek de ADTStack. Retorna el último elemento 
     * ingresado a la pila sin extraerlo de ésta.
     * @return Retorna el último elemento ingresado a la pila.
     * @see ADTStack#peek() 
     */
    @Override
    public Object peek() {
        return head.getData();
    }
    
}
