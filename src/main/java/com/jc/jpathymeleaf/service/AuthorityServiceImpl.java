package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;


    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Optional<Authority> findById(int id) {
        return authorityRepository.findById(id);
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
