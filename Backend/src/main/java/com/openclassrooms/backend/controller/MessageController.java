package com.openclassrooms.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.MessageDTO;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.service.MessageService;
import com.openclassrooms.backend.service.UserService;

@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> createMessage(@RequestParam("rental_id") int rentalId, @RequestParam("user_id") int userId, @RequestParam("message") String message) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		String email = authentication.getName();

        Optional<User> user = userService.findByEmail(email);

        MessageDTO messageDTO = null;

        if(user.isPresent()) {
            messageDTO = messageService.createMessage(rentalId, user.get().getId(), message);
        }

        if(messageDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(messageDTO);
    }
}
