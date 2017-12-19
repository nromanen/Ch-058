/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloWorldRestController {
    private UserService service;

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = "application/json")
    public ResponseEntity<String> sayHelloWorld() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @RequestMapping(value = "/users")
    public List<User> getAllUsers() {
        return service.getAll();
    }
}
