package org.ingue.mall.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.domain.Postings;
import org.ingue.mall.domain.domainResource.PostingsResource;
import org.ingue.mall.domain.dto.PostingsDto;
import org.ingue.mall.repository.PostingsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

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

        //Postings postings = modelMapper.map(postingsDto, Postings.class); // dto는 잘받아지는데 얘가 문제임 테스트 필요
        Postings postings = Postings.builder()
                .postingTitle(postingsDto.getPostingTitle())
                .postingContent(postingsDto.getPostingContent())
                .build();

        Postings newPostings = postingsRepository.save(postings);

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(newPostings.getPostingId());
        URI createdUri = selfLinkBuilder.toUri();

        PostingsResource postingsResource = new PostingsResource(newPostings);
        postingsResource.add(linkTo(PostingController.class).withRel("query-postings"));
        postingsResource.add(selfLinkBuilder.withRel("update-postings"));

        return ResponseEntity.created(createdUri).body(postingsResource);
    }

    @GetMapping
    public ResponseEntity findOnePosting(@RequestParam Long postingId) {
        Optional<Postings> optional = postingsRepository.findById(postingId);

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Postings postings = optional.get();

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(postings.getPostingId());

        PostingsResource postingsResource = new PostingsResource(postings);
        postingsResource.add(linkTo(PostingController.class).withRel("query-postings"));
        postingsResource.add(selfLinkBuilder.withRel("update-postings"));

        return ResponseEntity.ok().body(postingsResource);
    }
}