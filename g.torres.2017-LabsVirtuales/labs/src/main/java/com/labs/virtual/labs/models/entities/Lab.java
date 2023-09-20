package com.labs.virtual.labs.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "labs")
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn (name = "lab_id")
    private List<LabUser> labUsers;

    @Transient
    private List<User> users;

    public Lab() {
        this.labUsers = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<LabUser> getLabUsers() {
        return labUsers;
    }

    public void setLabUsers(List<LabUser> labUsers) {
        this.labUsers = labUsers;
    }

    public void addLabUsuario(LabUser labuser){
        this.labUsers.add(labuser);
    }

    public void removeLabUser(LabUser labUser){
        this.labUsers.remove(labUser);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
