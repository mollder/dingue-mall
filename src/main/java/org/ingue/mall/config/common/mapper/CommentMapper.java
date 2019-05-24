package org.ingue.mall.config.common.mapper;

import org.ingue.mall.comment.controller.dto.CommentDto;
import org.ingue.mall.comment.domain.Comments;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements Mapper<Comments, CommentDto> {
    @Override
    public Comments mappingDto(CommentDto commentDto) {
        return Comments.builder()
                .commentContent(commentDto.getCommentContent())
                .build();
    }
}
