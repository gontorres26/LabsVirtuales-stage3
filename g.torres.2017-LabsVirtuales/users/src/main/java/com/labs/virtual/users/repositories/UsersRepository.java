package com.labs.virtual.users.repositories;

import com.labs.virtual.users.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email=?1")
    Optional<User> porEmail(String email);

    boolean existsByEmail(String email);
}
