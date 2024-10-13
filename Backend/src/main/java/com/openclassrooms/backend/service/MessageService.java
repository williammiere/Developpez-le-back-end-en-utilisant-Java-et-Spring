package com.openclassrooms.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.repository.MessageRepository;


@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public Iterable<Message> findAll() {
        return messageRepository.findAll();
    }
}
