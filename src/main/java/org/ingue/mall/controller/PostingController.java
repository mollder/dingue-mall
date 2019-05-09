package org.ingue.mall.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.Postings;
import org.ingue.mall.domain.dto.PostingsDto;
import org.ingue.mall.repository.PostingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/postings", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class PostingController {

    private final PostingsRepository postingsRepository;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity createPosting(@RequestBody @Valid PostingsDto postingsDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Postings postings = modelMapper.map(postingsDto, Postings.class);
        Postings newPostings = postingsRepository.save(postings);

        URI createdUri = linkTo(PostingController.class).slash(newPostings.getPostingId()).toUri();

        return ResponseEntity.created(createdUri).body(postings);
    }
}
