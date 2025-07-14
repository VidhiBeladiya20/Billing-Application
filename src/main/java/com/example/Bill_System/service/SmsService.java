package com.example.Bill_System.service;

import com.example.Bill_System.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private final TwilioConfig twilioConfig;
    @Autowired
    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }
    public void sendWhatsApp(String toWhatsAppNumber, String messageBody) {
        Message.creator(
                new PhoneNumber("whatsapp:" + toWhatsAppNumber), //  WhatsApp prefix
                new PhoneNumber("whatsapp:" + twilioConfig.getFromPhoneNumber()), // Twilio sandbox number
                messageBody
        ).create();
    }
}
