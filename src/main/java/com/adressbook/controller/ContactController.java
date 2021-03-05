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

    @PostMapping("/contact")
    public ResponseEntity<Object> addContact(@RequestBody Map<String, Object>inputMap){
        Contact contact = objectMapper.convertValue(inputMap.get("contact"), Contact.class);
        byte[] image = Base64.getDecoder().decode((String)inputMap.get("image"));
        contact.setImage(image);
        contactService.addContact(contact);
        return new ResponseEntity<>("sucessfully added contact", HttpStatus.CREATED);
    }

    @PutMapping("/contact")
    public ResponseEntity<Object> editContact(@RequestBody Map<String, Object>inputMap){
        Contact contact = objectMapper.convertValue(inputMap.get("contact"), Contact.class);
        byte[] image = Base64.getDecoder().decode((String)inputMap.get("image"));
        contact.setImage(image);
        contactService.editContact(contact);
        return new ResponseEntity<>("sucessfully edited contact", HttpStatus.OK);
    }

    @DeleteMapping("/deleteContact/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable Long id){
        contactService.deleteContact(id);
        return new ResponseEntity<>("sucessfully deleted contact", HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<Object> findByName(@RequestParam String contactName){
        if (contactService.findByName(contactName) != null)
            return new ResponseEntity<>(contactService.findByName(contactName), HttpStatus.OK);
        else return new ResponseEntity<>("contact does not exist", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/exportToCSV")
    public ResponseEntity<Object> exportToCSV() throws IOException {
        contactService.exportToCSV();
        return new ResponseEntity<>("CSV created", HttpStatus.OK);
    }
}
