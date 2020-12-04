package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();
    Customer save(Customer customer);
    Customer getById(int id);
    void deleteById(int id);
}
