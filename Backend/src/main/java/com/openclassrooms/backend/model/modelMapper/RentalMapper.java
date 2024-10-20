package com.openclassrooms.backend.model.modelMapper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.RentalDTO;
import com.openclassrooms.backend.service.UserService;

@Component
public class RentalMapper {

    @Autowired
    private UserService userService;

    public RentalDTO toRentalDTO(Rental rental) {
        RentalDTO rentalDTO = new RentalDTO();
        rentalDTO.setId(rental.getId());
        rentalDTO.setOwner_id(rental.getOwner_id().getId());
        rentalDTO.setName(rental.getName());
        rentalDTO.setDescription(rental.getDescription());
        rentalDTO.setSurface(rental.getSurface());
        rentalDTO.setPrice(rental.getPrice());
        rentalDTO.setPicture(rental.getPicture());
        rentalDTO.setCreated_at(rental.getCreated_at());
        rentalDTO.setUpdated_at(rental.getUpdated_at());
        return rentalDTO;
    }

    public Rental toRental(RentalDTO rentalDTO) {
        Rental rental = new Rental();
        rental.setId(rentalDTO.getId());
        rental.setOwner_id(userService.findById(rentalDTO.getOwner_id()).orElse(null));
        rental.setName(rentalDTO.getName());
        rental.setDescription(rentalDTO.getDescription());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setCreated_at(rentalDTO.getCreated_at());
        rental.setUpdated_at(rentalDTO.getUpdated_at());
        return rental;
    }

    public Iterable<RentalDTO> toListRentalDTO(Iterable<Rental> rentals) {
        if (rentals == null) {
            return null;
        }
        
        Iterable<RentalDTO> res = new ArrayList<>();
        
        for (Rental rental : rentals) {
            res.iterator().next().equals(toRentalDTO(rental));
        }

        return res;
    }
    
}
