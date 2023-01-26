package com.maktabsharif.configuration;

//import com.maktabsharif.entity.Account;
//import com.maktabsharif.entity.Transaction;
import com.maktabsharif.entity.BaseEntity;
import com.maktabsharif.entity.User;
//import com.maktabsharif.repository.Repository;
import com.maktabsharif.repository.UserRepository;

public class Db {

    private static UserRepository<User> userRepository = null;
//    private static Repository<Account> accountRepository = null;
//    private static Repository<Transaction> transactionRepository = null;

    public static UserRepository<User> getUserRepository() {
        if (userRepository == null) {
            synchronized (Db.class) {
                if (userRepository == null) {
                    userRepository = new UserRepository<User>("User", User.class);
                }
            }
        }
        return userRepository;
    }

//    public static Repository<Account> getAccountRepository() {
//        if (accountRepository == null) {
//            synchronized (Db.class) {
//                if (accountRepository == null) {
//                    accountRepository = new Repository<>();
//                }
//            }
//        }
//        return accountRepository;
//    }
//
//    public static Repository<Transaction> getTransactionRepository() {
//        if (transactionRepository == null) {
//            synchronized (Db.class) {
//                if (transactionRepository == null) {
//                    transactionRepository = new Repository<>();
//                }
//            }
//        }
//        return transactionRepository;
//    }
}
