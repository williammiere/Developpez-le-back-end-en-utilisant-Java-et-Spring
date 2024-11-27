package com.openclassrooms.backend.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.dto.CreateRentalDTO;
import com.openclassrooms.backend.dto.RentalDTO;
import com.openclassrooms.backend.dto.RentalsResponseDTO;
import com.openclassrooms.backend.dto.UpdateRentalDTO;
import com.openclassrooms.backend.dto.UserDTO;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api")
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Autowired
  private UserService userService;

  @GetMapping("/rentals/{id}")
  public ResponseEntity<RentalDTO> getRental(@PathVariable int id) {

    RentalDTO rentalDTO = rentalService.findById(id);
    return ResponseEntity.ok(rentalDTO);
  }

  @GetMapping("/rentals")
  public ResponseEntity<RentalsResponseDTO> getRentals() {

    RentalsResponseDTO rentalsResponseDTO = new RentalsResponseDTO();
    RentalDTO[] rentals = rentalService.findAll();
    rentalsResponseDTO.setRentals(rentals);
    return ResponseEntity.ok(rentalsResponseDTO);
  }

  @GetMapping("/files/rentals/{param}")
  public ResponseEntity<Resource> getMethodName(@PathVariable String param) throws IOException {
    Resource image = rentalService.getPicture(param);
    String contentType = Files.probeContentType(image.getFile().toPath());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(image);
  }
  

  @PostMapping("/rentals")
  public ResponseEntity<RentalDTO> createRental(@Valid @ModelAttribute CreateRentalDTO createRentalDTO) throws IOException {

    UserDTO user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    RentalDTO rental = rentalService.createRental(user.getId(), createRentalDTO.getName(), createRentalDTO.getDescription(), createRentalDTO.getSurface(), createRentalDTO.getPrice(), createRentalDTO.getPicture());
    return ResponseEntity.ok(rental);

  }

  @PutMapping("/rentals/{id}")
  public ResponseEntity<RentalDTO> updateRental(@PathVariable int id, @Valid @ModelAttribute UpdateRentalDTO updateRentalDTO) {

    UserDTO user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    RentalDTO rental = rentalService.updateRental(id, user.getId(), updateRentalDTO.getName(), updateRentalDTO.getDescription(), updateRentalDTO.getSurface(), updateRentalDTO.getPrice());

    return ResponseEntity.ok(rental);

  }
}
