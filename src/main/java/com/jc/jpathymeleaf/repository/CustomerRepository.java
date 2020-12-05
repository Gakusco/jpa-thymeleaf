package com.jc.jpathymeleaf.repository;

import java.util.List;

import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

/*
    List<Package> findByPackageById(int id);
*/
}
