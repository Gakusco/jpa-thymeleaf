package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Package;
import com.jc.jpathymeleaf.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService{

    @Autowired
    PackageRepository packageRepository;

    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> findById(int id) {
        return packageRepository.findById(id);
    }

    @Override
    public Package save(Package pack) {
        return packageRepository.save(pack);
    }

    @Override
    public void deleteById(int id) {
        packageRepository.deleteById(id);
    }
}
