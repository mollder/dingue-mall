package org.ingue.mall.comment.controller;

import lombok.RequiredArgsConstructor;
import org.ingue.mall.comment.CommentRepository;
import org.ingue.mall.comment.controller.dto.CommentDto;
import org.ingue.mall.comment.domain.Comments;
import org.ingue.mall.config.common.mapper.CommentMapper;
import org.ingue.mall.posting.controller.PostingController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/postings/{postingId}/comments",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity createComment(@PathVariable Long postingId, @RequestBody CommentDto commentDto) {
        Comments comment = commentMapper.mappingDto(commentDto);

        Comments newComment = commentRepository.save(comment);

        ControllerLinkBuilder selfLinkBuilder = linkTo(CommentController.class, postingId).slash(newComment.getCommentId());
        URI createdUri = selfLinkBuilder.toUri();

        return ResponseEntity.created(createdUri).build();
    }
}
