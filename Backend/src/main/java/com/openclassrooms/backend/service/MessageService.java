package com.openclassrooms.backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.repository.MessageRepository;

@Service
public class MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private RentalService rentalService;

  @Autowired
  private UserService userService;

  public Message createMessage(int rentalId, int userId, String message) {
    Rental rental = rentalService.findById(rentalId);
    User user =userService.findById(userId);

    Message newMessage = new Message();
    newMessage.setRental_id(rental);
    newMessage.setUser_id(user);
    newMessage.setMessage(message);
    newMessage.setCreated_at(LocalDateTime.now());

    return messageRepository.save(newMessage);
  }

  public Message saveMessage(Message message) {
    return messageRepository.save(message);
  }
}