package ru.javawebinar.graduation.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.TestUtil;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.service.UserService;
import ru.javawebinar.graduation.to.UserTo;
import ru.javawebinar.graduation.util.UserUtil;
import ru.javawebinar.graduation.web.AbstractControllerTest;
import ru.javawebinar.graduation.web.json.JsonUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.graduation.TestUtil.readFromJson;
import static ru.javawebinar.graduation.TestUtil.userHttpBasic;
import static ru.javawebinar.graduation.UserTestData.*;
import static ru.javawebinar.graduation.UserTestData.USER;

class ProfileUserControllerTest extends AbstractControllerTest {
    private static final String REST_URL = ProfileUserController.REST_URL + "/";

    @Autowired
    private UserService userService;

    @Test
    void getUser() throws Exception {
        ResultActions action = mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        User returned = TestUtil.readFromJson(action, User.class);
        assertMatch(USER, returned);
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update() throws Exception {
        UserTo updated = getUpdated();

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.getByEmail("new@mail.ru"), UserUtil.updateFromTo(USER, updated));

    }

    @Test
    void testdelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER)))
                .andDo(print());
        //.andExpect(status().isNoContent());
        assertMatch(userService.getAll(), ADMIN);
    }

    @Test
    void register() throws Exception {
        UserTo created = getCreated();

        ResultActions action = mockMvc.perform(post(REST_URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());

        User returned = readFromJson(action, User.class);
        created.setId(returned.getId());


        //assertMatch(returned, created);
        assertMatch(userService.getAll(), ADMIN, returned, USER);
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void registerDuplicate() throws Exception {
        UserTo createdDuplicate = getDuplicated();

        mockMvc.perform(post(REST_URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(createdDuplicate)))
                .andExpect(status().isConflict());
    }
}