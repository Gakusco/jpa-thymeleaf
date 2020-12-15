package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Staff;

import java.util.List;

public interface StaffService {

    Staff save(Staff stuff);
    Staff findById(int id);
    List<Staff> findAll();
    void deleteById(int id);
}
