package com.maktabsharif.repository;

import com.maktabsharif.entity.Transaction;

public class TransactionRepository extends BaseRepository<Transaction> {
    public TransactionRepository() {
        super("Transaction", Transaction.class);
    }
}
