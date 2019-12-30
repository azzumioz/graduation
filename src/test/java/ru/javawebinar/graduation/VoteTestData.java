package ru.javawebinar.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.javawebinar.graduation.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.graduation.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    private static final int VOTE1 = START_SEQ + 20;
    private static final int VOTE2 = START_SEQ + 21;
    private static final int VOTE3 = START_SEQ + 22;
    private static final int VOTE4 = START_SEQ + 23;
    private static final int VOTE5 = START_SEQ + 24;
    private static final int VOTE6 = START_SEQ + 25;

    public static final VoteTo VOTE1_TO = new VoteTo(VOTE1, of(2019, Month.DECEMBER, 23, 10, 0), 100003);
    public static final VoteTo VOTE2_TO = new VoteTo(VOTE2, of(2019, Month.DECEMBER, 23, 10, 0), 100003);
    public static final VoteTo VOTE3_TO = new VoteTo(VOTE3, of(2019, Month.DECEMBER, 24, 10, 0), 100002);
    public static final VoteTo VOTE4_TO = new VoteTo(VOTE4, of(2019, Month.DECEMBER, 24, 10, 0), 100002);
    public static final VoteTo VOTE5_TO = new VoteTo(VOTE5, of(LocalDate.now(), LocalTime.MIN), 100002);
    public static final VoteTo VOTE6_TO = new VoteTo(VOTE6, of(LocalDate.now(), LocalTime.MIN), 100003);

    public static VoteTo getCreated() {
        return new VoteTo(LocalDateTime.now(), 100002);
    }

    public static void assertMatch(VoteTo actual, VoteTo expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static ResultMatcher contentJson(VoteTo... expected) {
        return contentJson(List.of(expected));
    }

    private static ResultMatcher contentJson(Iterable<VoteTo> expected) {
        return result -> assertThat(TestUtil.readListFromJsonMvcResult(result, VoteTo.class)).isEqualTo(expected);
    }

}
