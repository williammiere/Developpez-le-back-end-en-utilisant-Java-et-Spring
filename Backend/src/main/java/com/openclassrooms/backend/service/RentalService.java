package com.openclassrooms.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Iterable<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(int id) {
        return rentalRepository.findById(id);
    }

    public Rental updateRental(Rental rental) {
        return rentalRepository.save(rental);
    }
    
}
