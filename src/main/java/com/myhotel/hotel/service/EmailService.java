package com.myhotel.hotel.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service

public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailBookingConfirmation(String to, String bookingCode, String userName){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject("Booking Confirmation From MyHotel");
            helper.setText("Dear " + userName + ",\n\n"
                    + "Your booking with code: " + bookingCode + " has been confirmed.\n"
                    + "Thank you for choosing our service!\n"
                    + "Please check in the detail information in website!\n\n"
                    + "Best regards,\n MyHotel");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
