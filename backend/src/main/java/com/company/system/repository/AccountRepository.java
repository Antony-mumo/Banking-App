package com.company.system.repository;

import com.company.system.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Additional custom queries can be added here if needed

    List<Account> findByCustomerId(Long customerId); // Example of custom query to find accounts by customer ID
}
