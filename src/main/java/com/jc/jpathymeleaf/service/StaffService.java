package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.Utils.Crud;
import com.jc.jpathymeleaf.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StaffService extends Crud<Staff> {

    Page<Staff> findAllPage(Pageable pageable);

}
