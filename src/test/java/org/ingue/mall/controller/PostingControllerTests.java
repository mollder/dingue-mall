package org.ingue.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ingue.mall.config.common.TestDescription;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.domain.Postings;
import org.ingue.mall.posting.PostingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostingControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostingRepository postingRepository;

    @Test
    @TestDescription("유저가 글을 게시판에 올릴 때 성공적으로 글이 생성되는지 확인하는 테스트")
    public void createPosting() throws Exception {

        PostingDto postings = PostingDto.builder()
                .postingContent("테스트글")
                .postingTitle("테스트제목")
                .build();

        mockMvc.perform(post("/api/postings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(postings))
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
        .andDo(document("create-postings"));
    }

    @Test
    @TestDescription("유저가 한 게시글을 찾을 때 성공적으로 게시글을 돌려주는지 확인하는 테스트")
    public void findPosting() throws Exception {
        Postings postings = Postings.builder()
                .postingTitle("테스트제목")
                .postingContent("테스트내용")
                .build();

        postingRepository.save(postings);

        mockMvc.perform(get("/api/postings")
                .param("postingId", postings.getPostingId().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
