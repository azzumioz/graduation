package ru.javawebinar.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.graduation.service.VoteService;
import ru.javawebinar.graduation.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.graduation.UserTestData.ADMIN;
import static ru.javawebinar.graduation.VoteTestData.*;

class AdminVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminVoteController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    void getToday() throws Exception{
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE5_TO, VOTE6_TO));
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2019-12-23")
                .param("endDate", "2019-12-24")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1_TO, VOTE2_TO, VOTE3_TO, VOTE4_TO));

    }

    @Test
    void getBetweenWithUser() throws Exception {
        mockMvc.perform(get(REST_URL + "filter/100000")
                .param("startDate", "2019-12-23")
                .param("endDate", "2019-12-24")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1_TO, VOTE3_TO));
    }

}