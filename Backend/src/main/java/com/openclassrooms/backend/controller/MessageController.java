package com.openclassrooms.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public Iterable<Message> getMessages() {
        return messageService.findAll();
    }
}
