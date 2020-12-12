package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.City;

import java.util.List;

public interface CityService {

    City save(City city);

    List<City> findAll();

    City findById(int id);

    void deleteById(int id);
}
