/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public abstract class ADTList {
    
    protected int size;
    
    public ADTList() {
        this.size = 0;
    }
        
    public abstract boolean insert(Object E, int p);
    public abstract Object remove(int p);
    public abstract Object first();
    public abstract Object last();
    public abstract int getCapacity();
    public abstract boolean isFull();
    public abstract void clear();
    public abstract int indexOf(Object E);
    public abstract Object get(int p);    
            
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int getSize() {
        return size;
    }

}
