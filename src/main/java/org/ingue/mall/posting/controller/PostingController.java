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

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(newPostings.getPostingId());
        URI createdUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createdUri).body(newPostings);
    }

    @GetMapping
    public ResponseEntity findPostings(Pageable pageable) {
        Page<Postings> postings = this.postingRepository.findAll(pageable);

        return ResponseEntity.ok(postings);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPosting(@PathVariable Long id) {
        Optional<Postings> optionalPostings = this.postingRepository.findById(id);

        if(!optionalPostings.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Postings posting = optionalPostings.get();

        return ResponseEntity.ok(posting);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePosting(@PathVariable Long id, @RequestBody Postings posting, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Postings> optionalPostings = this.postingRepository.findById(id);

        if(!optionalPostings.isPresent() && !id.equals(posting.getPostingId()) ) {
            return ResponseEntity.notFound().build();
        }

        Postings newPosting = this.postingRepository.save(posting);

        return ResponseEntity.ok(newPosting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePosting(@PathVariable Long id) {
        Optional<Postings> optionalPosting = this.postingRepository.findById(id);

        if(!optionalPosting.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        this.postingRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

}