package com.maktabsharif.repository;

import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.Wallet;

public class WalletRepository extends BaseRepository<Wallet> {
    public WalletRepository() {
        super("Wallet", Wallet.class);
    }

}
