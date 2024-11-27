package com.openclassrooms.backend.modelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.backend.dto.MessageDTO;
import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.service.RentalService;
import com.openclassrooms.backend.service.UserService;

@Component
public class MessageMapper {

  @Autowired
  private RentalService rentalService;

  @Autowired
  private UserService userService;

  @Autowired
  private RentalMapper rentalMapper;

  @Autowired
  private UserMapper userMapper;

  public MessageDTO toMessageDTO(Message message) {
    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setId(message.getId());
    messageDTO.setRental_id(message.getRental_id().getId());
    messageDTO.setUser_id(message.getUser_id().getId());
    messageDTO.setMessage(message.getMessage());
    messageDTO.setCreated_at(message.getCreated_at());
    messageDTO.setUpdated_at(message.getUpdated_at());
    return messageDTO;
  }

  public Message toMessage(MessageDTO messageDTO) {
    Message message = new Message();
    message.setId(messageDTO.getId());
    message.setRental_id(rentalMapper.toRental(rentalService.findById(messageDTO.getRental_id())));
    message.setUser_id(userMapper.toUser(userService.findById(messageDTO.getUser_id())));
    message.setMessage(messageDTO.getMessage());
    message.setCreated_at(messageDTO.getCreated_at());
    message.setUpdated_at(messageDTO.getUpdated_at());
    return message;
  }
}
