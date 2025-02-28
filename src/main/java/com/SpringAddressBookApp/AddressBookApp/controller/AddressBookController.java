package com.SpringAddressBookApp.AddressBookApp.controller;

import com.SpringAddressBookApp.AddressBookApp.dto.ContactDTO;
import com.SpringAddressBookApp.AddressBookApp.model.Contact;
import com.SpringAddressBookApp.AddressBookApp.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
@Slf4j
public class AddressBookController {

    private final ContactService contactService;

    public AddressBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        log.info("Received request to fetch all contacts");
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        log.info("Received request to fetch contact with ID: {}", id);
        Optional<Contact> contact = Optional.ofNullable(contactService.getContactById(id));
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Contact with ID: {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody @Valid ContactDTO contactDTO) {
        log.info("Received request to create a new contact: {}", contactDTO);
        Contact newContact = contactService.addContact(contactDTO);
        return ResponseEntity.status(201).body(newContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody @Valid ContactDTO contactDTO) {
        log.info("Received request to update contact with ID: {}", id);
        Contact updatedContact = contactService.updateContact(id, contactDTO);
        return ResponseEntity.ok(updatedContact);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        log.info("Received request to delete contact with ID: {}", id);
        boolean deleted = contactService.deleteContact(id);
        if (deleted) {
            log.info("Successfully deleted contact with ID: {}", id);
            return ResponseEntity.ok("Contact with ID " + id + " deleted successfully.");
        } else {
            log.warn("Contact with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
    }
