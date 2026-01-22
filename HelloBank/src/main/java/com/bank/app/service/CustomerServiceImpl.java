package com.bank.app.service;

import com.bank.app.model.Customer;
import com.bank.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateExistingCustomer(Customer customer, Long customerId) {
        existsCustomerById(customerId);
        customer.setId(customerId);
        return customerRepository.save(customer);
    }


    @Override
    public String deleteExistingCustomer(Customer customer, Long customerId) {
        existsCustomerById(customerId);
        customerRepository.deleteById(customerId);
        return "Customer with id: " + customerId + " deleted successfully!";

    }

    private void existsCustomerById(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer with id: " + customerId + " not found!"));
    }

}
