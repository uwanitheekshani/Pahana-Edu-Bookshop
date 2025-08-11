package com.example.pahanabillingsystem.service;

import com.example.pahanabillingsystem.model.Customer;

import java.util.List;

public interface CustomerService {

    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
}
