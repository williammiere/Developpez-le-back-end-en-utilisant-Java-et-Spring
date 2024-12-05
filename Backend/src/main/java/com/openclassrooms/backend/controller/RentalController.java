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
import com.openclassrooms.backend.modelMapper.RentalMapper;
import com.openclassrooms.backend.modelMapper.UserMapper;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api")
public class RentalController {

  @Autowired
  private RentalService rentalService;

  @Autowired
  private UserService userService;

  @Autowired
  private RentalMapper rentalMapper;

  @Autowired
  private UserMapper userMapper;

  @Operation(summary = "Get rental by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Rental found"),
          @ApiResponse(responseCode = "404", description = "Rental not found")
  })
  @GetMapping("/rentals/{id}")
  public ResponseEntity<RentalDTO> getRental(@PathVariable int id) {

    RentalDTO rentalDTO = rentalMapper.toRentalDTO(rentalService.findById(id));
    return ResponseEntity.ok(rentalDTO);
  }

  @Operation(summary = "Get all rentals")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Rentals found"),
          @ApiResponse(responseCode = "404", description = "No rental found")
  })
  @GetMapping("/rentals")
  public ResponseEntity<RentalsResponseDTO> getRentals() {

    RentalsResponseDTO rentalsResponseDTO = new RentalsResponseDTO();
    RentalDTO[] rentals = rentalMapper.toListRentalDTO(rentalService.findAll());
    rentalsResponseDTO.setRentals(rentals);
    return ResponseEntity.ok(rentalsResponseDTO);
  }

  @Operation(summary = "Get rental picture by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Rental picture found"),
          @ApiResponse(responseCode = "404", description = "Rental picture not found")
  })
  @GetMapping("/files/rentals/{param}")
  public ResponseEntity<Resource> getPicture(@PathVariable String param) throws IOException {
    Resource image = rentalService.getPicture(param);
    String contentType = Files.probeContentType(image.getFile().toPath());
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .body(image);
  }
  
  @Operation(summary = "Create a new rental")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Rental created"),
          @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PostMapping("/rentals")
  public ResponseEntity<RentalDTO> createRental(@Valid @ModelAttribute CreateRentalDTO createRentalDTO) throws IOException {

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

    RentalDTO rental = rentalMapper.toRentalDTO(rentalService.createRental(user.getId(), createRentalDTO.getName(), createRentalDTO.getDescription(), createRentalDTO.getSurface(), createRentalDTO.getPrice(), createRentalDTO.getPicture()));
    return ResponseEntity.ok(rental);

  }

  @Operation(summary = "Update a rental")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Rental updated"),
          @ApiResponse(responseCode = "400", description = "Bad request"),
          @ApiResponse(responseCode = "404", description = "Rental not found")
  })
  @PutMapping("/rentals/{id}")
  public ResponseEntity<RentalDTO> updateRental(@PathVariable int id, @Valid @ModelAttribute UpdateRentalDTO updateRentalDTO) {

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

    RentalDTO rental = rentalMapper.toRentalDTO(rentalService.updateRental(id, user.getId(), updateRentalDTO.getName(), updateRentalDTO.getDescription(), updateRentalDTO.getSurface(), updateRentalDTO.getPrice()));

    return ResponseEntity.ok(rental);

  }
}
