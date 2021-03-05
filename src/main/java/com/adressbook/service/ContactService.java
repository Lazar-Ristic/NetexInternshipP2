package com.adressbook.service;

import com.adressbook.entity.Contact;
import com.adressbook.repo.ContactRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    public void deleteContact(Long id) {
        Contact contactFromDb =  contactRepo.findById(id).orElseGet(null);
        if(contactFromDb != null)
            contactRepo.delete(contactFromDb);
    }

    public List<Contact> findByName(String contactName) {
        List<Contact> contactList = contactRepo.findByName(contactName);
        return contactList;
    }

    public void exportToCSV() throws IOException {
        List<Contact> contactList = contactRepo.findAll();
        String[] headers = { "id", "Name", "Email"}; // "Image"
        FileWriter out = new FileWriter("C:\\Temp\\testCSV.csv");
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers))) {
            for (Contact contact:contactList) {
                List<String> values = new ArrayList<>();
                values.add(String.valueOf(contact.getId()));
                values.add(contact.getName());
                values.add(contact.getEmail());
//                in case if we want to add image in CSV file which is Base64 encoded just uncomment bellow code
//                String image = Base64.getEncoder().encodeToString(contact.getImage());
//                values.add(image);
                printer.printRecord(values);
            }
        }
    }
}
