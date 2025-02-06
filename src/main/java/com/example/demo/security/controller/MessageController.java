package com.example.demo.security.controller;


import com.example.demo.security.dtos.ResponseMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @GetMapping("/user-message")
    @PreAuthorize("hasAuthority('ROLE_ASPIRANTE')")
    public ResponseEntity<ResponseMessageDto> userMessage() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol usuario"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin-message")
    public ResponseEntity<ResponseMessageDto> adminMessage() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol admin"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_EGRESADO')")
    @GetMapping("/egresado-message")
    public ResponseEntity<ResponseMessageDto> egresadoMessage() {
        return new ResponseEntity<>(new ResponseMessageDto("El usuario tiene el rol egresado"), HttpStatus.OK);
    }


}
