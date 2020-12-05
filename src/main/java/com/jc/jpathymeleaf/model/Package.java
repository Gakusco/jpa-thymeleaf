package com.jc.jpathymeleaf.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="packages")
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    private String name;

    private String description;

    private String enable;

    /*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
    	name="travels",
	joinColumns = @JoinColumn(name = "id", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "id", nullable =  false)
    )*/
    /*private List<Customer> customers;*/

    public Package(){

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }
}
