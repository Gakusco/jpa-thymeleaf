package com.jc.jpathymeleaf.model;

import javax.persistence.*;

@Entity
@Table(name="staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String run;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user")
    private User user;

    public Staff(){}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
