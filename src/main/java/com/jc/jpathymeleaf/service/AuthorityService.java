package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Authority;

import java.util.List;

public interface AuthorityService {

    Authority save(Authority authority);
    Authority findById(int id);
    List<Authority> findAll();
    void deleteById(int id);
}
