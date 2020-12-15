package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.City;
import com.jc.jpathymeleaf.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public City save(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(int id) {
        return cityRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        cityRepository.deleteById(id);
    }
}
