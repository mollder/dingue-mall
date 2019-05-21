package org.ingue.mall.config.common.mapper;

import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.domain.Postings;
import org.springframework.stereotype.Component;

@Component
public class PostingMapper implements Mapper<Postings, PostingDto> {

    @Override
    public Postings mappingDto(PostingDto postingDto) {
        return Postings.builder()
                .postingTitle(postingDto.getPostingTitle())
                .postingContent(postingDto.getPostingContent())
                .build();
    }
}
