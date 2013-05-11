/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public class SLList extends ADTList {
    
    private SLNode head;
    
    public SLList() {
        super();
        head = null;
    }
    
    @Override
    public boolean insert(Object E, int p) {
        if (!(p >= 0 && p <= size)) {
            return false;
        }
        
        SLNode neo = new SLNode(E);
        
        if (neo == null) {
            return false;
        }
        
        if (isEmpty()) {
            head = neo;
        } else {
            if (p == 0) {
                neo.setNext(head);
                head = neo;
            } else {
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
    
    @Override
    public Object remove(int p) {
        if (isEmpty()) {
            return null;
        }
        if (!(p >= 0 && p < size)) {
            return null;
        }
        
        SLNode rem = head;
        Object retval = null;
        
        if (p == 0) {
            head = head.getNext();
        } else {
            for (int i = 0; i < p - 1; i++) {
                rem = rem.getNext();
            }
            
            SLNode tmp = rem;
            rem = rem.getNext();
            tmp.setNext(rem.getNext());
        }
        
        retval = rem.getData();
        rem.setData(null);
        rem.setNext(null);
        size--;
        return retval;
    }
    
    @Override
    public Object first() {
        if (isEmpty()) {
            return null;
        }
        
        return head.getData();
    }
    
    @Override
    public Object last() {
        if (isEmpty()) {
            return null;
        }
        
        SLNode tmp = head;
        
        for (int i = 0; i < size - 1; i++) {
            tmp = tmp.getNext();
        }
        
        return tmp.getData();
    }
    
    @Override
    public int getCapacity() {
        return -1;
    }
    
    @Override
    public boolean isFull() {
        return false;
    }
    
    @Override
    public void clear() {
        if (!isEmpty()) {
            head = null;
            size = 0;
        }
    }
    
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
    
    @Override
    public Object get(int p) {
        if (isEmpty()) {
            return null;
        }
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
