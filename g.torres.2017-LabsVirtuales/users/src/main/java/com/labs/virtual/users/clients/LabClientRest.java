package com.labs.virtual.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="labs")
public interface LabClientRest {

    @DeleteMapping("/eliminar-lab-user/{id}")
    public void eliminaLabUserPorId(@PathVariable Long id);
}
