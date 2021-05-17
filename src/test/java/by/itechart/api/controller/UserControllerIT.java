package by.itechart.api.controller;

import by.itechart.api.dto.CreateUserDTO;
import by.itechart.api.dto.UpdateUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@admin.com", roles = "ADMIN", password = "$2a$12$mbrKKshlIeJQNszD4KJSjeXCAZGbIMrcDG8qkiPkwTXy2G4dNXzrW")
@Sql(value = "/db.script/add_users_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db.script/add_users_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/db.script/add_users_before.sql")
    void getAllUsers() throws Exception {
        this.mockMvc.perform(get("/users/all")).andDo(print()).andExpect(status().isOk());
        //.andExpect(content().string(containsString("")))
    }

    @Test
    void getCurrentUser() throws Exception {
        this.mockMvc.perform(get("/users/current")).andDo(print()).andExpect(status().is(302));
    }

    @Test
    void updateCurrentUser() {
        //TODO authentication?
    }

    @Test
    void updateUser() throws Exception {
        var updateUserDTO = getUpdateUserDTO();
        this.mockMvc.perform(patch("/users"));
        //TODO id?
    }

    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users").param("id", "2")).andDo(print())
                .andExpect(status().isOk());
        //something goes wrong
    }

    @Test
    void createUser() throws Exception {
        var createUserDTO = getCreateUserDTO();
        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDTO))
                .with(csrf())).andExpect(status().isCreated());
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(mockMvc).isNotNull();
        assertThat(context).isNotNull();
    }

    @BeforeTest
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private CreateUserDTO getCreateUserDTO() {
        var createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("email@email.com");
        createUserDTO.setPassword("Password123");
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