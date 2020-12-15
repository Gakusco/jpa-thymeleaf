package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Staff;
import com.jc.jpathymeleaf.repository.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StuffRepository stuffRepository;

    @Override
    public Staff save(Staff stuff) {
        return stuffRepository.save(stuff);
    }

    @Override
    public Optional<Staff> findById(int id) {
        return stuffRepository.findById(id);
    }

    @Override
    public List<Staff> findAll() {
        return stuffRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        stuffRepository.deleteById(id);
    }
}
