package com.maktabsharif.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "services")
public class Services extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "service_title")
    private String serviceTitle;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "base_price")
    private Double basePrice;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "services")
     List<User> user;

    @ManyToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "services_order",
            joinColumns = {@JoinColumn(name = "services_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
     List<Order> order;




    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}
