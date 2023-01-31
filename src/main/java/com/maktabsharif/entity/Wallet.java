package com.maktabsharif.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wallet")
public class Wallet extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "balance")
    private Double balance;
    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Transaction> transaction;


    @OneToOne(mappedBy = "wallet")
    private User user;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
