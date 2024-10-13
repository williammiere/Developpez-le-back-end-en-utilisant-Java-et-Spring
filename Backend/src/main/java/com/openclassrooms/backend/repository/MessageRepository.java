package com.openclassrooms.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.backend.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {
    
}
