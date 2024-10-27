package com.openclassrooms.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.RentalDTO;
import com.openclassrooms.backend.model.modelMapper.RentalMapper;
import com.openclassrooms.backend.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RentalMapper rentalMapper;
    
    public Iterable<Rental> findAll() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findById(int id) {
        return rentalRepository.findById(id);
    }

    public RentalDTO createRental(int ownerId, String name, String description, float surface, float price, MultipartFile picture) {
        
        Rental rental = new Rental();
        rental.setOwner_id(userService.findById(ownerId).orElse(null));
        rental.setName(name);
        rental.setDescription(description);
        rental.setSurface(surface);
        rental.setPrice(price);

        if(!picture.isEmpty()) {
            rental.setPicture(this.savePicture(picture));
        }
        
        return rentalMapper.toRentalDTO(rentalRepository.save(rental));
    }

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public String savePicture(MultipartFile picture) {

        Path destination = Paths.get("src/images/");
        String generatedName = picture.getOriginalFilename() + "-" + UUID.randomUUID().toString();
    try {

      Path targetLocation = destination.resolve(generatedName);
      Files.copy(picture.getInputStream(), targetLocation);

      return "http://localhost:8080/images/"+generatedName;

    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + generatedName, e);
    }

    }

    public Boolean updateRental(Rental rental, int id) {
        Optional<Rental> rentalData = rentalRepository.findById(id);
        if (rentalData.isPresent()) {
            rentalRepository.save(rental);
            return true;
        }
        return false;
    }
    
}
