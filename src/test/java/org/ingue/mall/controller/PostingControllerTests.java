package org.ingue.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ingue.mall.common.RestDocsConfiguration;
import org.ingue.mall.config.common.TestDescription;
import org.ingue.mall.config.common.mapper.PostingMapper;
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
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.not;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    PostingMapper postingMapper;

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
    @TestDescription("글 30개 생성 후 두번째 페이지에 10개 글을 가져올 때 성공적으로 글과 페이지 정보가 들어있어야 함")
    public void queryPostings() throws Exception {
        //Given
        IntStream.range(0, 30).forEach(this::generatePosting);

        //when & then
        this.mockMvc.perform(get("/api/postings")
                .param("page", "1")
                .param("size", "10")
                .param("sort", "postingId,DESC")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("pageable").exists())
                .andExpect(jsonPath("pageable.pageSize").value(10))
                .andExpect(jsonPath("pageable.pageNumber").value(1))
        ;
    }

    @Test
    @TestDescription("")
    public void getPosting() throws Exception {
        //Given
        Postings posting = this.generatePosting(1);

        //When & then
        this.mockMvc.perform(get("/api/postings/{id}", posting.getPostingId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("postingId").exists())
                .andExpect(jsonPath("postingContent").exists())
                .andExpect(jsonPath("postingTitle").exists())
                .andExpect(jsonPath("postingRecommend").exists())
                .andExpect(jsonPath("createAt").exists())
                .andExpect(jsonPath("updateAt").exists())
                ;
    }

    @Test
    @TestDescription("없는 글을 요청했을 때 404 발생")
    public void getPosting404() throws Exception {
        this.mockMvc.perform(get("/api/postings/{id}", Long.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @TestDescription("제목과 내용이 바뀐 객체로 업데이트 요청이 들어오면 업데이트가 성공적으로 진행되어야 함")
    public void updatePosting() throws Exception {
        Postings postings = this.generatePosting(1);

        this.postingRepository.save(postings);

        Postings updatePosting = Postings.builder()
                .postingId(postings.getPostingId())
                .postingTitle("다른제목")
                .postingContent("다른내용")
                .postingRecommend(postings.getPostingRecommend())
                .boardName(postings.getBoardName())
                .commentsSet(postings.getCommentsSet())
                .user(postings.getUser())
                .build();

        this.mockMvc.perform(put("/api/postings/{id}", postings.getPostingId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(updatePosting))
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("postingId").value(postings.getPostingId()))
                .andExpect(jsonPath("postingTitle").value(not(postings.getPostingTitle())))
                .andExpect(jsonPath("postingContent").value(not(postings.getPostingContent())))
                .andExpect(jsonPath("postingRecommend").value(postings.getPostingRecommend()))
                .andExpect(jsonPath("updateAt").value(not(postings.getUpdateAt())))
                ;
    }

    @Test
    @TestDescription("저장해둔 글을 삭제했을 때 성공적으로 삭제되는지 되어야 함")
    public void deletePosting() throws Exception {
        PostingDto dto = PostingDto.builder()
                .postingTitle("테스트제목")
                .postingContent("테스트내용")
                .build();

        Postings posting = this.postingMapper.mappingDto(dto);

        this.postingRepository.save(posting);

        this.mockMvc.perform(delete("/api/postings/{id}", posting.getPostingId()))
                .andDo(print())
                .andExpect(status().isOk());

        Optional<Postings> optionalPosting = this.postingRepository.findById(posting.getPostingId());

        assertThat(optionalPosting.isPresent()).isFalse();
    }

    @Test
    @TestDescription("없는 글을 삭제하려고 하면 404가 나와야 함")
    public void deletePosting404() throws Exception {
        Optional<Postings> optionalPosting = this.postingRepository.findById(Long.MAX_VALUE);

        assertThat(optionalPosting.isPresent()).isFalse();

        this.mockMvc.perform(delete("/api/postings/{id}", Long.MAX_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @TestDescription("글을 추천했을 때 정상적으로 추천수가 1 올라가야 함")
    public void recommendPosting() throws Exception {
        PostingDto postingDto = PostingDto.builder()
                .postingContent("테스트내용")
                .postingTitle("테스트제목")
                .build();

        Postings posting = postingMapper.mappingDto(postingDto);

        Postings newPosting = this.postingRepository.save(posting);

        this.mockMvc.perform(patch("/api/postings/{id}", newPosting.getPostingId())
                            .param("postingRecommend", String.valueOf(newPosting.getPostingRecommend()))
                            .accept(MediaTypes.HAL_JSON_UTF8))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("postingRecommend").value(newPosting.getPostingRecommend()+1))
                            .andExpect(jsonPath("postingId").value(newPosting.getPostingId()));
    }

    @Test
    @TestDescription("없는 글을 추천하면 404에러 발생")
    public void recommendPosting404() throws Exception {
        this.mockMvc.perform(patch("/api/postings/{id}", Long.MAX_VALUE)
                .param("postingRecommend", "0")
                .accept(MediaTypes.HAL_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Postings generatePosting(int i) {
        Postings posting = Postings.builder()
                .postingTitle("테스트제목"+i)
                .postingContent("테스트내용"+i)
                .build();

        return this.postingRepository.save(posting);
    }
}
