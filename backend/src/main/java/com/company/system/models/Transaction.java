package com.company.system.models;

import javax.persistence.*;

@Entity
public class Transaction {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId; // Unique transaction ID
    // other attributes
    // @ManyToOne relationship with Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}