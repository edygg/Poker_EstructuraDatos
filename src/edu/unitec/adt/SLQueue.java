/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 * Implementación con nodos enlazados de manera simple de una cola. Utiliza
 * mecanismos similiares a los de SLList.
 * @author EdilsonFernando
 * @see SLList
 */
public class SLQueue extends ADTQueue {
    
    /**
     * Nodo que mantiene una referencia al primer elemento de la cola.
     * @see SLNode
     */
    private SLNode head;
    
    /**
     * Constructor predeterminado de una SLQueue. Inicializa la cabeza en null.
     */
    public SLQueue() {
        head = null;
    }
    
    /**
     * Implementación del método queue de ADTQueue. Inserta un elemento al 
     * final de la cola.
     * @param E Elemento a insertar.
     * @return Retorna true si es posible agregarlo a la cola, false en caso
     * contrario.
     * @see ADTQueue#queue(java.lang.Object) 
     */
    @Override
    public boolean queue(Object E) {
        SLNode neo = new SLNode(E);
        
        //Verifica que hubo memoria para crear el nodo
        if (neo == null) {
            return false;
        }
        
        //Caso 1: La cola está vacía
        if (isEmpty()) {
            head = neo;
        } else {
            //Caso 2: inserta el elemento al final de la cola
            SLNode tmp = head;
            
            for (int i = 0; i < size - 1; i++) {
                tmp = tmp.getNext();
            }
            
            tmp.setNext(neo);
        }
        
        size++;
        return true;
    }
    
    /**
     * Implementación del método dequeue de ADTQueue. Elimina un elemento del 
     * inicio de la cola.
     * @return Retorna el elemento eliminado, si la cola está vacía retorna
     * null.
     * @see ADTQueue#dequeue() 
     */
    @Override
    public Object dequeue() {
        //Si la cola está vacía retorna null
        if (isEmpty()) {
            return null;
        }
        
        //Eliminando el nodo en la cabeza
        SLNode rem = head;
        head = head.getNext();
        Object retval = rem.getData();
        
        rem.setData(null);
        rem.setNext(null);
        
        size--;
        return retval;
    }
    
    /**
     * Implementación del método peek de ADTQueue. Método que retorna el 
     * elemento inicial de la cola sin extraerlo de ella.
     * @return Retorna el primer elemento de la cola, si la cola está vacía
     * retorna null.
     * @see ADTQueue#peek() 
     */
    @Override
    public Object peek() {
        return head.getData();
    }
}
