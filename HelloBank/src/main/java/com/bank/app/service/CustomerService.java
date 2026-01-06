package com.bank.app.service;

import com.bank.app.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer updateExistingCustomer(Customer customer, Long customerId);

    String deleteExistingCustomer(Customer customer, Long customerId);
}
