/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public class SLQueue extends ADTQueue {
    
    private SLNode head;
    
    public SLQueue() {
        head = null;
    }
    
    @Override
    public boolean queue(Object E) {
        SLNode neo = new SLNode(E);
        
        if (neo == null) {
            return false;
        }
        
        if (isEmpty()) {
            head = neo;
        } else {
            SLNode tmp = head;
            
            for (int i = 0; i < size - 1; i++) {
                tmp = tmp.getNext();
            }
            
            tmp.setNext(neo);
        }
        
        size++;
        return true;
    }
    
    @Override
    public Object dequeue() {
        if (isEmpty()) {
            return null;
        }
        
        SLNode rem = head;
        head = head.getNext();
        Object retval = rem.getData();
        
        rem.setData(null);
        rem.setNext(null);
        
        size--;
        return retval;
    }
    
    @Override
    public Object peek() {
        return head.getData();
    }
}
