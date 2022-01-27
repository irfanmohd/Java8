package com.datastructure;

public class Node {


    public Node(Object data2) {
        this.data = data2;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Object data;
    public Node next;
}
