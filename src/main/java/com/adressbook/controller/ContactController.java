package com.adressbook.controller;

import com.adressbook.entity.Contact;
import com.adressbook.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/test")
    private Map<String, Object> test(){
        Map<String, Object> test = new HashMap<>();
        Contact contact = new Contact();
        contact.setName("Laza");
        contact.setEmail("rista007@gmail.com");
        test.put("contact", contact);
        test.put("image", "img");
        return test;
    }

    @PostMapping("/contact")
    private ResponseEntity<Object> addContact(@RequestBody Map<String, Object>inputMap){
        Contact contact = objectMapper.convertValue(inputMap.get("contact"), Contact.class);
        byte[] image = Base64.getDecoder().decode((String)inputMap.get("image"));
        contact.setImage(image);
        contactService.addContact(contact);
        return new ResponseEntity<>("sucessfully added contact", HttpStatus.CREATED);
    }

    @PutMapping("/contact")
    private ResponseEntity<Object> editContact(@RequestBody Map<String, Object>inputMap){
        Contact contact = objectMapper.convertValue(inputMap.get("contact"), Contact.class);
        byte[] image = Base64.getDecoder().decode((String)inputMap.get("image"));
        contact.setImage(image);
        contactService.editContact(contact);
        return new ResponseEntity<>("sucessfully edited contact", HttpStatus.CREATED);
    }
}
