package com.jc.jpathymeleaf.config;

import com.jc.jpathymeleaf.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/css/**","/js/**","/fontawesome-free-5.15.1-web/**","/sweet-alert/**","/image/**","/webjars/jquery/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/register/save").permitAll()
                .antMatchers("/package/list").permitAll()
                .antMatchers("/package/ver/**").permitAll()
                .antMatchers("/agent/**").hasAnyRole("ADMINISTRADOR")
                .antMatchers("/city/**").hasAnyRole("ADMINISTRADOR")
                .antMatchers("/admin/**").hasAnyRole("ADMINISTRADOR")
                .antMatchers("/customer/mis-paquetes").hasAnyRole("CLIENTE")
                .antMatchers("/customer/list").hasAnyRole("AGENTE","ADMINISTRADOR")
                .antMatchers("/customer/package/**").hasAnyRole("AGENTE", "ADMINISTRADOR")
                .antMatchers("/customer/**").hasAnyRole("ADMINISTRADOR")
                .antMatchers("/manager/**").hasAnyRole("ADMINISTRADOR")
                .antMatchers("/package/**").hasAnyRole("ADMINISTRADOR","GERENTE")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
//                .and()
//                    .exceptionHandling()
//                    .accessDeniedPage("/error_403");
    }
}
