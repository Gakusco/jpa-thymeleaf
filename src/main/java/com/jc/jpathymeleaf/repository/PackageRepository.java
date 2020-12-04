package com.jc.jpathymeleaf.repository;

import com.jc.jpathymeleaf.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}
