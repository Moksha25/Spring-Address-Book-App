package com.SpringAddressBookApp.AddressBookApp.repository;

import com.SpringAddressBookApp.AddressBookApp.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}