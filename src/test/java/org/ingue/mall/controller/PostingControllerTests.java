package org.ingue.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ingue.mall.common.RestDocsConfiguration;
import org.ingue.mall.config.common.TestDescription;
import org.ingue.mall.posting.Board;
import org.ingue.mall.posting.controller.dto.PostingDto;
import org.ingue.mall.posting.domain.Postings;
import org.ingue.mall.posting.PostingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
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

        PostingDto posting = PostingDto.builder()
                .postingContent("테스트글")
                .postingTitle("테스트제목")
                .build();

        mockMvc.perform(post("/api/postings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(posting))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("postingId").exists())
                .andExpect(jsonPath("postingContent").value("테스트글"))
                .andExpect(jsonPath("postingTitle").value("테스트제목"))
                .andExpect(jsonPath("postingRecommend").value(0))
                .andExpect(jsonPath("createAt").exists())
                .andExpect(jsonPath("updateAt").exists())
        .andDo(document(
                "create-posting",
                requestHeaders(
                        headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content-type header")
                ),
                requestFields(
                        fieldWithPath("postingTitle").description("title of new posting"),
                        fieldWithPath("postingContent").description("content of new posting")
                ),
                responseHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("content-type header")
                ),
                relaxedResponseFields(
                        fieldWithPath("postingId").description("id of new posting"),
                        fieldWithPath("postingTitle").description("title of new posting"),
                        fieldWithPath("postingContent").description("content of new posting"),
                        fieldWithPath("postingRecommend").description("recommendCount of new posting"),
                        fieldWithPath("boardName").description("boardName of new posting"),
                        fieldWithPath("commentsSet").description("comments in the new posting"),
                        fieldWithPath("user").description("user who write this new posting"),
                        fieldWithPath("createAt").description("time created for new posting"),
                        fieldWithPath("updateAt").description("time updated for new posting"),
                        fieldWithPath("developer").description("application developer name")
                )
        ));
    }

    @Test
    @TestDescription("받기로한 값 이외에 다른 값들로 글을 생성하면 Bad Request 발생")
    public void createPosting_Bad_Request_UnReceived_Input() throws Exception {
        Postings posting = Postings.builder()
                .postingId(1L)
                .postingContent("테스트글")
                .postingTitle("테스트제목")
                .boardName(Board.USER)
                .build();

        mockMvc.perform(post("/api/postings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(posting))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("글 제목을 공백상태, 내용을 null인 상태로 글을 생성하면 Bad Request 발생")
    public void createPosting_Bad_Request_Wrong_Input() throws Exception {
        PostingDto posting = PostingDto.builder()
                .postingTitle(" ")
                .postingContent("테스트 내용")
                .build();

        mockMvc.perform(post("/api/postings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(posting))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists());

        posting = PostingDto.builder()
                .postingTitle("테스트 제목")
                .postingContent(null)
                .build();

        mockMvc.perform(post("/api/postings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(posting))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists());
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
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("postingId", postings.getPostingId().toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @TestDescription("")
    public void queryPostings() throws Exception {
        //Given
        IntStream.range(0, 30).forEach(this::generatePosting);

        //when
        this.mockMvc.perform(get("/api/postings")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "postingId,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("_embedded.postingsList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andDo(document("query-postings"))
        ;
    }

    private void generatePosting(int i) {
        Postings posting = Postings.builder()
                .postingTitle("테스트제목"+i)
                .postingContent("테스트내용"+i)
                .build();

        this.postingRepository.save(posting);
    }
}
