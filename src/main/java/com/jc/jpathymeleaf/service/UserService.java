package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    User findById(int id);

    void deletById(int id);
}
