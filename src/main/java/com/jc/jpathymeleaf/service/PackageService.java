package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Package;

import java.util.List;

public interface PackageService {

    List<Package> findAll();
    Package findById(int id);
    Package save(Package pack);
    void delete(int id);
}
