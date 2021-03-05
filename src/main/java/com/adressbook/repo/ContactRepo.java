package com.adressbook.repo;

import com.adressbook.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {


    public List<Contact> findByName(String contactName);
}
