package com.openclassrooms.backend.controller;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.RentalDTO;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.model.modelMapper.RentalMapper;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    Optional<User> user = userService.findByEmail(email);

    if (user.isPresent()) {

      return ResponseEntity.ok(rentalService.createRental(ownerId, name, description, surface, price, picture));

    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/rentals/{id}")
  public ResponseEntity<Boolean> updateRental(@RequestParam RentalDTO rental, @PathVariable int id) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    String email = authentication.getName();

    Optional<User> user = userService.findByEmail(email);

    if (user.isPresent()) {

      if (rentalService.updateRental(rentalMapper.toRental(rental), id)) {
        return ResponseEntity.ok(true);
      }
    }
    return ResponseEntity.notFound().build();

  }
}
