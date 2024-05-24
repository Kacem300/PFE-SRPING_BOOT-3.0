package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDao extends JpaRepository<ContactForm, Long> {
}
