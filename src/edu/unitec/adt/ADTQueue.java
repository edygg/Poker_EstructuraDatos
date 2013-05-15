/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

import java.io.Serializable;

/**
 *
 * @author EdilsonFernando
 */
public abstract class ADTQueue implements Serializable {
    
    protected int size;

    public ADTQueue() {
        size = 0;
    }
    
    public abstract boolean queue(Object E);
    public abstract Object dequeue();
    public abstract Object peek();
    
    public boolean isEmpty() {
        return size == 0;
    }
}
