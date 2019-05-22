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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
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
    public ResponseEntity findPostings(Pageable pageable, PagedResourcesAssembler<Postings> postingsPagedResourcesAssembler) {
        Page<Postings> postings = this.postingRepository.findAll(pageable);
        PagedResources<Resource<Postings>> pagedResources = postingsPagedResourcesAssembler.toResource(postings,
                p -> new PostingResource(p));

        return ResponseEntity.ok(pagedResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPosting(@PathVariable Long id) {
        Optional<Postings> optionalPostings = this.postingRepository.findById(id);

        if(!optionalPostings.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Postings posting = optionalPostings.get();
        PostingResource postingResource = new PostingResource(posting);

        return ResponseEntity.ok(postingResource);
    }

    @PutMapping
    public ResponseEntity updatePosting(@RequestBody Postings posting, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Postings> optionalPostings = this.postingRepository.findById(posting.getPostingId());

        if(!optionalPostings.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Postings newPosting = this.postingRepository.save(posting);

        return ResponseEntity.ok(newPosting);
    }

}