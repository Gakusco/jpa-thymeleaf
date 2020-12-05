package com.jc.jpathymeleaf.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String run;

    private Date birth;

/*    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customers")*/
    /*private List<Package> packages;*/

    public Customer() {
	/*packages = new ArrayList<>();*/
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
	
    /*public List<Package> getPackages(){
    	return packages;
    }

    public void addPackage(Package pack){
    	this.packages.add(pack);
    }*/
}
