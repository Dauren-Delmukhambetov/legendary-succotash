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



    @Test
    @DisplayName("Should get two users with specific filtering keyword 'am' ")
    void getUsersWithSpecificFirstname() throws Exception {
        this.mockMvc.perform(get("/users/all?keyword={keyword}", "am")
                .with(csrf())
                .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(2)))
                .andExpect(jsonPath("$.[0].firstName", is("Adam")))
                .andExpect(jsonPath("$.[1].lastName", is("Bentham")));
    }


    @Test
    @DisplayName("Should get first page with 10 users with specific filtering keyword 'com' ")
    void getUsersWithSpecificFirstnameAndLastname() throws Exception {
        this.mockMvc.perform
                (get("/users/all?keyword={keyword}", "com")
                        .with(csrf())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(10)))
                .andExpect(jsonPath("$.[0].firstName", is("Adam")))
                .andExpect(jsonPath("$.[1].lastName", is("Marshall")))
                .andExpect(jsonPath("$.[2].email", is("david.ricardo@gmail.com")))
                .andExpect(jsonPath("$.[-1].lastName", is("Friedman")));
    }


    @Test
    @DisplayName("Should get second page with 3 users with specific filtering keyword 'com' ")
    void getUsersWithSpecificFirstnameAndEmail() throws Exception {
        this.mockMvc.perform
                (get("/users/all?keyword={keyword}&page={page}", "com", 2)
                        .with(csrf())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(3)))
                .andExpect(jsonPath("$.[0].firstName", is("Robert")))
                .andExpect(jsonPath("$.[2].email", is("user@user.com")));
    }

    @Disabled
    @Test
    @DisplayName("Should get active users with specific filtering keyword")
    void getActiveUsersWithSpecificUsernameSortedByLastname() throws Exception {
        this.mockMvc.perform
                (get("/users?keyword={keyword}&page={page}&pageSize={pageSize}&sort={sort}",
                        "joh", 2, 1, "lastName,DESC")
                        .with(csrf())
                        .with(httpBasic(ADMIN_USERNAME, ADMIN_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*.id", hasSize(1)))
                .andExpect(jsonPath("$.[0].lastName", is("Locke")))
                .andExpect(jsonPath("$.[0].email", is("john.locke@gmail.com")));
    }

}
