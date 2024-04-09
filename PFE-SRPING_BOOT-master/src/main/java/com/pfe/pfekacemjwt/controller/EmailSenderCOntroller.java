package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderCOntroller {
    @Autowired
    private EmailSenderService emailSenderService;
    public void sendEmail() {
        emailSenderService.sendEmail("kacem.benbrahim07@yahoo.com", "Test", "Test");
    }
}
