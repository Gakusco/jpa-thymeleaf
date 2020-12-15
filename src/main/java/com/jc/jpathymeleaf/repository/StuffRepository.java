package com.jc.jpathymeleaf.repository;

import com.jc.jpathymeleaf.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StuffRepository extends JpaRepository<Staff, Integer> {
}
