/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

import java.io.Serializable;

/**
 * Estructura nodo que encapsula los datos y mantiene un apuntador al siguiente
 * elemento nodo. Implementa Serializable para ser almacenado en archivos 
 * binarios.
 * @author EdilsonFernando
 */
public class SLNode implements Serializable {
    /**
     * Objecto que contiene los datos.
     */
    private Object data;
    
    /**
     * Referencia al siguiente nodo.
     */
    private SLNode next;
    
    /**
     * Constructor por defecto. Inicializa los datos y la referencia al
     * siguiente nodo en null.
     */
    public SLNode() {
        this(null);
    }
    
    /**
     * Constructor que recibe una referencia a un objeto para ser almacenado
     * en la estructura, inicializa la referencia al siguiente nodo en null.
     * @param data Dato que será almacenado dentro de la estructura nodo.
     */
    public SLNode(Object data) {
        this.data = data;
        this.next = null;
    }
    
    /**
     * Mutador para los datos del nodo.
     * @param data Datos que se almacenarán en el nodo.
     */
    public void setData(Object data) {
        this.data = data;
    }
    
    /**
     * Metodo accesor para los datos del nodo.
     * @return Retornan el objeto que almacena el nodo.
     */
    public Object getData() {
        return this.data;
    }
    
    /**
     * Mutador para la referencia al sigiente nodo.
     * @param n Referencia del siguiente nodo.
     */
    public void setNext(SLNode n) {
        this.next = n;
    }
    
    /**
     * Método accesor para la referencia al siguiente noo.
     * @return Retorna la referencia del siguiente nodo.
     */
    public SLNode getNext() {
        return this.next;
    }
}
