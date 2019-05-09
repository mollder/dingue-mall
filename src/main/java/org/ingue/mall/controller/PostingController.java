package org.ingue.mall.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.Postings;
import org.ingue.mall.repository.PostingsRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/postings", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PostingController {

    private final PostingsRepository postingsRepository;

    @PostMapping
    public ResponseEntity createPosting(@RequestBody Postings postings) {
        Postings newPostings = postingsRepository.save(postings);

        URI createdUri = linkTo(PostingController.class).slash(newPostings.getPostingId()).toUri();

        return ResponseEntity.created(createdUri).body(postings);
    }
}
