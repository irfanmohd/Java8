package com.datastructure;

public class CustomerLinkedList {

    private Node head;
    int size;

    public void addFirst(Object data) {
        Node node = new Node(data);
        node.setNext(head);
        head = node;

    }


    public void add(Object data) {

        if (head == null) {
            head = new Node(data);
            size++;
        } else {


            Node newNode = new Node(data);
            Node headNode = head;
            while (headNode.getNext() != null) {
                headNode = headNode.getNext();
            }
            headNode.setNext(newNode);

            size++;
        }
    }

    public void add(Object data, int index) {


        Node crunchifyTemp = new Node(data);
        Node crunchifyCurrent = head;
        if (crunchifyCurrent != null) {
            // crawl to the requested index or the last element in the list, whichever comes first
            for (int i = 0; i < index && crunchifyCurrent.getNext() != null; i++) {
                crunchifyCurrent = crunchifyCurrent.getNext();
            }
        }

        // set the new node's next-node reference to this node's next-node reference
        crunchifyTemp.setNext(crunchifyCurrent.getNext());

        // now set this node's next-node reference to the new node
        crunchifyCurrent.setNext(crunchifyTemp);

    }

    public Object get(int index) {
        // index must be 1 or higher
        if (index < 0)
            return null;
        Node crunchifyCurrent = null;
        if (head != null) {
            crunchifyCurrent = head;
            for (int i = 0; i < index; i++) {
                System.out.println("insude");

                crunchifyCurrent = crunchifyCurrent.getNext();
            }
            return crunchifyCurrent.getData();
        }
        return crunchifyCurrent;

    }


    public String toString() {
        String output = "";

        if (head != null) {
            output += "[" + head.getData().toString() + "]";
            Node crunchifyCurrent = head.getNext();

            while (crunchifyCurrent != null) {

                output += "[" + crunchifyCurrent.getData().toString() + "]";
                crunchifyCurrent = crunchifyCurrent.getNext();
            }

        }
        return output;
    }

    public int size() {
        return size;
    }


    public boolean remove(int index) {

        // if the index is out of range, exit
        if (index < 1 || index > size())
            return false;

        Node crunchifyCurrent = head;
        if (head != null) {
            for (int i = 0; i < index; i++) {


                crunchifyCurrent = crunchifyCurrent.getNext();
            }
            crunchifyCurrent.setNext(crunchifyCurrent.getNext().getNext());

            // decrement the number of elements variable
            size--;

            return true;
        }
        return false;
    }
}
