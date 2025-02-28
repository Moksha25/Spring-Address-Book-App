package com.SpringAddressBookApp.AddressBookApp.service;

import com.SpringAddressBookApp.AddressBookApp.dto.ContactDTO;
import com.SpringAddressBookApp.AddressBookApp.exception.ContactNotFoundException;
import com.SpringAddressBookApp.AddressBookApp.model.Contact;
import com.SpringAddressBookApp.AddressBookApp.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    public Contact addContact(ContactDTO contactDTO) {
        Contact contact = new Contact(contactDTO.getName(), contactDTO.getPhone());
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));

        contact.setName(contactDTO.getName());
        contact.setPhone(contactDTO.getPhone());
        return contactRepository.save(contact);
    }

    public boolean deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ContactNotFoundException(id);
        }
        contactRepository.deleteById(id);
        return false;
    }