package org.ingue.mall.posting.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.config.common.mapper.PostingMapper;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.PostingRepository;
import org.ingue.mall.posting.controller.domainResource.PostingResource;
import org.ingue.mall.posting.domain.Postings;
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

    private final PostingRepository postingRepository;
    private final PostingMapper postingMapper;

    @PostMapping
    public ResponseEntity createPosting(@RequestBody @Valid PostingDto postingDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Postings postings = postingMapper.mappingDto(postingDto);
        Postings newPostings = postingRepository.save(postings);

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(newPostings.getPostingId());
        URI createdUri = selfLinkBuilder.toUri();

        PostingResource postingResource = new PostingResource(newPostings);
        postingResource.add(linkTo(PostingController.class).withRel("query-postings"));
        postingResource.add(selfLinkBuilder.withRel("update-postings"));

        return ResponseEntity.created(createdUri).body(postingResource);
    }

    @GetMapping
    public ResponseEntity findOnePosting(@RequestParam Long postingId) {
        Optional<Postings> optional = postingRepository.findById(postingId);

        if(!optional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Postings postings = optional.get();

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(postings.getPostingId());

        PostingResource postingResource = new PostingResource(postings);
        postingResource.add(linkTo(PostingController.class).withRel("query-postings"));
        postingResource.add(selfLinkBuilder.withRel("update-postings"));

        return ResponseEntity.ok().body(postingResource);
    }

    private Postings mappingPostings(@Valid @RequestBody PostingDto postingDto) {
        return Postings.builder()
                .postingTitle(postingDto.getPostingTitle())
                .postingContent(postingDto.getPostingContent())
                .build();
    }
}