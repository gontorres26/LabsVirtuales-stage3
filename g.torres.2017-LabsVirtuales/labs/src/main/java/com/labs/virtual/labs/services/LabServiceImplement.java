package com.labs.virtual.labs.services;

import com.labs.virtual.labs.clients.UserClientRest;
import com.labs.virtual.labs.models.entities.Lab;
import com.labs.virtual.labs.models.entities.LabUser;
import com.labs.virtual.labs.models.entities.User;
import com.labs.virtual.labs.repositories.LabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LabServiceImplement implements LabService {

    @Autowired
    private LabRepository repository;

    @Autowired
    private UserClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Lab> listar() {
        return (List<Lab>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lab> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Lab> porIdconUsuarios(Long id) {
        Optional<Lab> optionalLab = repository.findById(id);
        if (optionalLab.isPresent()){
            Lab lab = optionalLab.get();
            if (!lab.getLabUsers().isEmpty()){
                List<Long> ids = lab.getLabUsers().stream().map(labUser -> labUser.getUserID()).toList();
                List<User> users = client.obtenerListaUsers(ids);
                lab.setUsers(users);
            }
            return Optional.of(lab);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Lab guardar(Lab lab) {
        return repository.save(lab);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> asignarUser(User user, Long labID) {
        Optional<Lab> optionalLab = repository.findById(labID);
        if (optionalLab.isPresent()){
            User userMsvc = client.detalle(user.getId());

            Lab lab = optionalLab.get();
            LabUser labUser = new LabUser();
            labUser.setUserID(userMsvc.getId());

            lab.addLabUsuario(labUser);
            repository.save(lab);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> crearUser(User user, Long labID) {
        Optional<Lab> optionalLab = repository.findById(labID);
        if (optionalLab.isPresent()){
            User userNuevoMsvc = client.crear(user);

            Lab lab = optionalLab.get();
            LabUser labUser = new LabUser();
            labUser.setUserID(userNuevoMsvc.getId());

            lab.addLabUsuario(labUser);
            repository.save(lab);
            return Optional.of(userNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> eliminarUser(User user, Long labID) {
        Optional<Lab> optionalLab = repository.findById(labID);
        if (optionalLab.isPresent()){
            User userMsvc = client.detalle(user.getId());

            Lab lab = optionalLab.get();
            LabUser labUser = new LabUser();
            labUser.setUserID(userMsvc.getId());

            lab.removeLabUser(labUser);
            repository.save(lab);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarLabUserPorId(Long id) {
        repository.eliminarLabUserPorId(id);
    }
}
