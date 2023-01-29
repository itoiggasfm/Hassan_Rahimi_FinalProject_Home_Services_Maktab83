package com.maktabsharif.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Services extends BaseEntity<Long>{

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    private Long id;
    @Column(name = "service_title")
    private String serviceTitle;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "base_price")
    private Double basePrice;
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
     List<User> user;



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
}
