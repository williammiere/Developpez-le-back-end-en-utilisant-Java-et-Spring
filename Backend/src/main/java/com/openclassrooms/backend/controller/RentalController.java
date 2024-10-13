package com.openclassrooms.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.service.RentalService;


@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals/{id}")
    public Optional<Rental> getRental(@RequestParam int id) {
        return rentalService.findById(id);
    }

    @GetMapping("/rentals")
    public Iterable<Rental> getRentals() {
        return rentalService.findAll();
    }
    
    @PutMapping("/rentals/{id}")
    public Rental updateRental(@RequestParam Rental rental) {
        return rentalService.updateRental(rental);
    }
    
}
