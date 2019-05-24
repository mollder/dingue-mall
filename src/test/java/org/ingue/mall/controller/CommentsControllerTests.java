package org.ingue.mall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ingue.mall.comment.controller.dto.CommentDto;
import org.ingue.mall.comment.CommentRepository;
import org.ingue.mall.comment.domain.Comments;
import org.ingue.mall.config.common.TestDescription;
import org.ingue.mall.config.common.mapper.CommentMapper;
import org.ingue.mall.config.common.mapper.PostingMapper;
import org.ingue.mall.posting.PostingRepository;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.domain.Postings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentsControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostingRepository postingRepository;

    @Autowired
    PostingMapper postingMapper;

    @Autowired
    CommentMapper commentMapper;

    @Test
    @TestDescription("코멘트를 생성했을 때 성공적으로 올바른 댓글이 만들어 져야 함")
    public void createComment() throws Exception {
        PostingDto postingDto = PostingDto.builder()
                .postingTitle("테스트제목")
                .postingContent("테스트내용")
                .build();

        Postings posting = postingMapper.mappingDto(postingDto);

        Postings savePosting = postingRepository.save(posting);

        CommentDto commentDto = new CommentDto();
        commentDto.setCommentContent("테스트댓글내용");

        this.mockMvc.perform(post("/api/postings/{postingId}/comments", savePosting.getPostingId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(commentDto))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("posting").exists())
        ;
    }

    @Test
    @TestDescription("댓글을 생성할 때 없는 글에 댓글을 달려고 시도하면 404")
    public void createPosting_badInput_404() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentContent("테스트댓글내용");

        this.mockMvc.perform(post("/api/postings/{postingId}/comments", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(commentDto))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
