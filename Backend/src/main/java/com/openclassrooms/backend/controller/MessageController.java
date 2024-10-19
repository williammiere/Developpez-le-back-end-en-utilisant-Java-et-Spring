package com.openclassrooms.backend.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.model.Rental;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.service.MessageService;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    @GetMapping("/messages")
    public ResponseEntity<Iterable<Message>> getMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestParam("rental_id") int rentalId, @RequestParam("user_id") int userId, @RequestParam("message") String message) {
        
        Message newMessage = new Message();

        Optional<Rental> rental = rentalService.findById(rentalId);
        Optional<User> user = userService.findById(userId);
        System.out.println(rental);
        System.out.println(user);

        if (!rental.isPresent() || !user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        newMessage.setRental_id(rental.get());
        newMessage.setUser_id(user.get());
        newMessage.setMessage(message);
        newMessage.setCreated_at(LocalDateTime.now());

        return ResponseEntity.ok(messageService.saveMessage(newMessage));
    }
}
