package com.jc.jpathymeleaf.service;

import com.jc.jpathymeleaf.model.Authority;
import com.jc.jpathymeleaf.model.User;
import com.jc.jpathymeleaf.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deletById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user == null) {
            logger.error("Error login: el usuario no existe");
            throw new UsernameNotFoundException("Error, verifique sus credenciales");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Authority rol : user.getAuthorities() ){
            logger.info("ROl: "+rol.getAuthority());
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }

        if (authorities.isEmpty()) {
            logger.error("Error login: usuario no tiene permisos");
            throw new UsernameNotFoundException("Error con el usuario, verifique sus permisos");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled(), true, true, true, authorities);
    }
}
