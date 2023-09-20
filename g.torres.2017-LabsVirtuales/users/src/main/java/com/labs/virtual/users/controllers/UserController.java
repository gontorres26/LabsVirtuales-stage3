package com.labs.virtual.users.controllers;

import com.labs.virtual.users.models.entities.User;
import com.labs.virtual.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public List<User> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<User> optionalUser = service.porId(id);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody User user, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        if (!user.getEmail().isEmpty() && service.existePorEmail(user.getEmail())){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("mensaje", "Ya existe un usuario con este email"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody User userUpdate, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }

        Optional<User> optionalUser = service.porId(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (!userUpdate.getEmail().isEmpty()
                    && !userUpdate.getEmail().equalsIgnoreCase(user.getEmail())
                    && service.porEmail(userUpdate.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("mensaje", "Ya existe un usuario con este email"));
            }

            user.setNombre(userUpdate.getNombre());
            user.setEmail(userUpdate.getEmail());
            user.setPassword(userUpdate.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<User> optionalUser = service.porId(id);
        if (optionalUser.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/users-por-lab")
    public ResponseEntity<?> obtenerListaUsers(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listarPorIDs(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
