package com.jc.jpathymeleaf.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name="staff")
public class Staff {

    @Id
    @Column(name="id")
    private int id;

    @Size(min = 1, max =60)
    private String name;

    private String run;

    @NotNull(message = "debe ingresar su fecha de nacimiento")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @Valid
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id")
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

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
