package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();
    Customer save(Customer customer);
    Customer getById(int idCustomer);
    void deleteById(int id);
    List<Package> findByPackageId(int id);
}
