package com.labs.virtual.labs.services;

import com.labs.virtual.labs.models.entities.Lab;
import com.labs.virtual.labs.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface LabService {
    List<Lab> listar();
    Optional<Lab> porId(Long id);
    Optional<Lab> porIdconUsuarios(Long id);
    Lab guardar(Lab lab);
    void eliminar(Long id);
    void eliminarLabUserPorId(Long id);

    Optional<User> asignarUser(User user, Long labID);
    Optional<User> crearUser (User user, Long labID);
    Optional<User> eliminarUser (User user, Long labID);
}
