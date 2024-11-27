package com.openclassrooms.backend.repository;

import com.openclassrooms.backend.model.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {

}
