package com.rebel.BlogAPIv2.services;


import com.rebel.BlogAPIv2.enitities.Email.EmailDetails;

public interface EmailService
{

    // To send a simple email
    String sendSimpleMail(EmailDetails details);


    // To send an email with attachment
    String  sendMailWithAttachment(EmailDetails details);



}
