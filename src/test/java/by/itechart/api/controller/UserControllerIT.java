package by.itechart.api.controller;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/db.script/add_users_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/db.script/delete_all_data_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    private final String USER_USERNAME = "user@user.com";
    private final String ADMIN_USERNAME = "admin@admin.com";
    private final String ADMIN_AND_USER_PASSWORD = "Password123";

    private final Long USER_ID = 2L;

    @Test
    void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/users/all")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_AND_USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(2)));
    }

    @Test
    void getCurrentUser() throws Exception {
        this.mockMvc.perform(get("/users/current")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_AND_USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(jsonPath("$.email", is("admin@admin.com")));
    }

    @Test
    void updateCurrentUser() throws Exception {
        var updateUserDTO = getUpdateUserDTO();
        this.mockMvc.perform(patch("/users/current")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDTO))
                .with(csrf())
                .with(httpBasic(USER_USERNAME, ADMIN_AND_USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newemail@email.com")))
                .andExpect(jsonPath("$.phone", is("3213213213")));
    }

    @Test
    void updateUser() throws Exception {
        var updateUserDTO = getUpdateUserDTO();
        this.mockMvc.perform(patch("/users/{userID}", USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDTO))
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_AND_USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("newemail@email.com")))
                .andExpect(jsonPath("$.phone", is("3213213213")));
    }

    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/{userID}", USER_ID)
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_AND_USER_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserWithoutAdminRole() throws Exception {
        this.mockMvc.perform(delete("/users/{userID}", USER_ID)
                .with(httpBasic(USER_USERNAME, ADMIN_AND_USER_PASSWORD))
                .with(csrf()))
                .andDo(print())
                .andExpect(status()
                        .is(403));
    }

    @Test
    void createUser() throws Exception {
        var createUserDTO = getCreateUserDTO();
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO))
                .with(httpBasic(ADMIN_USERNAME, ADMIN_AND_USER_PASSWORD))
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.phone", is("1231231231")));
    }

    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
        assertThat(context).isNotNull();
    }

    private CreateUserDTO getCreateUserDTO() {
        var createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("email@email.com");
        createUserDTO.setPassword(ADMIN_AND_USER_PASSWORD);
        createUserDTO.setPhone("1231231231");
        return createUserDTO;
    }

    private UpdateUserDTO getUpdateUserDTO() {
        var updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setEmail("newemail@email.com");
        updateUserDTO.setPassword("newPassword123");
        updateUserDTO.setPhone("3213213213");
        return updateUserDTO;
    }
}