package com.maktabsharif.entity;

import com.maktabsharif.entity.enumeration.OrderStatus;

public class Order extends BaseEntity{
    String id;
    Double suggestedPrice;
    Long orderDate;
    String address;
    OrderStatus orderStatus;
    User user;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public Double getSuggestedPrice() {
        return suggestedPrice;
    }

    public void setSuggestedPrice(Double suggestedPrice) {
        this.suggestedPrice = suggestedPrice;
    }

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
