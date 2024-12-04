package com.openclassrooms.backend.modelMapper;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.openclassrooms.backend.dto.RentalDTO;
import com.openclassrooms.backend.model.Rental;

@Component
public class RentalMapper {

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

  public RentalDTO[] toListRentalDTO(Rental[] rentals) {
    RentalDTO[] rentalsDTO = Arrays.stream(rentals)
      .map(this::toRentalDTO)
      .toArray(RentalDTO[]::new);

      return rentalsDTO;
  }

}
