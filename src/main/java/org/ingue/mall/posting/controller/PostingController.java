package org.ingue.mall.posting.controller;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.ingue.mall.config.common.mapper.PostingMapper;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.PostingRepository;
import org.ingue.mall.posting.controller.domainResource.PostingResource;
import org.ingue.mall.posting.domain.Postings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/postings",
        produces = MediaTypes.HAL_JSON_UTF8_VALUE
)
public class PostingController {

    private final PostingRepository postingRepository;
    private final PostingMapper postingMapper;

    @PostMapping
    public ResponseEntity createPosting(@RequestBody @Valid PostingDto postingDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Postings postings = postingMapper.mappingDto(postingDto);
        Postings newPostings = postingRepository.save(postings);

        PostingResource postingResource = new PostingResource(newPostings);
        postingResource.add(linkTo(PostingController.class).withRel("query-postings"));

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(newPostings.getPostingId());
        URI createdUri = selfLinkBuilder.toUri();

        postingResource.add(selfLinkBuilder.withRel("update-postings"));
        postingResource.add(selfLinkBuilder.withRel("delete-postings"));

        return ResponseEntity.created(createdUri).body(postingResource);
    }

    @GetMapping
    public ResponseEntity findPostings(Pageable pageable) {
        Page<Postings> postingsPage = postingRepository.findAll(pageable);

        List<PostingResource> postingResources = Lists.newArrayList();

        for(Postings p : postingsPage.getContent()) {
            PostingResource postingResource = new PostingResource(p);
            postingResource.add(linkTo(PostingController.class).withRel("query-postings"));

            ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(p.getPostingId());

            postingResource.add(selfLinkBuilder.withRel("recommend-postings"));

            postingResources.add(postingResource);
        }

        return ResponseEntity.ok(postingResources);
    }

//    @GetMapping
//    public ResponseEntity findOnePosting(@RequestParam Long postingId) {
//        Optional<Postings> optional = postingRepository.findById(postingId);
//
//        if(!optional.isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        Postings postings = optional.get();
//
//        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(postings.getPostingId());
//
//        PostingResource postingResource = new PostingResource(postings);
//        postingResource.add(linkTo(PostingController.class).withRel("query-postings"));
//        postingResource.add(selfLinkBuilder.withRel("update-postings"));
//
//        return ResponseEntity.ok().body(postingResource);
//    }
}