package com.labs.virtual.labs.controllers;

import com.labs.virtual.labs.models.entities.Lab;
import com.labs.virtual.labs.models.entities.User;
import com.labs.virtual.labs.services.LabService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class LabController {

    @Autowired
    private LabService service;

    @GetMapping
    public ResponseEntity<List<Lab>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Lab> optionalLab = service.porIdconUsuarios(id);
        if (optionalLab.isPresent()){
            return ResponseEntity.ok(optionalLab.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Lab lab, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(lab));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Lab labUpdate, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Lab> optionalLab = service.porId(id);
        if (optionalLab.isPresent()){
            Lab lab = optionalLab.get();
            lab.setNombre(labUpdate.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(lab));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Lab> optionalLab = service.porId(id);
        if (optionalLab.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-user/{labID}")
    public ResponseEntity<?> asignarUser(@RequestBody User user, @PathVariable Long labID){
        Optional<User> optionalUser;
        try{
            optionalUser = service.asignarUser(user, labID);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe " +
                            "el usuario o error en la comunicacion: " + e.getMessage()));
        }
        if (optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-user/{labID}")
    public ResponseEntity<?> crearUser(@RequestBody User user, @PathVariable Long labID){
        Optional<User> optionalUser;
        try{
            optionalUser = service.crearUser(user, labID);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error en la comunicacion: " + e.getMessage()));
        }
        if (optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-user/{labID}")
    public ResponseEntity<?> eliminarUser(@RequestBody User user, @PathVariable Long labID){
        Optional<User> optionalUser;
        try{
            optionalUser = service.eliminarUser(user, labID);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe " +
                            "el usuario o error en la comunicacion: " + e.getMessage()));
        }
        if (optionalUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-lab-user/{id}")
    public ResponseEntity<?> eliminaLabUserPorId(@PathVariable Long id){
        service.eliminarLabUserPorId(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
