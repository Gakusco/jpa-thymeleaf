package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.Utils.Crud;
import com.jc.jpathymeleaf.model.User;

import java.util.List;

public interface UserService extends Crud<User> {

    List<User> findByAuthoritiesAuthority(String authority);
}
