package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.Utils.Crud;
import com.jc.jpathymeleaf.model.Customer;
import com.jc.jpathymeleaf.model.Package;

import java.util.List;

public interface CustomerService extends Crud<Customer> {

    List<Package> findByPackageId(int id);
}
