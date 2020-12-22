package com.jc.jpathymeleaf.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@SessionAttributes(value = {"customer"})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Size(min = 2, max =20, message= "El tamaño del nombre debe estar entre 2 y 20")
    private String name;

    private String run;

    @NotNull(message = "Debe ingresar su fecha de nacimiento")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    private List<Package> packages;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private User user;

    public Customer() {
	    packages = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public List<Package> getPackages(){
    	return packages;
    }

    public void addPackage(Package pack){
    	this.packages.add(pack);
        pack.addCustomer(this);
    }

    public void removePackage(Package pack){
        this.packages.remove(pack);
        pack.removeCustomer(this);
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
