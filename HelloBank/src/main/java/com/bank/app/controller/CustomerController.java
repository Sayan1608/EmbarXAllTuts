package com.bank.app.controller;

import com.bank.app.model.Customer;
import com.bank.app.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(path = "/api/bank")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // create new Customer
    @PostMapping(path = "/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    //get all customers
    @GetMapping(path = "/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> allCustomers = customerService.getAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(allCustomers);
    }

    //update existing customer
    @PutMapping(path = "/customers/{customerId}")
    public ResponseEntity<?> updateExistingCustomer(@RequestBody Customer customer,
                                                           @PathVariable Long customerId){
        try {
            Customer updatedCustomer = customerService.updateExistingCustomer(customer, customerId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
        } catch (ResponseStatusException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }
    }

    //delete existing customer
    @DeleteMapping(path = "/customers/{customerId}")
    public ResponseEntity<?> deleteExistingCustomer(@RequestBody Customer customer,
                                                    @PathVariable Long customerId){
        try {
            String status = customerService.deleteExistingCustomer(customer, customerId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }

    }

}
