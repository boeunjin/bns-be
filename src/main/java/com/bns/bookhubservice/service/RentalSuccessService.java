package com.bns.bookhubservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("rental_success")
public class RentalSuccessService {
    @Value("${slack.user_token}")
    private String token;
    @Value("${slack.bot_token}")
    private String bot_token;

    public void successMessage(){

    }


}
