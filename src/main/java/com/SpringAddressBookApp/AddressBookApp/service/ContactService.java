package com.SpringAddressBookApp.AddressBookApp.service;

import com.SpringAddressBookApp.AddressBookApp.dto.ContactDTO;
import com.SpringAddressBookApp.AddressBookApp.model.Contact;
import com.SpringAddressBookApp.AddressBookApp.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    public ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact addContact(ContactDTO contactDTO) {
        Contact contact = new Contact(contactDTO.getName(), contactDTO.getPhone());
        return contactRepository.save(contact);
    }

    public Optional<Contact> updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id).map(contact -> {
            contact.setName(contactDTO.getName());
            contact.setPhone(contactDTO.getPhone());
            return contactRepository.save(contact);
        });
    }

    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}