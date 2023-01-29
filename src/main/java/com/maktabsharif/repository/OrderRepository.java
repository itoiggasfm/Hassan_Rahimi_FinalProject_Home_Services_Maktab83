package com.maktabsharif.repository;

import com.maktabsharif.entity.Order;

public class OrderRepository extends BaseRepository<Order> {
    public OrderRepository() {
        super("Order", Order.class);
    }
}
