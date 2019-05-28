package org.ingue.mall.posting.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.config.common.mapper.PostingMapper;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.PostingRepository;
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

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/postings",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE // tset를 해보는 것이 좋음
)
//controller 에서는 dto 의 변환과 메세지 리턴의 책임이 있음.
//그래서 트랜잭션을 쓰지 않는 것이 명확
public class PostingController {

    private final PostingRepository postingRepository;
    private final PostingMapper postingMapper;

    @PostMapping
    public ResponseEntity createPosting(@RequestBody @Valid PostingDto postingDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        } // 중복 코드라서 빼는 것이 좋음, 롬북 애노테이션에서 유틸리티 참조

        Postings postings = postingMapper.mappingDto(postingDto);
        Postings newPostings = postingRepository.save(postings);

        ControllerLinkBuilder selfLinkBuilder = linkTo(PostingController.class).slash(newPostings.getPostingId());
        URI createdUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createdUri).body(newPostings);
    }

    // Pageable 관련 설정들을 구체적으로 사용 ( 에러 처리 )
    // 뷰단에 객체를 돌려줄때도 Dto를 사용하는 것이 좋음 ( 엔티티를 직접 노출하는 것은 위험 )
    @GetMapping
    public ResponseEntity findPostings(Pageable pageable) {
        Page<Postings> postings = this.postingRepository.findAll(pageable);

        return ResponseEntity.ok(postings);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPosting(@PathVariable Long id) {
        Optional<Postings> optionalPostings = this.postingRepository.findById(id); // controller 와 domain 간의 명확한 레이어 구분을 해두는 것이 좋음
        // 에러 처리는 전역으로 해서 코드 중복을 줄이는 방법이 좋고 전역 exception handler

        if(!optionalPostings.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Postings posting = optionalPostings.get();

        return ResponseEntity.ok(posting);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updatePosting(@PathVariable Long id, @RequestBody Postings posting, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Postings> optionalPostings = this.postingRepository.findById(id);

        if(!optionalPostings.isPresent() && !id.equals(posting.getPostingId()) ) { // 불필요한 체크
            return ResponseEntity.notFound().build();
        }

        Postings newPosting = this.postingRepository.save(posting);

        return ResponseEntity.ok(newPosting);
    }

    //실제 실무에서는 삭제를 하지 않음
    // history 테이블에 넣거나 아니면 flag를 둬서 저장하는 경우가 대부분
    @DeleteMapping("/{id}")
    public ResponseEntity deletePosting(@PathVariable Long id) {
        Optional<Postings> optionalPosting = this.postingRepository.findById(id);

        if(!optionalPosting.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        this.postingRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updatePostingRecommend(@PathVariable Long id, @RequestParam Integer postingRecommend) {
        Optional<Postings> optionalPosting = this.postingRepository.findById(id);

        if(!optionalPosting.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Postings posting = optionalPosting.get();
        posting.recommendPosting();

        Postings updatePosting = this.postingRepository.save(posting);

        return ResponseEntity.ok(updatePosting);
    }
}