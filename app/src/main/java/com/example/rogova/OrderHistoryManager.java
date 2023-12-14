package com.example.rogova;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryManager {
    private static OrderHistoryManager instance;
    private static List<Order> orderHistory = new ArrayList<>();

    private OrderHistoryManager() {
        orderHistory = new ArrayList<>();
    }

    public static OrderHistoryManager getInstance() {
        if (instance == null) {
            instance = new OrderHistoryManager();
        }
        return instance;
    }

    public static List<Order> getOrderHistory() {
        return orderHistory;
    }

    public static void addOrder(Order order) {
        orderHistory.add(order);
    }
}