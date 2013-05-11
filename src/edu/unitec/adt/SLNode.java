/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unitec.adt;

/**
 *
 * @author EdilsonFernando
 */
public class SLNode {
    private Object data;
    private SLNode next;
    
    public SLNode() {
        this(null);
    }
    
    public SLNode(Object data) {
        this.data = data;
        this.next = null;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public Object getData() {
        return this.data;
    }
    
    public void setNext(SLNode n) {
        this.next = n;
    }
    
    public SLNode getNext() {
        return this.next;
    }
}
