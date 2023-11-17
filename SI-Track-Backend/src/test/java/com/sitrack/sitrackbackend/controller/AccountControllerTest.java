package com.sitrack.sitrackbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.SearchIdDto;
import com.sitrack.sitrackbackend.dto.SearchPwdDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    public AccountControllerTest(@Autowired MockMvc mvc,
                                 @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }


    @DisplayName("[AccountC] 회원 가입 성공")
    @WithMockUser
    @Test
    public void signup_success() throws Exception {
        UserAccountDto userAccountDto = createUserAccountDto();
        given(accountService.signup(userAccountDto)).willReturn("회원 가입 완료");

        mvc.perform(
                post("/account/user/signup")
                        .content(objectMapper.writeValueAsString(userAccountDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @DisplayName("[AccountC] 아이디 찾기")
    @WithMockUser
    @Test
    public void userSearchId_success() throws Exception {
        SearchIdDto searchIdDto = SearchIdDto.of("user", "test@naver.com");

        mvc.perform(
                        post("/account/user/searchId")
                                .content(objectMapper.writeValueAsString(searchIdDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk());
        then(accountService).should().searchId(searchIdDto);
    }

    @DisplayName("[AccountC] 비밀번호 찾기")
    @WithMockUser
    @Test
    public void userSearchPwd_success() throws Exception {
        SearchPwdDto searchPwdDto = SearchPwdDto.of("user", "test@naver.com");

        mvc.perform(
                        post("/account/user/searchPwd")
                                .content(objectMapper.writeValueAsString(searchPwdDto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk());
        then(accountService).should().searchPwd(searchPwdDto);

    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "user1",
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222",
                RoleType.USER,
                LocalDateTime.now(),
                "kwon",
                LocalDateTime.now(),
                "kwon"
        );
    }

}
