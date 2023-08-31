package com.company.system.services;

import com.company.system.models.Transaction;
import com.company.system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            // Update the existing transaction with the data from updatedTransaction
            // Implement the update logic here
            return transactionRepository.save(existingTransaction);
        }
        return null; // Transaction not found
    }

    public boolean deleteTransaction(Long id) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            transactionRepository.delete(existingTransaction);
            return true;
        }
        return false; // Transaction not found
    }
}

