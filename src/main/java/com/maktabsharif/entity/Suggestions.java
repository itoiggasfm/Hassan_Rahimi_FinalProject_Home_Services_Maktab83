package com.maktabsharif.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "suggestions")
public class Suggestions extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "expert_suggestion")
    private String expertSuggestion;
    @Column(name = "expert_suggested_price")
    private Double expertSuggestedPrice;
    @Column(name = "expert_suggestion_date")
    private Timestamp expertSuggestionDate;
    @Column(name = "start_date_by_expert")
    private Timestamp startDateByExpert;
    @Column(name = "order_do_duration")
    private Long orderDoDuration;
    @Column(name = "selected")
    private Boolean selecetd;

    @ManyToOne (cascade = CascadeType.MERGE/*, fetch = FetchType.LAZY*/)
    private User user;

    @ManyToOne( cascade = CascadeType.MERGE/*, fetch = FetchType.LAZY*/)
    private Order order;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id =id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getExpertSuggestion() {
        return expertSuggestion;
    }

    public void setExpertSuggestion(String expertSuggestion) {
        this.expertSuggestion = expertSuggestion;
    }

    public Timestamp getExpertSuggestionDate() {
        return expertSuggestionDate;
    }

    public void setExpertSuggestionDate(Timestamp expertSuggestionDate) {
        this.expertSuggestionDate = expertSuggestionDate;
    }

    public Timestamp getStartDateByExpert() {
        return startDateByExpert;
    }

    public void setStartDateByExpert(Timestamp startDateByExpert) {
        this.startDateByExpert = startDateByExpert;
    }

    public Long getOrderDoDuration() {
        return orderDoDuration;
    }

    public void setOrderDoDuration(Long orderDoDuration) {
        this.orderDoDuration = orderDoDuration;
    }

    public Double getExpertSuggestedPrice() {
        return expertSuggestedPrice;
    }

    public void setExpertSuggestedPrice(Double expertSuggestedPrice) {
        this.expertSuggestedPrice = expertSuggestedPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getSelecetd() {
        return selecetd;
    }

    public void setSelecetd(Boolean selecetd) {
        this.selecetd = selecetd;
    }
}
