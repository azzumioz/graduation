package ru.javawebinar.graduation.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.graduation.TestUtil;
import ru.javawebinar.graduation.VoteTestData;
import ru.javawebinar.graduation.service.VoteService;
import ru.javawebinar.graduation.to.VoteTo;
import ru.javawebinar.graduation.web.AbstractControllerTest;
import ru.javawebinar.graduation.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.graduation.UserTestData.USER;
import static ru.javawebinar.graduation.VoteTestData.*;

class ProfileVoteControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileVoteController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    void save() throws Exception {
        VoteTo created = VoteTestData.getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(USER)))
                .andExpect(status().isCreated());

        VoteTo returned = TestUtil.readFromJson(action, VoteTo.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
    }

    @Test
    void getToday() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE5_TO));
    }

    @Test
    void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2019-12-23")
                .param("endDate", "2019-12-24")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(VOTE1_TO, VOTE3_TO));
    }

}