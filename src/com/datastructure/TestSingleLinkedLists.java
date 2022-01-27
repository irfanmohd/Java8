package com.datastructure;

import java.util.LinkedList;

public class TestSingleLinkedLists {

    public static void main(String[] args) {

        CustomerLinkedList list = new CustomerLinkedList();
		/*list.addFirst(3);
		list.addFirst(9);
		list.addFirst(2);
		System.out.println(list);
*/
        list.add(30);
        list.add(40);
        list.add(87);
        System.out.println("Before adding--->" + list);
		
		
		/*LinkedList<Integer> linked = new LinkedList<>();
		linked.add(10);
		linked.add(20);
		linked.add(30);
		System.out.println("linked --->"+linked);*/
    }

}
