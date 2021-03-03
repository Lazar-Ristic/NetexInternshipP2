package com.adressbook.service;

import com.adressbook.entity.Contact;
import com.adressbook.repo.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private ContactRepo contactRepo;

    public void addContact(Contact contact){
        contactRepo.save(contact);
    }

    public void editContact(Contact contact) {
        Contact contactFromDb =  contactRepo.findById(contact.getId()).orElseGet(null);
        contactFromDb.setEmail(contact.getEmail());
        contactFromDb.setName(contact.getName());
        contactFromDb.setImage(contact.getImage());
        contactRepo.save(contact);
    }
}
