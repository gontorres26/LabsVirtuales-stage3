package com.labs.virtual.users.services;

import com.labs.virtual.users.clients.LabClientRest;
import com.labs.virtual.users.models.entities.User;
import com.labs.virtual.users.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements UserService{

    @Autowired
    private UsersRepository repository;

    @Autowired
    private LabClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<User> listar() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public User guardar(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        client.eliminaLabUserPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listarPorIDs(Iterable<Long> ids) {
        return (List<User>) repository.findAllById(ids);
    }

    @Override
    public Optional<User> porEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
