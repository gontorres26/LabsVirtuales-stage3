package com.labs.virtual.labs.repositories;

import com.labs.virtual.labs.models.entities.Lab;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LabRepository extends CrudRepository<Lab, Long> {

    @Modifying
    @Query("delete from LabUser lu where lu.userID=?1")
    public void eliminarLabUserPorId(Long id);
}
