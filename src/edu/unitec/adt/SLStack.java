/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public class SLStack extends ADTStack {
    
    private SLNode head;
    
    @Override
    public boolean push(Object E) {
        SLNode neo = new SLNode(E);
        
        if (neo == null) {
            return false;
        }
        
        if (isEmpty()) {
            head = neo;
        } else {
            neo.setNext(head);
            head = neo;
        }
        
        size++;
        return true;
    }
    
    @Override
    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        
        SLNode rem = head;
        Object retval = null;
        
        head = head.getNext();
        rem.setNext(null);
        retval = rem.getData();
        rem.setData(null);
        size--;
        
        return retval;
    }
    
    @Override
    public Object peek() {
        return head.getData();
    }
    
}
