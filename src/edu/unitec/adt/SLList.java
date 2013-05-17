/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

import java.io.Serializable;

/**
 * Clase SLList que implementa los métodos de ADTList. Esta implementación 
 * está basada en listas con enlaces simples, por lo tanto revise que el
 * rendimiento de su programa no se vea afectado por el orden de las 
 * operaciones de esta clase. 
 * Implementa Serizalizable para guardarse en archivos binarios.
 * @author EdilsonFernando
 * @see ADTList
 * @see SLNode
 */
public class SLList extends ADTList implements Serializable {
    
    /**
     * Nodo que identifica la cabeza de la lista.
     * @see SLNode
     */
    private SLNode head;
    
    /**
     * Constructor de la clase, inicializa la cabeza de la lista en null.
     */
    public SLList() {
        super();
        head = null;
    }
    
    /**
     * Implementación del método insert de ADTList. Inserta un elemento en
     * la lista.
     * @param E Elemento a insertar
     * @param p Posición donde se insertará el elemento
     * @return Retorna true si se logró insertar el elemento en una posición
     * válida, false el caso contrario.
     * @see ADTList#insert(java.lang.Object, int) 
     */
    @Override
    public boolean insert(Object E, int p) {
        //Precondición: es una posición valida
        if (!(p >= 0 && p <= size)) {
            return false;
        }
        
        SLNode neo = new SLNode(E);
        
        //Precondición: Hay memoria para el nodo
        if (neo == null) {
            return false;
        }
        
        //Caso 1: La lista está vacía
        if (isEmpty()) {
            head = neo;
        } else {
            //Caso 2: La lista contiene elementos, se inserta al principio
            if (p == 0) {
                neo.setNext(head);
                head = neo;
            } else {
                //Caso 3: La lista tiene elementos, se inserta en medio
                SLNode tmp = head;
                
                for (int i = 0; i < p - 1; i++) {
                    tmp = tmp.getNext();
                }
                
                neo.setNext(tmp.getNext());
                tmp.setNext(neo);
            }
        }
        
        size++;
        return true;
    }
    
    /**
     * Agrega un elemento al final de la lista.
     * @param E Elemento a insertar
     * @return Retorna true si es posible insertarlo en la lista, false en 
     * caso contrario.
     */
    public boolean insert(Object E) {
        return this.insert(E, size);
    }
    
    /**
     * Implementación del método remove de ADTList. Elimina un elemento de 
     * la lista.
     * @param p Posición donde se encuentra el elemento a remover
     * @return Retorna true si el elemento se removió exitosamente, false
     * en caso contrario.
     * @see ADTList#remove(int) 
     */
    @Override
    public Object remove(int p) {
        //Precondición: La lista está vacía
        if (isEmpty()) {
            return null;
        }
        //Precondición: Es una posición válida
        if (!(p >= 0 && p < size)) {
            return null;
        }
        
        //Nodo a remover
        SLNode rem = head;
        //Valor almacenado en el nodo que se retorna
        Object retval = null;
        
        //Caso 1: se remueve la cabeza
        if (p == 0) {
            head = head.getNext();
        } else {
            //Caso 2: Se remueve un nodo de en medio
            for (int i = 0; i < p - 1; i++) {
                rem = rem.getNext();
            }
            
            SLNode tmp = rem;
            rem = rem.getNext();
            tmp.setNext(rem.getNext());
        }
        
        retval = rem.getData();
        //Desconectando el nodo de la lista
        rem.setData(null);
        rem.setNext(null);
        size--;
        return retval;
    }
    
    /**
     * Implementación del método first de ADTList. Retorna el objeto que se 
     * encuentra en la primera posición de la lista.
     * @return Retorna el elemento en la primer posición, null si la
     * lista está vacía.
     * @see ADTList#first() 
     */
    @Override
    public Object first() {
        //Precondición: La lista está vacía
        if (isEmpty()) {
            return null;
        }
        
        return head.getData();
    }
    
    /**
     * Implementación del método last de ADTList. Retorna el último objeto de
     * la lista.
     * @return Retorna el último elemento de la lista, null si la lista está
     * vacía.
     * @see ADTList#last() 
     */
    @Override
    public Object last() {
        //Precondición: La lista está vacía
        if (isEmpty()) {
            return null;
        }
        
        SLNode tmp = head;
        
        for (int i = 0; i < size - 1; i++) {
            tmp = tmp.getNext();
        }
        
        return tmp.getData();
    }
    
    /**
     * Implementación del método getCapacity de ADTList. Retorna siempre
     * -1 debido a que la capacidad de la lista depende de cuanta memoria
     * haya disponible para reservar espacio para los nodos.
     * @return Retorna -1 porque es de capacidad dependiente de la memoria
     * disponible.
     * @see SLNode
     * @see ADTList#getCapacity() 
     */
    @Override
    public int getCapacity() {
        return -1;
    }
    
    /**
     * Implementación del método isFull de ADTList. Retorna siempre false
     * debido a que depende de la memoria que se pueda reservar para crear
     * nuevos nodos.
     * @return Retorna false por su dependencia con la máquina
     * @see SLNode
     * @see ADTList#isFull() 
     */
    @Override
    public boolean isFull() {
        return false;
    }
    
    /**
     * Implementación del método clear de ADTList. Elimina todo el contenido
     * de la lista, incluyecndo los nodos y sus datos.
     * @see ADTList#clear() 
     */
    @Override
    public void clear() {
        //Si la lista no está vacía ejecuta la limpieza
        if (!isEmpty()) {
            head = null;
            size = 0;
        }
    }
    
    /**
     * Implementación del método indexOf de ADTList. Busca la posición de un
     * objeto específico de la lista. Si lo encuentra retorna su posición, caso
     * contrario retorna -1. Le recomendamos sobreescriba el método equals del
     * objecto para la comparación de igualdad entre dos objetos.
     * @param E Objecto a comparar.
     * @return Retorna él indice dentro de la lista si se encuentra, caso 
     * contrario retorna -1.
     * @see ADTList#indexOf(java.lang.Object) 
     */
    @Override
    public int indexOf(Object E) {
        if (isEmpty()) {
            return -1;
        }
        
        SLNode tmp = head;
        
        for (int i = 0; i < size; i++) {
            if (tmp.getData().equals(E)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Implementación del método get de ADTList. Retorna el objeto que está
     * en la posición especificada como parámetro del método, si ésta es
     * válida, caso contrario retorna null.
     * @param p Posición del elemento en la lista.
     * @return Retorna el objecto si la posición es válida, null en el caso 
     * contrario.
     */
    @Override
    public Object get(int p) {
        //Si la lista está vacía no hay elementos
        if (isEmpty()) {
            return null;
        }
        //Si la posición es válida
        if (!(p >= 0 && p < size)) {
            return null;
        }
        
        SLNode tmp = head;
        
        for (int i = 0; i < p; i++) {
            tmp = tmp.getNext();
        }
        
        return tmp.getData();
    }
    
}   
