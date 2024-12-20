package com.openclassrooms.backend.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.repository.RentalRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalService {

    @Value("${pictures.upload.path}")
    private String picsUploadPath;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    public Rental[] findAll() {
        Iterable<Rental> rentals = rentalRepository.findAll();
        return StreamSupport.stream(rentals.spliterator(), false).toArray(Rental[]::new);
    }

    public Rental findById(int id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rental not found"));
        return rental;
    }

    public Rental createRental(int ownerId, String name, String description, float surface, float price, MultipartFile picture) throws IOException {
        Rental rental = new Rental();
        rental.setOwner_id(userService.findById(ownerId));
        rental.setName(name);
        rental.setDescription(description);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(this.savePicture(picture));

        return rentalRepository.save(rental);
    }

    public String savePicture(MultipartFile picture) throws IOException {
        Path destination = Paths.get(picsUploadPath);
        String generatedName = UUID.randomUUID().toString() + "-" + picture.getOriginalFilename(); // Generates a unique name for the picture to avoid conflicts

        Files.createDirectories(destination);

        Path targetLocation = destination.resolve(generatedName);
        Files.copy(picture.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); // Saves the picture in the destination folder

        return "api/files/rentals/" + generatedName;
    }

    public Resource getPicture(String fileName) throws MalformedURLException {
        Path filePath = Paths.get(picsUploadPath).resolve(fileName).normalize(); // Gets the path of the picture
        Resource resource = new UrlResource(filePath.toUri()); // Creates a resource from the path
        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new EntityNotFoundException("Picture not found");
        }
    }

    public Rental updateRental(int id, int owner_id, String name, String description, float surface, float price) {
        
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rental not found"));
        if (rental.getOwner_id().getId() != owner_id) {
            throw new IllegalArgumentException("You are not the owner of this rental");
        }

        rental.setOwner_id(userService.findById(owner_id));
        rental.setName(name);
        rental.setDescription(description);
        rental.setSurface(surface);
        rental.setPrice(price);

        return rentalRepository.save(rental);
    }
}