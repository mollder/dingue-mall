package org.ingue.mall.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ingue.mall.common.TestDescription;
import org.ingue.mall.domain.Postings;
import org.ingue.mall.domain.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PostingControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("유저가 글을 게시판에 올릴 때 성공적으로 글이 생성되는지 확인하는 테스트")
    public void createPosting() throws Exception {

        Postings postings = Postings.builder()
                .postingContent("테스트글")
                .postingTitle("테스트제목")
                .build();

        Users user = Users.builder()
                .userEmail("테스트이메일")
                .userNickName("테스트닉네임")
                .userPassword("테스트패스워드")
                .userPhoneNum("테스트핸드폰번호")
                .build();

        postings.setUsers(user);

        mockMvc.perform(post("/failure")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsBytes(postings))
                .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
