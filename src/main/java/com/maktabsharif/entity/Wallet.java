package com.maktabsharif.entity;

import javax.persistence.*;

@Entity
@Table(name = "wallet")

public class Wallet extends BaseEntity<Long>{

    @Id
    @Column(name = "Id", unique = true, nullable = false)
    Long id;

    @Column(name = "credit")
    Double credit;
//    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    Transaction transaction;

    @OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    User user;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

//    public Transaction getTransaction() {
//        return transaction;
//    }
//
//    public void setTransaction(Transaction transaction) {
//        this.transaction = transaction;
//    }


}
