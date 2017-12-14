package com.shrralis.ssdemo1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldRestController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> sayHelloWorld() {
        return new ResponseEntity<String>("Hello World", HttpStatus.OK);
    }
}
