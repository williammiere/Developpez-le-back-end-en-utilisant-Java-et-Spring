package com.openclassrooms.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.backend.model.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {
    
}
