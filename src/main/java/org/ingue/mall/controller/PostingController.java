package org.ingue.mall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class PostingController {

    @PostMapping("/api/postings")
    public ResponseEntity createPosting() {

        URI createdUri = linkTo(methodOn(PostingController.class).createPosting()).slash("{postingId}").toUri();

        return ResponseEntity.created(createdUri).build();
    }
}
