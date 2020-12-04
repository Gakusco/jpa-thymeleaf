package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(int id) {
        return customerRepository.getOne(id);
    }

    @Override
    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }
}
