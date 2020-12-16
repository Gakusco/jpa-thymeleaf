package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Staff;
import com.jc.jpathymeleaf.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public Staff save(Staff stuff) {
        return staffRepository.save(stuff);
    }

    @Override
    public Optional<Staff> findById(int id) {
        return staffRepository.findById(id);
    }

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        staffRepository.deleteById(id);
    }
}
