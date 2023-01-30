package com.maktabsharif.entity;

import com.maktabsharif.entity.enumeration.OrderStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "client_suggested_price")
    private Double clientSuggestedPrice;
    @Column(name = "order_date")
    private Long orderDate;
    @Column(name = "order_Start-date_by_client")
    private Long orderStartDateByClient;
    @Column(name = "address")
    private String address;
    @Column(name = "expert_suggestion")
    private String expertSuggestion;
    @Column(name = "expert_suggestion_date")
    private Long expertSuggestionDate;
    @Column(name = "order_Start-date_by_expert")
    private Long orderStartDateByExpert;
    @Column(name = "expert_suggested_price")
    private Double expertSuggestedPrice;
    @Column(name = "order_do_duration")
    private Double orderDoDuration;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "comment")
    private String comment;
    @ManyToOne( cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Suggestions> suggestions;

    @ManyToMany(mappedBy = "order")
    List<Services> services;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getClientSuggestedPrice() {
        return clientSuggestedPrice;
    }

    public void setClientSuggestedPrice(Double clientSuggestedPrice) {
        this.clientSuggestedPrice = clientSuggestedPrice;
    }

    public Long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Long orderDate) {
        this.orderDate = orderDate;
    }

    public Long getOrderStartDateByClient() {
        return orderStartDateByClient;
    }

    public void setOrderStartDateByClient(Long orderStartDateByClient) {
        this.orderStartDateByClient = orderStartDateByClient;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpertSuggestion() {
        return expertSuggestion;
    }

    public void setExpertSuggestion(String expertSuggestion) {
        this.expertSuggestion = expertSuggestion;
    }

    public Long getExpertSuggestionDate() {
        return expertSuggestionDate;
    }

    public void setExpertSuggestionDate(Long expertSuggestionDate) {
        this.expertSuggestionDate = expertSuggestionDate;
    }

    public Long getOrderStartDateByExpert() {
        return orderStartDateByExpert;
    }

    public void setOrderStartDateByExpert(Long orderStartDateByExpert) {
        this.orderStartDateByExpert = orderStartDateByExpert;
    }

    public Double getExpertSuggestedPrice() {
        return expertSuggestedPrice;
    }

    public void setExpertSuggestedPrice(Double expertSuggestedPrice) {
        this.expertSuggestedPrice = expertSuggestedPrice;
    }

    public Double getOrderDoDuration() {
        return orderDoDuration;
    }

    public void setOrderDoDuration(Double orderDoDuration) {
        this.orderDoDuration = orderDoDuration;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Suggestions> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestions> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }
}
