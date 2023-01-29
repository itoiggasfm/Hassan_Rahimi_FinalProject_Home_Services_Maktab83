package com.maktabsharif.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Suggestions extends BaseEntity<Long>{
    @Id
    @Column(name = "Id", unique = true, nullable = false)
    private Long id;
    @Column(name = "suggestion")
    private String suggestion;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Order order;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id =id;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
