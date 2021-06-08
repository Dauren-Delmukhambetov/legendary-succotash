package by.itechart.api.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(value = "/db.script/add_random_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/db.script/add_users_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/db.script/delete_all_data_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserFilteringIT {

    @Autowired
    MockMvc mockMvc;

    private final String ADMIN_USERNAME = "admin@admin.com";
    private final String ADMIN_PASSWORD = "Password123";

    @Disabled
    @Test
    @DisplayName("Should get all users with specific firstname")
    void getUsersWithSpecificFirstname() throws Exception {
        this.mockMvc.perform(get("/users/all?firstname={firstname}", "Robert")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(1)))
                .andExpect(jsonPath("$.[0].firstName", is("Robert")));
    }

    @Disabled
    @Test
    @DisplayName("Should get all users with specific firstname, lastname")
    void getUsersWithSpecificFirstnameAndLastname() throws Exception {
        this.mockMvc.perform
                (get("/users/all?firstname={firstname}&lastname={lastname}", "Robert", "Marshall")
                        .with(csrf())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(2)))
                .andExpect(jsonPath("$.[0].firstName", is("Robert")))
                .andExpect(jsonPath("$.[1].lastName", is("Marshall")));
    }

    @Disabled
    @Test
    @DisplayName("Should get all users with specific firstname and email")
    void getUsersWithSpecificFirstnameAndEmail() throws Exception {
        this.mockMvc.perform
                (get("/users/all?firstname={firstname}&email={email}",
                        "Jeremy", "milton.friedman@gmail.com")
                        .with(csrf())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(2)))
                .andExpect(jsonPath("$.[0].firstName", is("Jeremy")))
                .andExpect(jsonPath("$.[1].email", is("milton.friedman@gmail.com")));
    }

    //TODO check does it need to pass all parameters for email and multiple user's firstname
}
