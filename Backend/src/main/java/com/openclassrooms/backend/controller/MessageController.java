package com.openclassrooms.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.dto.CreateMessageDTO;
import com.openclassrooms.backend.dto.MessageResponseDTO;
import com.openclassrooms.backend.dto.UserDTO;
import com.openclassrooms.backend.model.Message;
import com.openclassrooms.backend.modelMapper.UserMapper;
import com.openclassrooms.backend.service.MessageService;
import com.openclassrooms.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;


@RestController
@RequestMapping("api")
public class MessageController {

  @Autowired
  private MessageService messageService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Operation(summary = "Create a new message")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Message created"),
          @ApiResponse(responseCode = "400", description = "Bad request")
  })
  @PostMapping("/messages")
  public ResponseEntity<MessageResponseDTO> createMessage(@Valid @RequestBody CreateMessageDTO createMessageDTO) {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));

    Message message = messageService.createMessage(createMessageDTO.getRental_id(), user.getId(), createMessageDTO.getMessage());

    MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
    messageResponseDTO.setMessage(message.getMessage());

    return ResponseEntity.ok(messageResponseDTO);
  }
}
