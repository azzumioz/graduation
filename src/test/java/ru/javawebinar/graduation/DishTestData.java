package ru.javawebinar.graduation;

import ru.javawebinar.graduation.model.Dish;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.graduation.RestaurantTestData.*;
import static ru.javawebinar.graduation.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 5;
    public static final int DISH2_ID = START_SEQ + 6;
    public static final int DISH3_ID = START_SEQ + 7;
    public static final int DISH4_ID = START_SEQ + 8;
    public static final int DISH5_ID = START_SEQ + 9;
    public static final int DISH6_ID = START_SEQ + 10;
    public static final int DISH7_ID = START_SEQ + 11;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Dish1-Rest1", of(2019, Month.NOVEMBER, 23), 100);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Dish2-Rest1", of(2019, Month.NOVEMBER, 23), 200);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Dish3-Rest1", of(2019, Month.NOVEMBER, 23), 300);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Dish4-Rest2", LocalDate.now(), 30);
    public static final Dish DISH5 = new Dish(DISH5_ID, "Dish5-Rest2", LocalDate.now(), 200);
    public static final Dish DISH6 = new Dish(DISH6_ID, "Dish6-Rest3", LocalDate.now(), 120);
    public static final Dish DISH7 = new Dish(DISH7_ID, "Dish7-Rest3", LocalDate.now(), 10);


    public static final Dish DISHNEW = new Dish(START_SEQ + 14, "New", of(2019, Month.NOVEMBER, 23), 111);

    static {
        DISHNEW.setRestaurant(RESTAURANT1);
    }

    public static Dish getCreated() {
        return new Dish("NewDish", of(2019, Month.NOVEMBER, 23), 111);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "DISH1_NewName", DISH1.getDate(), DISH1.getPrice(), DISH1.getRestaurant());
    }


    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }


    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertAllMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }

}
