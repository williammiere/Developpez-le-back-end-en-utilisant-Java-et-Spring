package com.openclassrooms.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.backend.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
}
