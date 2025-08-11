package com.example.pahanabillingsystem.service.impl;

import com.example.pahanabillingsystem.dao.CustomerDAO;
import com.example.pahanabillingsystem.model.Customer;
import com.example.pahanabillingsystem.service.CustomerService;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private  CustomerDAO customerDAO = new CustomerDAO();

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    @Override
    public void addCustomer(Customer customer) {
        Customer newCustomer = new Customer();
        newCustomer.setAccountNumber(customer.getAccountNumber());
        newCustomer.setName(customer.getName());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setTelephone(customer.getTelephone());
         customerDAO.addCustomer(newCustomer);
    }

    @Override
    public void updateCustomer(Customer customer){
        Customer newCustomer = new Customer();

        newCustomer.setId(customer.getId());
        newCustomer.setAccountNumber(customer.getAccountNumber());
        newCustomer.setName(customer.getName());
        newCustomer.setAddress(customer.getAddress());
        newCustomer.setTelephone(customer.getTelephone());
        customerDAO.updateCustomer(newCustomer);
    }

    @Override
    public void deleteCustomer(int customerId){
         customerDAO.deleteCustomer(customerId);
    }

    @Override
    public Customer getCustomerById(int customerId){
        Customer customer = customerDAO.getCustomerById(customerId);
        if(customer != null){
            return customer;
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers(){
        return customerDAO.getAllCustomers();
    }



}
