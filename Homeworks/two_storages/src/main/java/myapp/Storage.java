package myapp;

import java.util.Deque;
import java.util.LinkedList;

public abstract class Storage {
    private Deque<Order> storageQueue = new LinkedList<>();

    public void addVIPOrder(Order order) {
        storageQueue.addFirst(order);
    }

    public void addOrder(Order order) {
        storageQueue.add(order);
    }

    
    public void processOrder() {
        Order taked_order = storageQueue.pollFirst();
        String string = String.format("Заказ \"" + taked_order.getOrderName() + "\" был забран курьером для доставки.\n");
        System.out.println(string);
    }
}
