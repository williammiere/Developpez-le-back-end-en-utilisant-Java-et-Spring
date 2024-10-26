package com.openclassrooms.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.RentalDTO;
import com.openclassrooms.backend.model.modelMapper.RentalMapper;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;


@RestController
@CrossOrigin
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/rentals/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable int id) {
        Optional<Rental> rental = rentalService.findById(id);
        if (rental.isPresent()) {
            return ResponseEntity.ok(rentalMapper.toRentalDTO(rental.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/rentals")
    public ResponseEntity<List<RentalDTO>> getRentals() {
        return ResponseEntity.ok(rentalMapper.toListRentalDTO((List<Rental>) rentalService.findAll()));
    }

    @PostMapping("/rentals")
    public ResponseEntity<RentalDTO> createRental(@RequestParam("owner_id") int ownerId, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("surface") float surface, @RequestParam("price") float price, @RequestParam("picture") MultipartFile picture) {
        Rental rental = new Rental();
        rental.setOwner_id(userService.findById(ownerId).orElse(null));
        rental.setName(name);
        rental.setDescription(description);
        rental.setSurface(surface);
        rental.setPrice(price);

        if(!picture.isEmpty()) {
            rental.setPicture(rentalService.savePicture(picture));
        }
        
        return ResponseEntity.ok(rentalMapper.toRentalDTO(rentalService.save(rental)));
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
