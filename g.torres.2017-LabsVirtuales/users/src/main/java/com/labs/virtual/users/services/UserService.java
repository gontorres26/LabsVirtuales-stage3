package com.labs.virtual.users.services;

import com.labs.virtual.users.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listar();
    Optional<User> porId(Long id);
    User guardar(User user);
    void eliminar(Long id);
    List<User> listarPorIDs(Iterable<Long> ids);

    Optional<User> porEmail (String email);
    boolean existePorEmail(String email);
}
