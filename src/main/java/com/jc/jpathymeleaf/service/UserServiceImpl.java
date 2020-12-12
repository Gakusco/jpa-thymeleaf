package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deletById(int id) {
        userRepository.deleteById(id);
    }
}
