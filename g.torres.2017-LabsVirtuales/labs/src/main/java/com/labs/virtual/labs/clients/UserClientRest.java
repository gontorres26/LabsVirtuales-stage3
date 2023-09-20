package com.labs.virtual.labs.clients;

import com.labs.virtual.labs.models.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient (name="users")
public interface UserClientRest {

    @GetMapping("/{id}")
    public User detalle(@PathVariable Long id);

    @PostMapping("/")
    public User crear(@RequestBody User user);

    @GetMapping("/users-por-lab")
    public List<User> obtenerListaUsers(@RequestParam Iterable<Long> ids);
}
