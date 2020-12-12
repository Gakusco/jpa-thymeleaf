package com.jc.jpathymeleaf.repository;

import com.jc.jpathymeleaf.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
