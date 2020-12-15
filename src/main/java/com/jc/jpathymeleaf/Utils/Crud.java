package com.jc.jpathymeleaf.Utils;

import com.jc.jpathymeleaf.model.Authority;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {

    T save(T authority);
    Optional<T> findById(int id);
    List<T> findAll();
    void deleteById(int id);
}
