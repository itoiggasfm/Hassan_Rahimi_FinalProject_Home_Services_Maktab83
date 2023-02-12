package com.maktabsharif.entity;

import com.maktabsharif.entity.enumeration.OrderStatus;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "work_description")
    private String workDescription;
    @Column(name = "client_suggested_price")
    private Double clientSuggestedPrice;
    @Column(name = "order_date")
    private Timestamp orderDate;
    @Column(name = "start_date_by_client")
    private Timestamp startDateByClient;
    @Column(name = "address")
    private String address;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "comment")
    private String comment;
    @Column(name = "selected_suggestion_id")
    private Long selectedSuggestionId;


    @ManyToOne(cascade = CascadeType.MERGE/*, fetch = FetchType.LAZY*/)
    private User user;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "order")
    private List<Suggestions> suggestions;

    @ManyToOne/*( cascade = CascadeType.MERGE, fetch = FetchType.LAZY)*/
    Services services;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    public void setWorkDescription(String workDescription) {
        this.workDescription = workDescription;
    }

    public Double getClientSuggestedPrice() {
        return clientSuggestedPrice;
    }

    public void setClientSuggestedPrice(Double clientSuggestedPrice) {
        this.clientSuggestedPrice = clientSuggestedPrice;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public Timestamp getStartDateByClient() {
        return startDateByClient;
    }

    public void setStartDateByClient(Timestamp startDateByClient) {
        this.startDateByClient = startDateByClient;
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

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Long getSelectedSuggestionId() {
        return selectedSuggestionId;
    }

    public void setSelectedSuggestionId(Long selectedSuggestionId) {
        this.selectedSuggestionId = selectedSuggestionId;
    }
}
