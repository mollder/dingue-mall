package org.ingue.mall.controller;

import org.ingue.mall.pojo.AuthenticationRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public boolean login(@RequestBody AuthenticationRequest authenticationRequest) {

        /*Authentication request = new UsernamePasswordAuthenticationToken(authenticationRequest.getUserId(), authenticationRequest.getUserPassword());
        Authentication result = am.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
        */

        return true;
    }
}
