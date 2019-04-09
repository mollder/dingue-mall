package org.ingue.mall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {

        return "success";
    }

    @RequestMapping(value = "/failure", method = RequestMethod.GET)
    public String failure() {

        return "failure";
    }
}
