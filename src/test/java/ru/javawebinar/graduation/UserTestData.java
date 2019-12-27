package ru.javawebinar.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.to.UserTo;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.graduation.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.graduation.TestUtil.readListFromJsonMvcResult;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@mail.ru","password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@mail.ru","password", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static UserTo getCreated() {
        return new UserTo(null, "New", "new@mail.ru", "newpass");
    }

    public static UserTo getUpdated() {
        return new UserTo(null, "New", "new@mail.ru", "newpass");
    }

    public static UserTo getDuplicated() {
        return new UserTo(null, "New", "user@mail.ru", "password");
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "password");
    }

//    public static void assertMatch(UserTo actual, UserTo expected) {
//        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "password");
//    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

//    public static ResultMatcher contentJson(User expected) {
//        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
//    }

    public static ResultMatcher getUserMatcher(User... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

//    public static ResultMatcher getUserMatcher(User expected) {
//        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
//    }

}
