package com.sitrack.sitrackbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sitrack.sitrackbackend.annotation.WithAuthUser;
import com.sitrack.sitrackbackend.domain.constant.RoleType;
import com.sitrack.sitrackbackend.dto.AdminProductDto;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import com.sitrack.sitrackbackend.service.ManagerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private ManagerService managerService;

    public ManagerControllerTest(@Autowired MockMvc mvc,
                               @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.objectMapper = objectMapper;
    }

    @DisplayName("[ManagerC] 권한 설정 완료")
    @WithAuthUser(userId = "test1", role = "ROLE_MANAGER")
    @Test
    public void update_product() throws Exception {
        // Given
        UserAccountDto userAccountDto = createUserAccountDto("test1");

        // When & Then
        mvc.perform(
                post("/manager/update/role")
                        .content(objectMapper.writeValueAsString(userAccountDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andExpect(status().isOk());
        then(managerService).should().setRole(any(), any());
    }

    private UserAccountDto createUserAccountDto(String userId) {
        return UserAccountDto.of(
                userId,
                "1234",
                "권용호",
                "b2b@naver.com",
                "010-1111-2222",
                RoleType.ADMIN,
                LocalDateTime.now(),
                "kwon",
                LocalDateTime.now(),
                "kwon"
        );
    }
}
