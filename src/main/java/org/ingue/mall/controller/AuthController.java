package org.ingue.mall.controller;

import org.ingue.mall.pojo.AuthenticationRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody AuthenticationRequest authenticationRequest, HttpSession httpSession) {
        System.out.println(authenticationRequest.getUserId());
        System.out.println(authenticationRequest.getUserPassword());

        return true;
    }
}
