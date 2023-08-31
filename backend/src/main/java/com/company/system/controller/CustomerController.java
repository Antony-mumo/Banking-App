package com.company.system.controller;

import com.company.system.models.Customer;
import com.company.system.services.CustomerService; // Import your CustomerService
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // Import for autowiring
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService; // Autowire your CustomerService
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired // Constructor injection
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET endpoint to retrieve a customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        // Retrieve and return a customer by ID
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST endpoint to create a new customer
    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        logger.info("Creating customer: {}", customer);
        // Implementation
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }
}
