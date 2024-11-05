package com.openclassrooms.backend.service;

import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.model.MessageDTO;
import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.model.modelMapper.MessageMapper;
import com.openclassrooms.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private RentalService rentalService;

  @Autowired
  private UserService userService;

  @Autowired
  private MessageMapper messageMapper;

  public MessageDTO createMessage(int rentalId, int userId, String message) {

    Message newMessage = new Message();

    Optional<Rental> rental = rentalService.findById(rentalId);
    Optional<User> user = userService.findById(userId);
    System.out.println(rental);
    System.out.println(user);

    if (!rental.isPresent() || !user.isPresent()) {
      return null;
    }

    newMessage.setRental_id(rental.get());
    newMessage.setUser_id(user.get());
    newMessage.setMessage(message);
    newMessage.setCreated_at(LocalDateTime.now());

    return messageMapper.toMessageDTO(messageRepository.save(newMessage));

  }

  public Message saveMessage(Message message) {
    return messageRepository.save(message);
  }
}
