package by.itechart.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
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
//FIXME all tests are not working
class UsersPaginationIT {

    @Autowired
    private MockMvc mockMvc;

    private final String ADMIN_USERNAME = "admin@admin.com";
    private final String ADMIN_PASSWORD = "Password123";


    @Test
    @DisplayName("Should return first page with ten items when no page is defined in request")
    void getFirstPage() throws Exception {
        this.mockMvc.perform(get("/users/all")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(10)))
                .andExpect(jsonPath("$.[0].firstName", is("Adam")))
                .andExpect(jsonPath("$.[1,2].firstName", contains("Alfred", "David")))
                .andExpect(jsonPath("$.[-1:].lastName", contains("Friedman")));
    }


    @Test
    @DisplayName("Should return second page with three items when page and pageSize are defined in request")
    void getSecondPage() throws Exception {
        this.mockMvc.perform(get("/users/all?page={page}&pageSize={pageSize}", 2, 3)
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(3)))
                .andExpect(jsonPath("$.[0].firstName", is("Elinor")))
                .andExpect(jsonPath("$.[1].lastName", is("von Böhm-Bawerk")))
                .andExpect(jsonPath("$.[-1:].email", contains("jean-baptiste.say@gmail.com")));
    }


    @Test
    @DisplayName("Should return last page with leftovers when only page is defined in request")
    void getLastPage() throws Exception {
        this.mockMvc.perform(get("/users/all?page={page}", 2)
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(3)))
                .andExpect(jsonPath("$.[0].firstName", is("Robert")))
                .andExpect(jsonPath("$.[1].lastName", nullValue()))
                .andExpect(jsonPath("$.[-1:].email", contains("user@user.com")));
    }


    @Test
    @DisplayName("Should return first page with only active users when no page is defined in request")
    void getFirstPageOfActiveUsers() throws Exception {
        this.mockMvc.perform(get("/users")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(10)))
                .andExpect(jsonPath("$.[0].firstName", is("Adam")))
                .andExpect(jsonPath("$.[1,2].lastName", contains("Marshall", "Ricardo")))
                .andExpect(jsonPath("$.[-1:].lastName", contains("Lucas Jr.")));
    }


    @Test
    @DisplayName("Should return specific page when both page and pageSize are defined in request")
    void getSpecificPageOfActiveUsers() throws Exception {
        this.mockMvc.perform(get("/users?page={page}&pageSize={pageSize}", 9, 1)
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(1)))
                .andExpect(jsonPath("$.[0].firstName", equalToIgnoringCase("milton")))
                .andExpect(jsonPath("$.[0].phone", nullValue()))
                .andExpect(jsonPath("$.[0].email", containsString("milton.friedman@gmail.com")));
    }

}
