package com.shrralis.ssdemo1.controller;


import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/connect")
public class SocialController extends ConnectController {

    public SocialController(ConnectionFactoryLocator connectionFactoryLocator,
                            ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected String connectedView(String providerId) {
        return "redirect:http://localhost:8081/";
    }

}
