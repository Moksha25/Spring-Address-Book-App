package com.SpringAddressBookApp.AddressBookApp.controller;

import com.SpringAddressBookApp.AddressBookApp.entity.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class AddressBookController {

    private final List<Contact> contactList = new ArrayList<>();
    private Long contactIdCounter = 1L; // Auto-incrementing ID

    // ✅ Get all contacts
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactList;
    }

    // ✅ Get contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return contact.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Add a new contact
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        contact.setId(contactIdCounter++);
        contactList.add(contact);
        return ResponseEntity.status(201).body(contact);
    }

    // ✅ Update an existing contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @RequestBody Contact updatedContact) {
        for (Contact contact : contactList) {
            if (contact.getId().equals(id)) {
                contact.setName(updatedContact.getName());
                contact.setPhone(updatedContact.getPhone());
                return ResponseEntity.ok(contact);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Delete a contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        boolean removed = contactList.removeIf(contact -> contact.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok("Contact with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
