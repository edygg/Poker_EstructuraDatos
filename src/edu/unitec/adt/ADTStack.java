/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public abstract class ADTStack {
    
    protected int size;
    
    public ADTStack() {
        size = 0;
    }
    
    public abstract boolean push(Object E);
    public abstract Object pop();
    public abstract Object peek();
    
    public int getSize() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
}
