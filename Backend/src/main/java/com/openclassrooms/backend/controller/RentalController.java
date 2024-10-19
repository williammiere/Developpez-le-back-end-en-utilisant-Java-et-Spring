package com.openclassrooms.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.service.RentalService;


@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable int id) {
        Optional<Rental> rental = rentalService.findById(id);
        if (rental.isPresent()) {
            return ResponseEntity.ok(rental.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/rentals")
    public ResponseEntity<Iterable<Rental>> getRentals() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @PostMapping("/rentals")
    public ResponseEntity<Rental> createRental(@RequestParam("owner_id") int ownerId, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("surface") float surface, @RequestParam("price") float price, @RequestParam("picture") MultipartFile picture) {
        Rental rental = new Rental();
        rental.setOwner_id(ownerId);
        rental.setName(name);
        rental.setDescription(description);
        rental.setSurface(surface);
        rental.setPrice(price);

        if(!picture.isEmpty()) {
            rental.setPicture(rentalService.savePicture(picture));
        }
        
        return ResponseEntity.ok(rentalService.save(rental));
    }
    
    @PutMapping("/rentals/{id}")
    public ResponseEntity<Boolean> updateRental(@RequestParam Rental rental, @PathVariable int id){ {
        if(rentalService.updateRental(rental, id)){
        return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
        }
    }
}
