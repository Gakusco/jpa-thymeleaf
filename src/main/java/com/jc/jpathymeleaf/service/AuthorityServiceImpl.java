package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;


    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority findById(int id) {
        return authorityRepository.getOne(id);
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        authorityRepository.deleteById(id);
    }
}
